<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    info="로그인페이지"%>
<%@ include file="/common/jsp/common_code.jsp" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

 <!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

 <link href="<%= commonUrl %>/common/css/login.css" rel="stylesheet">
 
 <script type="text/javascript">
$(function() {
	
	$("#loginBtn").click(function() {
		if($("#inputId").val() != ""){
			$("#idView").html("");
		}
		if($("#inputPassword").val() != ""){
			$("#passView").html("");
		}
		
		if($("#inputId").val() == ""){ //아이디가 비었을 때
			$.ajax({
				dataType: "text",
				success: function( textObj ) {
					$("#idView").html("<span style='color:#FF0000;'>아이디를 입력해주세요.</span>");
					$("#inputId").focus();
				}
			}); //ajax
			return;
		} //end if
		
		if($("#inputPassword").val() == ""){ //비밀번호가 비었을 때
			$.ajax({
				dataType: "text",
				success: function( textObj ) {
					$("#passView").html("<span style='color:#FF0000;'>비밀번호를 입력해주세요.</span>");
					$("#inputPassword").focus();
				}
			}); //ajax
			return;
		} //end if
	}); //click
	
}); //ready
</script>
</head>

<body>
<div id="wrap">
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div id="container">
	<form class="form-signin" action="<%= commonUrl %>/user/login/process/login_process.jsp" method="post">
        <h2 id="signin-heading"><img src="<%= commonUrl %>/common/images/icons/login_icon.PNG" width="50px"/>&nbsp;<strong>로그인</strong></h2>
        <label>아이디</label>
        <input type="text" id="inputId" name="user_id" class="form-control" placeholder="아이디" autofocus>
        <div id="idView"></div>
		<label style="margin-top: 10px">비밀번호</label>
		<input type="password" id="inputPassword" name="pass" class="form-control" placeholder="비밀번호">
        <div id="passView"></div>
		<button class="btn btn-lg btn-block" type="button" id="loginBtn">로그인하기</button><!-- AJAX로 하기 -->
	</form>
	
	<div id="signin-bottom">
	<a href="<%= commonUrl %>/user/login/sign_up.do">회원가입</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<a href="<%= commonUrl %>/user/login/find_form.do">아이디/비밀번호 찾기</a>
	</div>
</div>

<!-- footer -->
</div>

<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>