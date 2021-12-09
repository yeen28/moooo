<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자-이용방법 수정 페이지"
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

#headTitle{font-size: 30px;margin-right: 50px;}
#saveBtn{width: 100px; height:40px; font-size: 16px; font-weight: bold;}
</style>
  
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>  
<!-- 국제화 : 다른 나라의 언어로 서비스를 제공할 때 i18n -->
<script src="<%= commonUrl %>/common/js/summernote/lang/summernote-ko-KR.js"></script>

<script>
$(document).ready(function() {
        $('#summernote').summernote({
			placeholder : '작성란',
			tabsize : 2,
			width: 1100,
			height: 500,
			lang: 'ko-KR',
        	toolbar: [
        	    // [groupName, [list of button]]
        	    ['style', ['bold']],
        	    ['font', ['strikethrough']],
        	    ['fontsize', ['fontsize']],
        	    ['color', ['color']],
        	    ['table',['table']],
        	    ['insert', ['link', 'picture', 'video']]
        	  ]
        });
}); //ready
</script>
<script type="text/javascript">
$(function() {
	$("#saveBtn").click(function() {
		if(confirm("저장하시겠습니까?")){
			$("#frm").submit();
		}//end if
	}); //click
}); //ready
</script>
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
%> --%>
<div class="right">

<div><span id="headTitle">이용방법 수정</span><input type="button" id="saveBtn" value="저장"/></div>
<br/>
<form action="proc/proc_how_edit.do" id="frm" method="">
<div class="edit">
	<textarea name="comments" id="summernote"><c:out value="${ comments }"/></textarea>
</div>
</form>
</div>
</body>
</html>