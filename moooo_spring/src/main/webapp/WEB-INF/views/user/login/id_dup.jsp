<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="아이디 중복확인"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>아이디 중복확인</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
#user_id{width: 200px;}
</style>

<script type="text/javascript">
$(function() {
	$("#btn").click(function() {
		chkNull();
	}); //click
	
	$("#user_id").keydown(function(evt) {
		if( evt.which == 13 ){
			chkNull();
		}//end if
	});
}); //ready

function chkNull() {
	let user_id=$("#user_id").val().trim();
	
	if(user_id==""){
		$.ajax({
			url: "id_dup_null.do",
			type: "get",
			dataType: "json",
			error: function( xhr ) {
				console.log( xhr.status + " / " + xhr.statusText );
			},
			success: function( jsonObj ) {
				$("#view").html( "<span style='color:#ff0000;font-weight:bold;'>" + jsonObj.alertMsg + "</span>" );
			}
		}); //ajax
		return;
	}//end if
	
	$("#frm").submit();
}//chkNull

function sendId( user_id ){
	//부모창으로 아이디 전달
	opener.window.document.new_user.user_id.value=user_id;
	//자식창 닫기
	self.close();
}//sendId
</script>
</head>
<body>
<form action="id_dup.do" method="get" id="frm">
<h2>아이디 중복확인</h2>
<label>아이디</label>
<input type="text" name="user_id" id="user_id" class="form-control"/>
<input type="text" style="display: none;"/>
<input type="button" value="중복확인" id="btn" class="btn"/>
<div id="view"></div>
<c:if test="${not empty param.user_id }">
<div>
<c:choose>
<c:when test="${ available eq true }">
<span style="color:#0D569F; font-weight: bold;">
<c:out value="${ requestScope.msg }"/>
</span> 
[<a href="javascript:sendId('${ requestScope.input_id }')">사용</a>]
</c:when>
<c:otherwise>
<span style="color:#D75043; font-weight: bold;">
<c:out value="${ requestScope.msg }"/>
</span>
</c:otherwise>
</c:choose>
</div>
</c:if>
</form>

</body>
</html>