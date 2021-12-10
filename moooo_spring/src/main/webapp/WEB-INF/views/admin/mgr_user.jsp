<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 회원관리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sessionScope.admin_id }">
<c:redirect url="/admin/login_form.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
body{height:100%;}

.right { position: absolute; top: 100px; left: 450px; }

.left-nav{width: 300px; height: 100%; background-color: #CFCFCF;position: absolute;}
.left-nav>ul{list-style: none;padding-top:30px;}
a{color: #333;}
.menu{border:1px solid #CFCFCF;}
</style>

<script type="text/javascript">

</script>
</head>
<body style="background-color: #DFDFDF;">
<jsp:include page="layout/header.jsp"/>

<!-- 왼쪽 메뉴바 -->
<div class="left-nav">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="<%= commonUrl %>/admin/main_form.do"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation" class="active"><a href="<%= commonUrl %>/admin/mgr_user.do"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_how_to.do"><span class="glyphicon glyphicon-pencil">&nbsp;이용방법수정</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_notice.do"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/change_pass.do"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
<li></li>
</ul>
</div>
<!-- /왼쪽 메뉴바 -->

<div class="right">
<h2>회원관리</h2>
<br/>
<div class="search">
<form class="navbar-form navbar-left" role="search">
  <div class="form-group">
    <label>아이디 : </label>
    <input type="text" class="form-control" placeholder="Search" style="height: 28px;">
  </div>
  <button type="submit" class="btn btn-default" style="height: 28px;font-size:13px;"><span class="glyphicon glyphicon-search"></span></button>
</form>
</div>
<br/>
<table class="table table-hover" style="text-align: center;">
<thead>
<tr>
<th>이미지</th>
<th>아이디</th>
<th>닉네임</th>
<th>휴대폰번호</th>
<th>신고누적수</th>
<th>최초가입일</th>
</tr>
</thead>
<c:if test="${ empty memberList }">
<tr>
<td>조회된 목록이 없습니다.</td>
</tr>
</c:if>
<c:forEach var="list" items="${ memberList }">
<tr>
<td><c:out value="${ list.img }"/></td>
<td><c:out value="${ list.user_id }"/></td>
<td><c:out value="${ list.nickname }"/></td>
<td><c:out value="${ list.phone }"/></td>
<td><c:out value="${ list.reported_cnt }"/></td>
<td><c:out value="${ list.input_date }"/></td>
</tr>
</c:forEach>
</table>
</div>
</body>
</html>