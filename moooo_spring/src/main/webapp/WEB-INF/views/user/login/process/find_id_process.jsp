<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="아이디 찾기 성공"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= title %></title>
<link rel="stylesheet" href="<%= commonUrl %>/common/css/find.css"/>

 <!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>

<body>

<div id="find_wrap">
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div class="find_container">

<div style="background-color: rgba( 255, 255, 255, 0.7 );">
<div class="find_img">
<img alt="find" src="<%= commonUrl %><%= common_images %>/find.png">
</div>

<c:choose>
<c:when test="${ not empty mVO }">
<div class="container-mid result-mid">
<span><strong><c:out value="${ mVO.getNickname() }"/></strong>님의 아이디입니다.<br/>
아이디 : <strong><c:out value="${ mVO.getUser_id() }"/></strong></span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= commonUrl %>/user/login/login.do">로그인</a>
</div>
</c:when>

<c:otherwise>
<div class="container-mid result-mid">
<span>이름과 이메일을 확인해주세요.</span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= commonUrl %>/user/login/find_form.do">확인</a>
</div>
</c:otherwise>
</c:choose>
</div>
</div>

<!-- footer -->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</body>
</html>