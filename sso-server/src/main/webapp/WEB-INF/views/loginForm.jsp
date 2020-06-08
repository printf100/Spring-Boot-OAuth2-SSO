<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta name="_csrf" th:content="${_csrf.token}"/>
<meta name="_csrf_header" th:content="${_csrf.headerName}"/>

<meta charset="UTF-8">
<title>Insert title here</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<!-- START :: CSS -->

    <link rel="icon" type="image/png" href="/resources/images/icons/favicon.ico"/>

	<link rel="stylesheet" type="text/css" href="/resources/vendor/bootstrap/css/bootstrap.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/fonts/font-awesome-4.7.0/css/font-awesome.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/fonts/iconic/css/material-design-iconic-font.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/vendor/animate/animate.css">

	<link rel="stylesheet" type="text/css" href="/resources/vendor/css-hamburgers/hamburgers.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/vendor/animsition/css/animsition.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/vendor/select2/select2.min.css">

	<link rel="stylesheet" type="text/css" href="/resources/vendor/daterangepicker/daterangepicker.css">

	<link rel="stylesheet" type="text/css" href="/resources/css/util.css">
	<link rel="stylesheet" type="text/css" href="/resources/css/main.css">

<!-- END :: CSS -->

</head>
<body>
<div class="limiter">
		<div class="container-login100">
			<div class="wrap-login100 p-l-55 p-r-55 p-t-65 p-b-54">
			
				<form action="/loginProcess" method="post" class="login100-form validate-form">
					<span class="login100-form-title p-b-49">
						Devs <img src="/resources/images/logo.png" width="15%;"></img> Login
					</span>

					<div class="wrap-input100 validate-input m-b-23" data-validate="Username is reauired">
						<span class="label-input100">Username</span>
						<input class="input100" type="text" name="member_id" required="required" placeholder="Type your username">
						<span class="focus-input100" data-symbol="&#xf206;"></span>
					</div>

					<div class="wrap-input100 validate-input m-b-23" data-validate="Password is required">
						<span class="label-input100">Password</span>
						<input class="input100" type="password" name="member_password" required="required" placeholder="Type your password">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
						<span class="focus-input100" data-symbol="&#xf190;"></span>
					</div>
					
					<c:if test="${not empty sessionScope.errorMessage }">
						<div id="loginFailureMessage">
							<p style="color: red;">${sessionScope.errorMessage }
						</div>
					</c:if>
					
					<div class="container-login100-form-btn">
						<div class="wrap-login100-form-btn">
							<div class="login100-form-bgbtn"></div>
							<button class="login100-form-btn" type="submit" value="LOGIN">
								Login
							</button>
						</div>
					</div>
				</form>

				<div class="txt1 text-center p-t-54 p-b-20">
					<span>
						카카오톡 & 네이버 로그인
					</span>
				</div>

				<div class="flex-c-m">
					<a href="javascript:loginWithKakaoRest()" class="login100-social-item bg1" id="custom-login-btn">
						<img src="/resources/images/social/kakao/kakaolink_btn_medium.png"></img>
					</a> &nbsp &nbsp &nbsp &nbsp &nbsp &nbsp 

					<a href="javascript:loginWithNaverRest()" class="login100-social-item bg2">
						<img src="/resources/images/social/naver/naver.png" width="135%"></img>
					</a>

					
				</div>

				<div class="flex-col-c p-t-155">
					<span class="txt1 p-b-17">
						계정이 없으신가요?
					</span>

					<a href="/signup" class="txt2">
						<div class="container-login100-form-btn">
							<div class="wrap-login100-form-btn">
								<div class="login100-form-bgbtn"></div>
								<button class="login100-form-btn">
									Join
								</button>
							</div>
						</div>
					</a>
				</div>
				
			</div>
		</div>
	</div>

	<div id="dropDownSelect1"></div>
	
<!-- START :: JS import -->
<!--===============================================================================================-->
	<script src="/resources/vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="/resources/vendor/animsition/js/animsition.min.js"></script>
<!--===============================================================================================-->
	<script src="/resources/vendor/bootstrap/js/popper.js"></script>
	<script src="/resources/vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="/resources/vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="/resources/vendor/daterangepicker/moment.min.js"></script>
	<script src="/resources/vendor/daterangepicker/daterangepicker.js"></script>
<!--===============================================================================================-->
	<script src="/resources/vendor/countdowntime/countdowntime.js"></script>
<!--===============================================================================================-->
	<script src="/resources/js/main.js"></script>
<!-- END :: JS import -->

<script type="text/javascript">
	$(function() {
		
		$("input[name='member_id']").keyup(function() {
			$("#loginFailureMessage").remove();
		});
		
		$("input[name='member_password']").keyup(function() {
			$("#loginFailureMessage").remove();
		});
		
	});
</script>
	
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
			const url = '${kakaoLoginLink}?client_id=${kakaoRestApiKey}&redirect_uri=http://${ssoDomain}:${serverPort}/kakaoOauth&response_type=code';
// 			const url = 'https://kauth.kakao.com/oauth/authorize?client_id=4be0db1fcb83bc9cf8c11a9fbca76507&redirect_uri=http://3.136.253.121:8585/kakaoOauth&response_type=code';
			location.href=url;
		}
	</script>
<!-- END :: KAKAO LOGIN -->

<!-- START :: NAVER LOGIN -->
	<script type="text/javascript">
		function loginWithNaverRest(){
			const url = '${naverLoginLink}?response_type=code&client_id=${naverRestApiKey}&redirect_uri=http://${ssoDomain}:${serverPort}/naverOauth&state=${naverOauthState}';
// 			const url = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=ZnB1hkTBDYaRrVgb5rpz&redirect_uri=http://3.136.253.121:8585/naverOauth&state=550e8400-e29b-41d4-a716-446655440000';
			location.href=url;
		}
	</script>
<!-- END :: NAVER LOGIN -->

</html>