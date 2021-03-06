<%@page import="kr.co.sist.admin.vo.NotiInsertVO"%>
<%@page import="kr.co.sist.admin.dao.NoticeDAO"%>
<%@page import="java.sql.SQLException"%>
<%@page import="kr.co.sist.user.vo.WantSellVO"%>
<%@page import="kr.co.sist.user.dao.WantSellDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="공지사항 상세페이지"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sess_id }">
<c:redirect url="${ commonUrl }/admin/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>팔아요</title>

<!--jQuery CDN-->
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

.notice_wrap {
	font-size: 14px;
	margin-left: 160px 10px 40px 10px;
}
.noticeBtn {
	font-size: 17px; 
	background-color: #494949; 
	color: #FFFFFF;
	border: 1px solid #494949; 
	border-radius: 3px; 
	width: 90px;
	margin-top: 40px;
	padding: 8px;
}
</style>

<script type="text/javascript">
function notice(){
	location.href="<%= commonUrl %>/view/want_sell/want_sell.jsp";
}//notice
</script>
</head>

<body style="background-color: #DFDFDF;">
<jsp:include page="layout/header.jsp"/>

<!-- 왼쪽 메뉴바 -->
<div class="left-nav">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="<%= commonUrl %>/admin/main.jsp"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_user.jsp"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_how_to.jsp"><span class="glyphicon glyphicon-pencil">&nbsp;이용방법수정</span></a></li>
  <li role="presentation" class="active"><a href="<%= commonUrl %>/admin/mgr_notice.jsp"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
<!--   <li role="presentation"><a href="#">동네이야기 관리</a></li> -->
  <li role="presentation"><a href="<%= commonUrl %>/admin/change_pass.jsp"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
<li></li>
</ul>
</div>
<!-- /왼쪽 메뉴바 -->

<c:catch var="e">
<%
int notice_id = Integer.parseInt(request.getParameter("notice_id"));

NoticeDAO wd = new NoticeDAO();
NotiInsertVO wv = wd.selectNotice(notice_id);
%>
<div class="right">
<h2>공지사항 관리</h2>
<br/>
	<div class="notice_wrap">
			<div class="notice_content">
				<table class="table table-bordered">
				<tbody>
				<tr>
					<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
					<td colspan="5" style="font-size: 16px; color: #333;"><%= wv.getTitle() %></td>
				</tr>
				<tr>
					<td style="font-weight: bold; font-size: 16px; color: #333;">작성자</td>
					<td style="font-size: 16px; color: #333;"><%= wv.getAdmin_id() %></td>
					<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
					<td style="font-size: 16px; color: #333;"><%= wv.getInput_date() %></td>
					<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
					<td style="font-size: 16px; color: #333;"><%= wv.getView_cnt() %></td>
				</tr>
				<tr>
					<td colspan="8" style="font-size: 16px; color: #333;"><%= wv.getComments() %></td>
				</tr>
				</tbody>
				</table>
			</div>
				<a href="<%= commonUrl %>/admin/mgr_notice.jsp" class="noticeBtn">목록</a>
				<a href="<%= commonUrl %>/admin/mgr_notice_write.jsp?notice_id=<%= wv.getNotice_id() %>" class="noticeBtn">수정</a>
				<a href="<%= commonUrl %>/admin/mgr_notice_delete.jsp?notice_id=<%= wv.getNotice_id() %>" class="noticeBtn">삭제</a>
			</div>
		</div>
</c:catch>
<c:if test="${ not empty e }">
<c:redirect url="${ commonUrl }/common/error/error.jsp"/>
</c:if>
</body>
</html>