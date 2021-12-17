<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="공지사항 추가/수정"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<!-- 서머노트에서 콜백함수 사용해서 이미지 등록하기 -->

<c:if test="${ empty sessionScope.admin_id }">
<c:redirect url="${ commonUrl }/admin/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

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

<style type="text/css">
body{height:100%;}

.right { position: absolute; top: 100px; left: 450px; }

.left-nav{width: 300px; height: 100%; background-color: #CFCFCF;position: absolute;}
.left-nav>ul{list-style: none;padding-top:30px;}
a{color: #333;}
.menu{border:1px solid #CFCFCF;}

.note {margin: 20px auto; }
</style>

<script>
$(document).ready(function() {
        $('#summernote').summernote({
			placeholder : '작성란',
			tabsize : 2,
			width: 1000,
			height: 500, //에디터 높이
			lang: 'ko-KR', //한글 설정
        	toolbar: [
        	    // [groupName, [list of button]]
        	    ['style', ['bold']],
        	    ['font', ['strikethrough']],
        	    ['fontsize', ['fontsize']],
        	    ['color', ['color']],
        	    ['table',['table']],
        	    ['insert', ['link', 'picture']]//, 'video']]
        	  ],
        	  fontSizes: ['9', '10', '12', '14', '16', '18', '20', '22', '24', '26', '28', '30', '35', '45', '60'],
        	  callbacks: { //이미지를 첨부하는 부분
        		  onImageUpload: function( files, editor, welEditable ) {
        			  //console.log('image upload: ', files);
        			  //console.log('this: ', this);
        			  //console.log('editor: ', editor);
        			  //console.log('wel: ', welEditable);
        			  sendFile(files[0], this);
        			  //uploadSummernoteImageFile(files[0], this);
        		  } //onImageUpload
        	  } //callbacks
        });
}); //ready

function sendFile( file, editor ) {
	data=new FormData();
	data.append("file", file);
	$.ajax({ //ajax를 통해 파일 업로드 처리
		data: data,
		type: "POST",
		url: "proc/uploadSummernoteImageFile.jsp",
		enctype: 'multipart/form-data',
		cache: false,
		contentType: false,
		processData: false,
		success: function( data ) { //처리가 성공할 경우
			//에디터에 이미지 출력
			//$(editor).summernote('editor.insertImage', data.url, filename);
			//console.log("filename : "+'filename');
			//data.url = "http://localhost/moooo/common/images"+data.url;
			console.log("data : "+data.url);
			$(editor).summernote('editor.insertImage', data.url, 'filename'); //server에 올리면 될거라고 생각하는데 과연...?
			//$(editor).summernote('editor.insertImage', 'http://localhost/moooo/common/images'+data.url, 'filename');
			//$("#summernote").summernote('editor', '<img src=http://localhost/moooo/common/images"'+data.url+'"/>');
			//$(editor).summernote('editor.insertImage', "http://localhost/moooo/common/images/upload/img1.png");
		},
		error: function( xhr ) {
			alert("error");
		}
	}); //ajax
}; //sendFile

/**
 * 이미지 파일 업로드
 */
/* function uploadSummernoteImageFile(file, editor) {
	data = new FormData();
	data.append("file", file);
	$.ajax({
		data: data,
		type: "POST",
		url: "proc/uploadSummernoteImgeFile.jsp",
		contentType: false,
		processData: false,
		success: function( data ) {
			//항상 업로드된 파일의 url이 있어야 한다.
			$(editor).summernote('insertImage', data.url);
		}
	}); //ajax
}//uploadSummernoteImageFile */
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

<c:if test="${ empty param.title }">
<div class="right">
<h2>공지사항 작성</h2>
<br/>

<form name="frm" id="frm" method="post" action="notice_edit_proc.do?control=${ param.control }">
<div>
<table class="table" style="width: 1000px;">
<thead>
<tr>
	<td colspan="2"><input type="button" class="btn btn-info" value="작성 완료" id="add" style="float: right;"></td>
</tr>
</thead>
<tbody>
<tr>
	<td>제목 : </td>
	<td>
		<input type="text" class="form-control" placeholder="50자 이내로 작성해주세요." name="title" id="add_title" value="${ notice.title }">
	</td>
</tr>
</tbody>
</table>
</div>
<div class="note">
	<textarea name="comments" id="summernote">${ notice.comments }</textarea>
</div>
<c:if test="${ not empty param.notice_id }">
<input type="hidden" value="${ param.notice_id }" name="notice_id"/>
</c:if>
</form>
</div><!-- /<div id="right"> -->
</c:if>

<c:if test="${ not empty param.title }">
<script type="text/javascript">
alert("${ msg }");
location.href="<%= commonUrl %>/admin/"+"${ url }";
</script>
</c:if>

</body>
</html>