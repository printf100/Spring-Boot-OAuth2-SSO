<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>

<!-- START :: js import -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- END :: js import -->

</head>
<body>
	<section>
	 	
		
		<div>
			<h1>
				DEVS
			</h1>
			<h3>
				ģ������ ������ �������� ������ �����ϼ���.
			</h3>
			
			<div>
				<span>
					<a id="custom-login-btn" href="javascript:loginWithKakaoRest()">
<!-- 						<img src="/resources/images/social/kakaolink_btn_medium.png" width="100px;"/> -->
					</a>
				</span>
				<span>
					<a id="naver_id_login"></a>
				</span>
			</div>	
			
			<div>
				<hr>
			</div>
			
			<div>		
				<form id="joinForm" action="/join" method="post" name="MemberVo">
					<!-- �̸��� -->
					<input type="text" name="memberaccount" required="required" placeholder="�޴��� ��ȣ �Ǵ� �̸��� �ּ�">
					
					<!-- ���� -->
					<input type="text" name="membername" required="required" placeholder="����">
					
					<!-- ����� �̸� -->
					<input type="text" name="memberid" required="required" placeholder="����� �̸�">
					
					<!-- ��й�ȣ -->
					<input type="password" name="memberpassword" required="required" placeholder="��й�ȣ">
					
					<!-- ȸ������ ��ư -->					
					<input type="submit" value="securityȸ������">
					<!-- ��ȿ������ ��ºκ� -->					
					<div id="joinchk"></div>
				</form>

			</div>		
			
		</div>

	 
 		<div>
			������ �����Ű���?
			<a href="/loginForm">&nbsp;�α��� </a>
		</div>
	</section>
</body>

<!-- START :: MEMBER �⺻ ȸ������ -->
<script type="text/javascript">

	$(function() {
		
		// �̸��� ���Խ�
		var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		// �ڵ�����ȣ ���Խ� (010-1234-1234)
		var regExpPhone = /^\d{3}-\d{4}-\d{4}$/;
		
		
		if(regExpEmail.test(memberaccount)) { // �̸��� �����̶��
			$("input[name='memberaccount']").attr({
				"name" : "memberemail"
			})
		
		} else if(regExpPhone.test(memberaccount)) {	// �ڵ�����ȣ �����̶��
			$("input[name='memberaccount']").attr({
				"name" : "memberphone"
			})
		
		}
		
	});
	
</script>
<!-- END :: MEMBER �⺻ ȸ������ -->

</html>