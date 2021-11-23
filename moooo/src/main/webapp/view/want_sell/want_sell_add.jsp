<%@page import="kr.co.sist.user.dao.WantSellDAO"%>
<%@page import="kr.co.sist.user.vo.CategoryVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.sist.user.dao.WantBuyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 추가 페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<!-- 서머노트에서 콜백함수 사용해서 이미지 등록하기 -->

<c:if test="${ empty sess_user_id }">
<c:redirect url="/users/login/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>


<style type="text/css">
.notice_title{margin: 0px auto; width: 1000px}
.note {margin: 0px auto; width: 1000px; margin-top: 20px; }

.title2{  margin-bottom: 20px; margin-top: 50px; font-weight: bold; font-size: 25px;}
.right_wrap {
	position: absolute;
	top: 0px;
	width: 70%;
	font-size: 14px;
	list-style: disc;
	margin-left: 10px;
}
#price{width:150px;}
</style>

<!--   <!-- summernote
  <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> -->
  
<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
  
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>  
<!-- 국제화 : 다른 나라의 언어로 서비스를 제공할 때 i18n -->
<script src="<%= protocol %><%= domain %><%= contextRoot %>/common/js/summernote/lang/summernote-ko-KR.js"></script>

<script>
$(document).ready(function() {
        $('#summernote').summernote({
			placeholder : '작성란',
			tabsize : 2,
			width: 1000,
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
$(function () {
	$("#add").click(function() {
		
		if( $("#title").val() == "" ){
			alert("제목을 입력해주세요.");
			return;
		}
		
		if( $("#summernote").val() == "" ){
			alert("내용을 입력해주세요.");
			return;
		}
		
		if( $("#price").val() == "" ){
			alert("가격을 입력해주세요.");
			return;
		}
		
		$("#frm").submit();
	}); //click
}); //ready
</script>
</head>
<body>
<%
request.setCharacterEncoding("UTF-8");
%>
<c:if test="${ empty param.title }">
<!-- 서머노트 -->
<!-- header -->
<jsp:include page="/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.jsp"/>
	
	<div id="right">
	<div class="right_wrap">
	<div style="margin: 0px auto; width: 900px;text-align: left; padding-bottom: 50px">
		<h3 class="title2">팔아요</h3>
	</div>

<form name="frm" id="frm" method="post">
<div class="notice_title">
<table>
<tr>
	<td colspan="5"><input type="text" class="form-control" placeholder="제목" name="title" id="title"></td>
</tr>
<tr>
	<td>가격 : </td>
	<td><input type="number" min="0" max="10000000000000000000" class="form-control" name="price" id="price" value="0"></td>
	<td>&nbsp;&nbsp;&nbsp;</td>
	<td>카테고리 : </td>
	<td>
	<c:catch var="e">
	<%
	List<CategoryVO> list=new WantSellDAO().selCategory();
	pageContext.setAttribute("list", list);
	%>
	<select name="category_id">
	<c:forEach var="list" items="${ list }">
	<option value="${ list.category_id }"><c:out value="${ list.name }"/></option>
	</c:forEach>
	</select>
	</c:catch>
	<c:if test="${ not empty e }">
	 ${ e }
	<!-- 문제발생 -->
	</c:if>
	</td>
</tr>
</table>
</div>
<div class="note">
	<textarea name="comments" id="summernote"></textarea>
</div>
<% 
String ip_addr=request.getRemoteAddr();
%>
<input type="hidden" name="ip_addr" value="<%= ip_addr %>"/>
<input type="hidden" name="user_id" value="${ sessionScope.id }"/>
</form>

	<div style="text-align: center">
		<input type="button" class="btn" value="작성 완료" id="add">
	</div>
	</div><!-- /<div class="right_wrap"> -->
</div><!-- /<div id="right"> -->
</div><!-- /<div id="container"> -->

<div style="clear:both;">
<jsp:include page="/layout/footer.jsp"/>
</div>
</c:if>

<c:if test="${ not empty param.title }">
<jsp:useBean id="wv" class="kr.co.sist.user.vo.WantSellVO" scope="page"/>
<jsp:setProperty property="*" name="wv"/>

<c:catch var="e">
<%
WantSellDAO wd=new WantSellDAO();
wd.insertSell(wv);
%>
<script type="text/javascript">
alert("팔아요 글을 추가했습니다.");
location.href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell.jsp";
</script>
</c:catch>
<c:if test="${ not empty e }">
<%-- ${ e } --%>
<c:redirect url="../../common/error/error.jsp"/>
</c:if>
</c:if>

</body>
</html>