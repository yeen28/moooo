<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 회원관리"
    %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>
<link rel="stylesheet" type="text/css" href="http://localhost/jsp_prj/common/css/main_v20211012.css"/>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
.right { position: absolute; top: 100px; left: 450px; }

.left-nav{width: 300px; height: 700px; background-color: #CFCFCF;position: absolute;}
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
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/main.jsp"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation" class="active"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_user.jsp"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_notice.jsp"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
<!--   <li role="presentation"><a href="#">동네이야기 관리</a></li> -->
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/change_pass.jsp"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
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
<th>아이디</th>
<th>최초가입일</th>
<th>신고누적수</th>
</tr>
</thead>
<tr>
<td>회원1</td>
<td>회원1</td>
<td>회원1</td>
</tr>
<tr>
<td>회원111111</td>
<td>회원111111</td>
<td>회원111111</td>
</tr>
</table>
</div>
</body>
</html>