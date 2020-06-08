<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>
<meta charset="EUC-KR">
<title>Insert title here</title>


<!-- START :: JS import -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<!-- END :: JS import -->

<!-- START :: CSS -->
	<!-- 이거는 -또는- 이거라서 ㅎㅎ.. -->
<style type="text/css">
.hr-sect {
	display: flex;
	flex-basis: 100%;
	align-items: center;
	color: rgba(0, 0, 0, 0.35);
	font-size: 12px;
	margin: 8px 0px;
}
   
.hr-sect::before, .hr-sect::after {
	content: "";
	flex-grow: 1;
	background: rgba(0, 0, 0, 0.35);
	height: 1px;
	font-size: 0px;
	line-height: 0px;
	margin: 0px 16px;
}
</style>
<!-- END :: CSS -->

</head>
<body>
	<section>
	 	
		
		<div>
			<h1>DEVS</h1>
			
			<div>				
				<form action="/loginProcess" method="post">			
					<!-- 이메일 -->
					<input type="text" name="member_id" required="required" placeholder="전화번호, 사용자 이름 또는 이메일">
					
					<!-- 비밀번호 -->
					<input type="password" name="member_password" required="required" placeholder="비밀번호">
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					
					<!-- 로그인 버튼 -->
					<input type="submit" value="LOGIN">
				</form>
				
			</div>
			
			<div class="hr-sect">또는</div>
			
			<div>
				<span>
					<a id="custom-login-btn" href="javascript:loginWithKakaoRest()">
						<img src="/resources/images/social/kakao/kakaolink_btn_medium.png" width="100px;"/> 
					</a>
				</span>

				<a href="javascript:loginWithNaverRest()">
					<img src="/resources/images/social/naver/naver_login.PNG" width="223" />
				</a>
				
								
				<div>
					<a href="#">비밀번호를 잊으셨나요?</a>
				</div>
			</div>			
			
		</div>

	 	<div>
			계정이 없으신가요?
			<a href="/signup">&nbsp;가입하기 </a>
		</div>
	</section>

	
	<section>
		<h1>사이트소개</h1>
		
		<div>
	
		</div>
	</section>	
	
</body>

<!-- START :: application.yml 참조 -->
	<spring:eval expression="@environment.getProperty('server.port')" var="serverPort"/>
	<spring:eval expression="@environment.getProperty('ssoDomain')" var="ssoDomain"/>
	
	<spring:eval expression="@environment.getProperty('social-link.kakao-login-link')" var="kakaoLoginLink"/>
	<spring:eval expression="@environment.getProperty('social-link.kakao-rest-api-key')" var="kakaoRestApiKey"/>
	
	<spring:eval expression="@environment.getProperty('social-link.naver-login-link')" var="naverLoginLink"/>
	<spring:eval expression="@environment.getProperty('social-link.naver-rest-api-key')" var="naverRestApiKey"/>
	<spring:eval expression="@environment.getProperty('social-link.naver-oauth-state')" var="naverOauthState"/>
<!-- END :: application.yml 참조 -->

<!-- START :: KAKAO LOGIN -->
	<script type="text/javascript">
		function loginWithKakaoRest(){	
			location.href='${kakaoLoginLink}?client_id=${kakaoRestApiKey}&redirect_uri=http://${ssoDomain}:${serverPort}/kakaoOauth&response_type=code';
		}
	</script>
<!-- END :: KAKAO LOGIN -->

<!-- START :: NAVER LOGIN -->
	<script type="text/javascript">
		function loginWithNaverRest(){
			location.href='${naverLoginLink}?response_type=code&client_id=${naverRestApiKey}&redirect_uri=http://${ssoDomain}:${serverPort}/naverOauth&state=${naverOauthState}';
		}
	</script>
<!-- END :: NAVER LOGIN -->

</html>