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
				
					<fieldset>
						<legend>optional</legend>
						
						<!-- 이메일 -->
						<input type="text" name="memberaccount" placeholder="휴대폰 번호 또는 이메일 주소">
						<div id="emailchk"></div>
						
						<!-- 성명 -->
						<input type="text" name="membername" placeholder="사용자 이름">
					</fieldset>
					
					<fieldset>
						<legend>essential</legend>
						
						<!-- 아이디 -->
						<input type="text" name="memberid" required="required" placeholder="사용자 아이디">
						<div id="idchk"></div>
						
						<!-- 비밀번호 -->
						<input type="password" name="memberpassword" required="required" placeholder="비밀번호">
					</fieldset>
					
					<!-- 회원가입 버튼 -->					
					<input type="submit" value="JOIN">
					<!-- 유효성검층 출력부분 -->
					
				</form>

			</div>		
			
		</div>

	 
 		<div>
			계정이 있으신가요?
			<a href="/loginForm">&nbsp;로그인 </a>
		</div>
	</section>
</body>

<!-- START :: submit 버튼 disabled handler -->
<script type="text/javascript">

	var memberaccount;	// email 또는 phone의 input name 설정

	// null 및 공백 체크, 유효성 체크
	var memberaccountBool = true;
	var membernameBool = true;
	var memberidBool = false;
	var memberpasswordBool = false;

	$(function() {
		$("input[type='submit']").attr("disabled", "disabled");

		// input name의 태그가 변경됨에 따라 존재하는지 여부
		if($("input[name='memberaccount']").length > 0) {	memberaccount = $("input[name='memberaccount']");}
		if($("input[name='memberemail']").length > 0) {	memberaccount = $("input[name='memberemail']");	}
		if($("input[name='memberphone']").length > 0) { memberaccount = $("input[name='memberphone']");}
		
		// 유효성 검증 후 submit 버튼 disable handler 호출
		memberaccount.keyup(function() {disableHandler();});
		$("input[name='membername']").keyup(function(){disableHandler()});
		$("input[name='memberid']").keyup(function() {disableHandler();});
		$("input[name='memberpassword']").keyup(function() {disableHandler();});
	});
	
	// submit 버튼 disable handler
	function disableHandler() {
		
		if($("input[name='memberpassword']").val() != null && $("input[name='memberpassword']").val() != "") {
			memberpasswordBool = true;
		} else {
			memberpasswordBool = false;
		}
		
		if(memberaccountBool && membernameBool && memberidBool && memberpasswordBool) {
			console.log(memberaccountBool + ":" + membernameBool + ":" + memberidBool + ":" + memberpasswordBool);
			$("input[type='submit']").removeAttr("disabled");

		} else {
			console.log(memberaccountBool + ":" + membernameBool + ":" + memberidBool + ":" + memberpasswordBool)
			$("input[type='submit']").attr("disabled", "disabled");
		}
	}		                                                             

</script>
<!-- END :: submit 버튼 disabled handler -->

<!-- START :: id 중복검사 -->
<script type="text/javascript">

	$(function(){

		$("input[name='memberid']").keyup(function(){
			$("#idchk").text("");
			
			if($("input[name='memberid']").val() != null && $("input[name='memberid']").val() != "") {
				
				var memberid = $("input[name='memberid']").val().trim();
				
				var joinVal = {
					"memberid" : memberid
				}
				
				$.ajax({
					type: "post",
					url: "/idCheck",
					data: JSON.stringify(joinVal),
					contentType: "application/json",
					dataType: "json",
					
					success: function(msg){
						
						if (msg.check == true) {
							$("#idchk").text("이미 존재하는 ID 입니다.").css("color","red");
							memberidBool = false;
						} else {
							$("#idchk").text("사용가능한 ID 입니다.").css("color","green");
							memberidBool = true;
						}
						
						disableHandler(); // submit 버튼 disabled handler
					},
					
					error: function(){
						alert("통신실패");
					}
				});
				
			} else {
				memberidBool = false;
				disableHandler(); // submit 버튼 disabled handler
			}
			
		});
		
	})
</script>
<!-- END :: id 중복검사 -->

<!-- START :: email, phone 타입체크, 중복검사 -->
<script type="text/javascript">

	$(function() {

		// 이메일 정규식
		var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		// 핸드폰번호 정규식 (010-1234-1234)
		var regExpPhone = /^\d{3}-\d{4}-\d{4}$/;
		
		memberaccount.keyup(function() {
			$("#emailchk").text("");
			
			accountValue = memberaccount.val();
			
			if(accountValue != null && accountValue != "") {
				
				if(regExpEmail.test(accountValue)) { // 이메일 형식이라면
					memberaccount.attr({
						"name" : "memberemail"
					});
				
					var joinVal = {
						"memberemail" : accountValue
					}
				
					// 이메일 중복 검사
					$.ajax({
						type: "post",
						url: "/emailCheck",
						data: JSON.stringify(joinVal),
						contentType: "application/json",
						dataType: "json",
						
						success: function(msg){
							
							if (msg.check == true) {
								$("#emailchk").text("이미 존재하는 EMAIL 입니다.").css("color","red");
								memberaccountBool = false;
							} else {
								$("#emailchk").text("사용가능한 EMAIL 입니다.").css("color","green");
								memberaccountBool = true;
							}
							
							disableHandler(); // submit 버튼 disabled handler
						},
						
						error: function(){
							alert("통신실패");
						}
					});
				
				} else if(regExpPhone.test(accountValue)) {	// 핸드폰번호 형식이라면
					$("#joinchk").hide();
				
					memberaccount.attr({                                                                                                                                                                                                                                                                               
						"name" : "memberphone"
					})
				}
				
			} else {
				memberaccountBool = true;
				disableHandler(); // submit 버튼 disabled handler
			}
			
		});
		
	});
	
</script>
<!-- END :: email, phone 타입체크, 중복검사 -->

<!-- START :: form submit -->
<script type="text/javascript">

	$(function() {
		
		$("#joinForm").submit(function(e) {
			
			if($("input[name='memberphone']").length > 0) {
				var phoneValue = $("input[name='memberphone']").val();
				 $("input[name='memberphone']").val(phoneValue.split("-").join(""));
			}
			
			return true;
		});
		
	});

</script>
<!-- END :: form submit -->

</html>