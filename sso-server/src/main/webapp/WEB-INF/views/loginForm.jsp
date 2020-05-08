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
					<!-- 이메일 -->
					<input type="text" name="member_id" required="required" placeholder="전화번호, 사용자 이름 또는 이메일">
					
					<!-- 비밀번호 -->
					<input type="password" name="member_password" required="required" placeholder="비밀번호">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
					<!-- 로그인 버튼 -->
					<input type="submit" value="security로그인">
				</form>
				<Button type="button" onclick="login();">ajax로그인</Button>
				
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
				
				<!-- 유효성검층 출력부분 -->
				<div id="loginchk"></div>
				
				<div>
					<a href="#">비밀번호를 잊으셨나요?</a>
				</div>
			</div>			
			
		</div>

	 
	 	<div>
			계정이 없으신가요?
			<a href="/join">&nbsp;가입하기 </a>
		</div>
	</section>

	
	<section>
		<h1>사이트소개</h1>
		
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
			
			// 이메일 정규식
			var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
			
			// 핸드폰번호 정규식 (010-1234-1234)
			var regExpPhone = /^\d{3}-\d{4}-\d{4}$/;
			
			var member_id = $("input[name='member_id']").val();
			var member_password = $("input[name='member_password']").val();
			
			if(regExpEmail.test(member_id)) { // 이메일 형식이라면
				
				var loginVal = {
						"memberemail" : member_id,
						"memberpassword" : member_password
				}
			
			} else if(regExpPhone.test(member_id)) {	// 핸드폰번호 형식이라면
				
				member_id = member_id.split("-").join("");
				alert(member_id);
			
				var loginVal = {
						"memberphone" : member_id,
						"memberpassword" : member_password
				}
			
			} else {		// 아이디라면
				
				var loginVal = {
						"member_id" : member_id,
						"member_password" : member_password
				}
				
			}
			
			var params = "member_id="+member_id+"&member_password="+member_password;
			
			if(member_id == null || member_id == "" 
					|| member_password == null || member_password == ""){
				alert("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.")
			}else{
				
				$.ajax({
					type: "post",
					url: "/loginProcess",
					data: params,//JSON.stringify(loginVal),
					//contentType: "application/json",
					dataType: "json",
					beforeSend : function(xhr)
		            {   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						xhr.setRequestHeader(header, token);
		            },
					
					success: function(msg){
						console.log(msg)			
						/* if (msg.result == 'success'){
							location.href="/result";
						} else {
							$("#loginchk").show().html("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.").css("color","red")
						} */
/* 						if (msg.check == true) {
							location.href="/result";
						} else {
							$("#loginchk").show().html("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.").css("color","red")
						} */
		
					},
					
					error: function(){
						$("#loginchk").show().html("입력한 사용자 이름을 사용하는 계정을 찾을 수 없습니다. 사용자 이름을 확인하고 다시 시도하세요.").css("color","red")
					}
				})
			}
			
		}
	</script>
<!-- END :: Ajax login -->

</html>