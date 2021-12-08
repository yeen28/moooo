<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자-이용방법 수정 페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>   

<c:if test="${ empty sess_id }">
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
.top-nav{height: 50px; background-color: #2D394A;}
.logo{float:left; width: 100px; margin-left: 10px;}
.logo>h3>a{color:#fff;}
.logo>h3>a:hover{text-decoration: none;}

.left-nav{width: 300px; height: 100%; background-color: #CFCFCF;position: absolute;}
.left-nav>ul{list-style: none;padding-top:30px;}
a{color: #333;}
.menu{border:1px solid #CFCFCF;}

.editWrap{ 
	overflow: auto; 
	width: 960px;
	height: 550px;
	border: 2px solid #F9F8F8;
	padding: 35px; font-size: 17px; margin-top: 30px; background-color: #fff; }
</style>
</head>

<body style="background-color: #DFDFDF;">
<jsp:include page="layout/header.jsp"/>

<!-- 왼쪽 메뉴바 -->
<div class="left-nav">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/main.do"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_user.do"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation" class="active"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_how_to.do"><span class="glyphicon glyphicon-pencil">&nbsp;이용방법수정</span></a></li>
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_notice.do"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
<!--   <li role="presentation"><a href="#">동네이야기 관리</a></li> -->
  <li role="presentation"><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/change_pass.do"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
<li></li>
</ul>
</div>
<!-- /왼쪽 메뉴바 -->

<%-- <%
HowToDAO hd=new HowToDAO();
String comments=hd.selHowTo();
pageContext.setAttribute("comments", comments);
%> --%>
<div class="right" style="width: 1000px;">

<h2>이용방법</h2>
<a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_how_to_edit.do">수정하기</a>
<br/>
<div>
<div class="editWrap">
	<c:out value="${ comments }" escapeXml="false"/>
</div>
</div>
</div>
</body>
</html>