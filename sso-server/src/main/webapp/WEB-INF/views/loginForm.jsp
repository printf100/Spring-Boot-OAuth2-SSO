<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
<meta charset="EUC-KR">
<title>Insert title here</title>

<!-- START :: js import -->
	<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- END :: js import -->


</head>
<body>
	<section>
	 	
		
		<div>
			<h1>DEVS</h1>
			
			<div>				
				<form action="/login" method="post">			
					<!-- �̸��� -->
					<input type="text" name="member_id" required="required" placeholder="��ȭ��ȣ, ����� �̸� �Ǵ� �̸���">
					
					<!-- ��й�ȣ -->
					<input type="password" name="member_password" required="required" placeholder="��й�ȣ">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
					<!-- �α��� ��ư -->
					<input type="submit" value="security�α���">
				</form>
				<Button type="button" onclick="login();">ajax�α���</Button>
				
			</div>
			
			<div>
				<hr>
			</div>
			
			<div>
				<span>
					<a id="custom-login-btn" href="javascript:loginWithKakaoRest()">
<!-- 						<img src="/resources/images/social/kakaolink_btn_medium.png" width="100px;"/> -->
					</a>
				</span>
				<span>
					<a id="naver_id_login"></a>
				</span>
				
				<!-- ��ȿ������ ��ºκ� -->
				<div id="loginchk"></div>
				
				<div>
					<a href="#">��й�ȣ�� �����̳���?</a>
				</div>
			</div>			
			
		</div>

	 
	 	<div>
			������ �����Ű���?
			<a href="/join">&nbsp;�����ϱ� </a>
		</div>
	</section>

	
	<section>
		<h1>����Ʈ�Ұ�</h1>
		
		<div>
	
		</div>
	</section>
	
	
	
</body>

<!-- START :: Ajax login -->
	<script type="text/javascript">	
		$(function(){
			$("#loginchk").hide();
		})
		
		function login(){
			var token = $("meta[name='_csrf']").attr("th:content");
			var header = $("meta[name='_csrf_header']").attr("th:content");
			
			// �̸��� ���Խ�
			var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			
			// �ڵ�����ȣ ���Խ� (010-1234-1234)
			var regExpPhone = /^\d{3}-\d{4}-\d{4}$/;
			
			var member_id = $("input[name='member_id']").val();
			var member_password = $("input[name='member_password']").val();
			
			if(regExpEmail.test(member_id)) { // �̸��� �����̶��
				
				var loginVal = {
						"memberemail" : member_id,
						"memberpassword" : member_password
				}
			
			} else if(regExpPhone.test(member_id)) {	// �ڵ�����ȣ �����̶��
				
				member_id = member_id.split("-").join("");
				alert(member_id);
			
				var loginVal = {
						"memberphone" : member_id,
						"memberpassword" : member_password
				}
			
			} else {		// ���̵���
				
				var loginVal = {
						"member_id" : member_id,
						"member_password" : member_password
				}
				
			}
			
			var params = "member_id="+member_id+"&member_password="+member_password;
			
			if(member_id == null || member_id == "" 
					|| member_password == null || member_password == ""){
				alert("�Է��� ����� �̸��� ����ϴ� ������ ã�� �� �����ϴ�. ����� �̸��� Ȯ���ϰ� �ٽ� �õ��ϼ���.")
			}else{
				
				$.ajax({
					type: "post",
					url: "/loginProcess",
					data: params,//JSON.stringify(loginVal),
					//contentType: "application/json",
					dataType: "json",
					beforeSend : function(xhr)
		            {   /*�����͸� �����ϱ� ���� ����� csrf���� �����Ѵ�*/
						xhr.setRequestHeader(header, token);
		            },
					
					success: function(msg){
						console.log(msg)			
						/* if (msg.result == 'success'){
							location.href="/result";
						} else {
							$("#loginchk").show().html("�Է��� ����� �̸��� ����ϴ� ������ ã�� �� �����ϴ�. ����� �̸��� Ȯ���ϰ� �ٽ� �õ��ϼ���.").css("color","red")
						} */
/* 						if (msg.check == true) {
							location.href="/result";
						} else {
							$("#loginchk").show().html("�Է��� ����� �̸��� ����ϴ� ������ ã�� �� �����ϴ�. ����� �̸��� Ȯ���ϰ� �ٽ� �õ��ϼ���.").css("color","red")
						} */
		
					},
					
					error: function(){
						$("#loginchk").show().html("�Է��� ����� �̸��� ����ϴ� ������ ã�� �� �����ϴ�. ����� �̸��� Ȯ���ϰ� �ٽ� �õ��ϼ���.").css("color","red")
					}
				})
			}
			
		}
	</script>
<!-- END :: Ajax login -->

</html>