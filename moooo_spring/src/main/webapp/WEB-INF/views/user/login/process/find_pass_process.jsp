<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="비밀번호 찾기 처리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<link rel="stylesheet" href="<%= commonUrl %><%= common_css %>/find.css"/>

<!-- jQuery CDN -->
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

<div class="find_img">
<img alt="find" src="<%= commonUrl %>/common/images/find.png">
</div>

<c:choose>
<c:when test="${ not empty mVO }">
<div class="container-mid result-mid">
<span><strong>${ mVO.getNickname() }</strong>님의 임시비밀번호입니다.<br/>
임시비밀번호 : <strong><c:out value="${ mVO.getPass() }"/></strong></span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= commonUrl %>/user/login/login.do">로그인</a>
</div>
</c:when>

<c:otherwise>
<div class="container-mid result-mid">
<span>입력하신 데이터에 일치하는 회원정보가 존재하지 않습니다.</span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= commonUrl %>/user/login/find_form.do">확인</a>
</div>
</c:otherwise>
</c:choose>

<!-- footer -->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</body>
</html>