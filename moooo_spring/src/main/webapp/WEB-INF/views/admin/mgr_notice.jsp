<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 공지사항 관리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sessionScope.admin_id }">
<c:redirect url="/admin/login.do"/>
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
  <li role="presentation"><a href="<%= commonUrl %>/admin/main.do"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_user.do"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_how_to.do"><span class="glyphicon glyphicon-pencil">&nbsp;이용방법수정</span></a></li>
  <li role="presentation" class="active"><a href="<%= commonUrl %>/admin/mgr_notice.do"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/change_pass.do"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
<li></li>
</ul>
</div>
<!-- /왼쪽 메뉴바 -->

<div class="right">
<h2>공지사항 관리</h2>
<br/>
<div class="search">
<form class="navbar-form navbar-left" role="search">
  <div class="form-group">
    <label>제목 : </label>
    <input type="text" class="form-control" placeholder="Search" style="height: 28px;">
  </div>
  <button type="submit" class="btn btn-default" style="height: 28px;font-size:13px;"><span class="glyphicon glyphicon-search"></span></button>
</form>
</div>
<br/>
<table class="table table-hover">
<thead>
<tr>
<th>번호</th>
<th>제목</th>
<th>작성자</th>
<th>조회수</th>
<th>등록일</th>
</tr>
</thead>
<c:if test="${ empty noticeList }">
<tr>
<td>조회된 결과가 없습니다.</td>
</tr>
</c:if>
<c:forEach var="list" items="${ noticeList }">
<tr>
<td><c:out value="${ list.notice_id }"/></td>
<td><a href="${ commonUrl }/admin/mgr_notice_detail.do?notice_id=${ list.notice_id }"><c:out value="${ list.title }"/></a></td>
<td><c:out value="${ list.admin_id }"/></td>
<td><c:out value="${ list.view_cnt }"/></td>
<td><c:out value="${ list.input_date }"/></td>
</tr>
</c:forEach>
</table>
</div>
</body>
</html>