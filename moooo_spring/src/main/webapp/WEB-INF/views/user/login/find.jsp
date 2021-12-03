<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="아이디 비밀번호 찾기 페이지"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= title %></title>

 <!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%= commonUrl %>/common/css/find.css"/>
</head>

<body>
<div id="find_wrap">
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="find_container">
<div class="find_img">
<img alt="findLogin_sist" src="<%= commonUrl %>/common/images/find.png">
</div>

<div class="container-mid">
<form class="form-signin" action="<%= commonUrl %>/users/login/process/find_id_process.jsp" method="post">
<div id="left">
<table>
	<tr><td><span><strong>아이디 찾기</strong><br/>회원님의 닉네임과 휴대폰번호를 입력해주세요.</span></td></tr>
	<tr><td>
	<div class="input-group input-group">
  <span class="input-group-addon glyphicon glyphicon-user" id="sizing-addon1"></span>
  <input type="text" class="form-control" name="nickname" placeholder="닉네임" aria-describedby="sizing-addon1" required>
</div>
	</td></tr>
	<tr><td>
	<div class="input-group input-group">
  <span class="input-group-addon" id="sizing-addon1">@</span>
  <input type="text" id="phone" class="form-control" name="phone" placeholder="휴대폰번호" aria-describedby="sizing-addon1" required>
</div>
	</td></tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td><button type="submit" id="find_id" name="find_id" class="btn">확인</button></td></tr>
</table>
</div>
</form>

<form action="<%= commonUrl %>/users/login/process/find_pass_process.jsp" method="post">
<div id="right">
<table>
	<tr><td><span><strong>비밀번호 찾기</strong><br/>회원님의 닉네임과 아이디, 휴대폰번호를 입력해주세요.</span></td></tr>
	<tr><td>
	<div class="input-group input-group">
  <span class="input-group-addon glyphicon glyphicon-user" id="sizing-addon1"></span>
  <input type="text" class="form-control" name="nickname" placeholder="닉네임" aria-describedby="sizing-addon1" required>
</div>
	</td></tr>
	<tr><td>
	<div class="input-group input-group">
  <span class="input-group-addon" id="sizing-addon1"><img src="<%= commonUrl %>/common/images/icons/pass_icon.PNG" width="15px"/></span>
  <input type="text" class="form-control"  id="id" name="user_id" placeholder="아이디" aria-describedby="sizing-addon1" required>
</div>
	</td></tr>
	<tr><td>
	<div class="input-group input-group">
  <span class="input-group-addon" id="sizing-addon1">@</span>
  <input type="text" id="phone" class="form-control" name="phone" placeholder="휴대폰번호" aria-describedby="sizing-addon1" required>
</div>
	</td></tr>
	<tr><td><input type="submit" id="find_pass" name="find_pass" class="btn" value="확인"></td></tr>
</table>
</div>
</form>
</div>
</div>

<!-- footer -->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>