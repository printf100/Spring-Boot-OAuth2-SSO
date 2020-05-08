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
				친구들의 사진과 동영상을 보려면 가입하세요.
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
					<!-- 이메일 -->
					<input type="text" name="memberaccount" required="required" placeholder="휴대폰 번호 또는 이메일 주소">
					
					<!-- 성명 -->
					<input type="text" name="membername" required="required" placeholder="성명">
					
					<!-- 사용자 이름 -->
					<input type="text" name="memberid" required="required" placeholder="사용자 이름">
					
					<!-- 비밀번호 -->
					<input type="password" name="memberpassword" required="required" placeholder="비밀번호">
					
					<!-- 회원가입 버튼 -->					
					<input type="submit" value="security회원가입">
					<!-- 유효성검층 출력부분 -->					
					<div id="joinchk"></div>
				</form>

			</div>		
			
		</div>

	 
 		<div>
			계정이 있으신가요?
			<a href="/loginForm">&nbsp;로그인 </a>
		</div>
	</section>
</body>

<!-- START :: MEMBER 기본 회원가입 -->
<script type="text/javascript">

	$(function() {
		
		// 이메일 정규식
		var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		// 핸드폰번호 정규식 (010-1234-1234)
		var regExpPhone = /^\d{3}-\d{4}-\d{4}$/;
		
		
		if(regExpEmail.test(memberaccount)) { // 이메일 형식이라면
			$("input[name='memberaccount']").attr({
				"name" : "memberemail"
			})
		
		} else if(regExpPhone.test(memberaccount)) {	// 핸드폰번호 형식이라면
			$("input[name='memberaccount']").attr({
				"name" : "memberphone"
			})
		
		}
		
	});
	
</script>
<!-- END :: MEMBER 기본 회원가입 -->

</html>