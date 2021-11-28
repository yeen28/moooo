<%@page import="java.util.List"%>
<%@page import="kr.co.sist.admin.vo.NotiInsertVO"%>
<%@page import="kr.co.sist.admin.dao.NoticeDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="공지사항 추가/수정"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<!-- 서머노트에서 콜백함수 사용해서 이미지 등록하기 -->

<c:if test="${ empty sess_id }">
<c:redirect url="${ commonUrl }/admin/login.jsp"/>
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

<%
request.setCharacterEncoding("UTF-8");
%>
<c:if test="${ empty param.title }">
<c:catch var="e">
<%
String sess_id=(String)session.getAttribute("sess_id");

String notice_id=request.getParameter("notice_id");

NoticeDAO nd=new NoticeDAO();
if(notice_id != null){ //글을 수정하는 경우
	NotiInsertVO nv=nd.selEditNotice(Integer.parseInt(notice_id),sess_id);
	pageContext.setAttribute("nv", nv);
}//end if

pageContext.setAttribute("notice_id", notice_id);
%>
<div class="right">
<h2>공지사항 작성</h2>
<br/>

<form name="frm" id="frm" method="post">
<div>
<table class="table" style="width: 1000px;">
<tr>
	<td>제목 : </td>
	<td>
		<input type="text" class="form-control" placeholder="50자 이내로 작성해주세요." name="title" id="add_title" <% 
		if(notice_id != null){ %>value="${ nv.title }"<%}%>>
	</td>
</tr>
</table>
</div>
<div class="note">
	<textarea name="comments" id="summernote"><% if(notice_id != null) { %>${ nv.comments }<% } %></textarea>
</div>
<% if(notice_id == null){ %>
<input type="hidden" name="type" value="add"/>
<% } else { %>
<input type="hidden" name="type" value="edit"/>
<%-- <input type="hidden" name="sell_id" value="${ sell_id }"/> --%>
<% } //end else %>

<input type="hidden" name="admin_id" value="${ sessionScope.sess_id }"/>
</form>

	<div style="text-align: center">
		<input type="button" class="btn" value="작성 완료" id="add">
	</div>
</div><!-- /<div id="right"> -->
	</c:catch>
	<c:if test="${ not empty e }">
	 <%-- ${ e } --%>
	<script type="text/javascript">
	alert("작성자만 수정할 수 있습니다.");
	location.href="javascript:history.back()";
	</script>
	</c:if>
</c:if>

<c:if test="${ not empty param.title }">
<jsp:useBean id="nv" class="kr.co.sist.admin.vo.NotiInsertVO" scope="page"/>
<jsp:setProperty property="*" name="nv"/>

<c:catch var="e">
<%
NoticeDAO nd=new NoticeDAO();

String url=commonUrl+"/admin/mgr_notice.jsp";
if( "add".equals(request.getParameter("type")) ) {
	nd.insertNoti(nv);
} else {
	nd.updateNoti(nv);
	url=commonUrl+"/admin/mgr_notice_detail.jsp?notice_id="+nv.getNotice_id();
} //end else
pageContext.setAttribute("url", url);
%>
<script type="text/javascript">
alert("등록됐습니다.");
location.href="${ url }";
</script>
</c:catch>
<c:if test="${ not empty e }">
<%-- ${ e } --%>
<c:redirect url="${ commonUrl }/common/error/error.jsp"/>
</c:if>
</c:if>

</body>
</html>