<%@page import="kr.co.sist.user.vo.WantSellVO"%>
<%@page import="kr.co.sist.user.dao.WantSellDAO"%>
<%@page import="kr.co.sist.user.vo.CategoryVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.sist.user.dao.WantBuyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 추가/수정"
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
.note {margin: 20px auto; }
.title{ font-weight: bold; font-size: 25px;}

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

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
  
  <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.css" rel="stylesheet">
  <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote.min.js"></script>  
<!-- 국제화 : 다른 나라의 언어로 서비스를 제공할 때 i18n -->
<script src="<%= commonUrl %>/common/js/summernote/lang/summernote-ko-KR.js"></script>

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
        	    ['insert', ['link', 'picture']]//, 'video']]
        	  ]
        });
}); //ready
</script>
<script type="text/javascript">
$(function () {
	$("#add").click(function() {
		if( $("#add_title").val() == "" ){
			alert("제목을 입력해주세요.");
			$("#add_title").focus();
			return;
		}
		
		if( $("#add_title").val().length > 50 ){
			alert("제목은 50자 이내로 작성해주세요.");
			$("#add_title").focus();
			return;
		}
		
		if( $("#price").val() == "" ){
			alert("가격을 입력해주세요.");
			return;
		}
		
		if($("#category_id option:selected").val() == "none"){
			alert("카테고리를 선택해주세요.")
			return;
		}
		
		if( $("#summernote").val() == "" ){
			alert("내용을 입력해주세요.");
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
<c:catch var="e">
<%
String sess_user_id=(String)session.getAttribute("sess_user_id");

String sell_id=request.getParameter("sell_id");

if(sell_id != null){
	WantSellDAO wd=new WantSellDAO();
	WantSellVO wv=wd.selEditSell(Integer.parseInt(sell_id),sess_user_id);
	pageContext.setAttribute("wv", wv);
}//end if

pageContext.setAttribute("sell_id", sell_id);
%>
<!-- header -->
<jsp:include page="/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.jsp"/>
	
	<div id="right">
	<div class="right_wrap">
	<div style="margin: 0px auto; width: 900px;text-align: left; padding-bottom: 50px">
		<h3 class="title2">팔아요 작성</h3>
	</div>

<form name="frm" id="frm" method="post">
<div>
<table class="table" style="width: 1000px;">
<tr>
	<td>제목 : </td>
	<td colspan="4">
		<input type="text" class="form-control" placeholder="50자 이내로 작성해주세요." name="title" id="add_title" <% 
		if(sell_id != null){ %>value="${ wv.title }"<%}%>>
	</td>
</tr>
<tr>
	<td>가격 : </td>
	<td>
		<input type="number" min="0" max="10000000000000000000" class="form-control" name="price" id="price" <% if(sell_id != null){ 
		%>value="${ wv.price }"<% } else { %> value="0"<% } %>></td>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td>카테고리 : </td>
	<td>
	<c:catch var="e">
	<%
	List<CategoryVO> list=new WantSellDAO().selCategory();
	pageContext.setAttribute("list", list);
	%>
	<select id="category_id" name="category_id" class="form-control">
	<% 
	if(sell_id == null){ 
	%>
	<option value="none"><c:out value="-------- 선택 --------"/></option>
	<c:forEach var="list" items="${ list }">
	<option value="${ list.category_id }"><c:out value="${ list.name }"/></option>
	</c:forEach>
	<%
	} else {
	%>
	<% String selected=""; %>
	<c:forEach var="list" items="${ list }">
	<c:choose>
	<c:when test="${ list.category_id eq wv.category_id }">
	<% selected="selected='selected'"; %>
	</c:when>
	<c:otherwise>
	<% selected=""; %>
	</c:otherwise>
	</c:choose>
	<option value="${ list.category_id }" <%= selected %>><c:out value="${ list.name }"/></option>
	</c:forEach>
	<% } //end else %>
	</select>
	</c:catch>
	<c:if test="${ not empty e }">
	 <%-- ${ e } --%>
	문제발생
	</c:if>
	</td>
</tr>
</table>
</div>
<div class="note">
	<textarea name="comments" id="summernote"><% if(sell_id != null) { %>${ wv.comments }<% } %></textarea>
</div>
<% if(sell_id == null){ %>
<input type="hidden" name="type" value="add"/>
<% } else { %>
<input type="hidden" name="type" value="edit"/>
<%-- <input type="hidden" name="sell_id" value="${ sell_id }"/> --%>
<% } //end else %>

<% 
String ip_addr=request.getRemoteAddr();
%>
<input type="hidden" name="ip_addr" value="<%= ip_addr %>"/>
<input type="hidden" name="user_id" value="${ sessionScope.sess_user_id }"/>
</form>

	<div style="text-align: center">
		<input type="button" class="btn" value="작성 완료" id="add">
	</div>
	</div><!-- /<div class="right_wrap"> -->
</div><!-- /<div id="right"> -->
</div><!-- /<div id="container"> -->
	</c:catch>
	<c:if test="${ not empty e }">
	 <%-- ${ e } --%>
	<script type="text/javascript">
	alert("작성자만 수정할 수 있습니다.");
	location.href="javascript:history.back()";
	</script>
	</c:if>

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

String url=commonUrl+"/view/want_sell/want_sell.jsp";
if( "add".equals(request.getParameter("type")) ) {
	wd.insertSell(wv);
} else {
	wd.updateSell(wv);
	url=commonUrl+"/view/want_sell/want_sell_detail.jsp?sell_id="+wv.getSell_id();
} //end else
pageContext.setAttribute("url", url);
%>
<script type="text/javascript">
alert("팔아요 글이 등록됐습니다.");
location.href="${ url }";
</script>
</c:catch>
<c:if test="${ not empty e }">
<%-- ${ e } --%>
<c:redirect url="../../common/error/error.jsp"/>
</c:if>
</c:if>

</body>
</html>