<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 추가/수정"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<!-- 서머노트에서 콜백함수 사용해서 이미지 등록하기 -->

<c:if test="${ empty user_id }">
<c:redirect url="/user/login/login.do"/>
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

.right_wrap { position: absolute; top: 0px; width: 70%; font-size: 14px; list-style: disc; margin-left: 10px; }
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

//이미지 업로드한 거 보이기
function readURL(input) {
	if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function (e) {
	        $('#viewImg').attr('src', e.target.result);
	    }
	    reader.readAsDataURL(input.files[0]);
	}//end if
}//readURL
</script>
</head>
<body>
<c:if test="${ empty param.title }">
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.do"/>
	
	<div id="right">
	<div class="right_wrap">
	<div style="margin: 0px auto; width: 900px;text-align: left; padding-bottom: 50px">
		<h3 class="title2">팔아요 작성</h3>
	</div>

<form name="frm" id="frm" method="post" action="ws_write_proc.do">
<c:if test="${ not empty param.sell_id }">
<input type="hidden" name="sell_id" value="${ param.sell_id }"/>
</c:if>
<div>
<table class="table" style="width: 1000px; background-color: rgba( 255, 255, 255, 0.7 );">
<tr>
	<td>제목 : </td>
	<td colspan="4">
		<input type="text" class="form-control" placeholder="50자 이내로 작성해주세요." name="title" id="add_title" value="${ sell.title }">
	</td>
</tr>
<tr>
	<td>가격 : </td>
	<td>
		<input type="number" min="0" max="10000000000000000000" class="form-control" name="price" id="price" value="${ sell.price }"/>
	</td>
	<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
	<td>카테고리 : </td>
	<td>
	<select id="category_id" name="category_id" class="form-control">
	<option value="none"><c:out value="-------- 선택 --------"/></option>
	<c:forEach var="list" items="${ requestScope.categoryList }">
			<% String selected=""; %>
		<c:if test="${ not empty sell.sell_id }"><!-- 수정하는 경우 -->
			<c:choose>
			<c:when test="${ list.category_id eq sell.category_id }">
			<% selected="selected='selected'"; %>
			</c:when>
			<c:otherwise>
			<% selected=""; %>
			</c:otherwise>
			</c:choose>
		</c:if>
			<option value="${ list.category_id }" <%= selected %>><c:out value="${ list.name }"/></option>
	</c:forEach>
	</select>
	</td>
</tr>
</table>
</div>

<!-- 여러 이미지 업로드할 수 있게 구현하기 -->
<%-- <div class="uploadImg">
<c:if test="${ not empty sell_img.sell_img }">
<img id="viewImg" src="<%= commonUrl %>/upload/${ sell_img.sell_img }" alt="image" width="300px;">
</c:if>
<input type="file" id="img" name="sell_img" accept="image/gif, image/jpeg, image/png, image/jpg" onchange="readURL(this);"/>
</div> --%>

<div class="note">
	<%-- <textarea name="comments" id="summernote">${ sell.comments }</textarea> --%>
	<textarea rows="40" cols="130" name="comments">${ sell.comments }</textarea>
</div>
</form>

	<div style="text-align: center">
		<input type="button" class="btn" value="작성 완료" id="add">
	</div>
	</div><!-- /<div class="right_wrap"> -->
</div><!-- /<div id="right"> -->
</div><!-- /<div id="container"> -->

<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</c:if>

<c:if test="${ not empty param.title }">
<script type="text/javascript">
alert("${ msg }");
location.href="${ url }";
</script>
</c:if>

</body>
</html>