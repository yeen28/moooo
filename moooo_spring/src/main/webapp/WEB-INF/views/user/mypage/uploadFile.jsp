<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="파일 업로드"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>파일 업로드</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function() {
	
	$("#btn").click(function() {
		
		let upload=$("#img").val();
		if(upload != ""){
			//업로드가능 확장자의 유효성검증.
			//서버에서 실행되는 언어와 같은 소스코드는 업로드하지 못하도록 막아야 함.
			//이미지파일(jpg, png, gif, bmp)만 업로드할 수 있도록 검증.
			
			let fileExt = ["jpg","png","gif","bmp"];
			let fileFlag = false;
			
			let ext = (upload.substring(upload.lastIndexOf(".")+1)).toLowerCase();
			for( var i=0; i<fileExt.length; i++ ){
				if( ext == fileExt[i] ){
					fileFlag = true;
					break;
				}//end if
			}//end for
		
			if( !fileFlag ){
				alert("업로드 가능 확장자가 아닙니다.");
				return;
			}//end if
		}//end if
		
		
		chkNull();
	}); //click
	
	/* $("#img").keydown(function(evt) {
		if( evt.which == 13 ){
			chkNull();
		}//end if
	}); */
}); //ready

function chkNull() {
	let img=$("#img").val().trim();
	
	if(img==""){
		alert("업로드할 파일을 선택해주세요.");
		return;
	}//end if
	
	$("#frm").submit();
}//chkNull

function sendImg( imgName ){
	//opener.window.document.frm.img.value=imgName;
	
	self.close();
}//sendId
</script>
</head>
<body>
<form action="<%= commonUrl %>/users/mypage/proc/update_proc.jsp" method="post" id="frm" enctype="multipart/form-data">
<h2>파일 업로드</h2>
<input type="file" name="img" id="img"/><!--  onchange="readURL(this);"/> -->
<input type="text" style="display: none;"/>
<input type="button" value="업로드" id="btn"/>

<c:if test="${not empty param.img }">
<div>
[<a href="javascript:sendImg('${ param.img }')">확인</a>]
</div>
</c:if>
</form>

</body>
</html>