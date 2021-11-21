<%@page import="kr.co.sist.user.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="닉네임 중복확인"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>닉네임 중복확인</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function() {
	$("#btn").click(function() {
		chkNull();
	}); //click
	
	$("#nickname").keydown(function(evt) {
		if( evt.which == 13 ){
			chkNull();
		}//end if
	});
}); //ready

function chkNull() {
	let nickname=$("#nickname").val().trim();
	
	if(nickname==""){
		alert("중복 검사할 닉네임을 입력해주세요.");
		return;
	}//end if
	
	$("#frm").submit();
}//chkNull

function sendId( nickname ){
	opener.window.document.frm.nickname.value=nickname;
	
	self.close();
}//sendId
</script>
</head>
<body>
<form action="dup_nickname.jsp" method="get" id="frm">
<h2>닉네임 중복확인</h2>
<label>닉네임</label>
<input type="text" name="nickname" id="nickname"/>
<input type="text" style="display: none;"/>
<input type="button" value="중복확인" id="btn"/>
<c:if test="${not empty param.nickname }">
<div>
<%
String nickname=request.getParameter("nickname");

MemberDAO mDAO=new MemberDAO();
String resultNickname=mDAO.selectNickname(nickname);

pageContext.setAttribute("resultNickname", resultNickname);
%>
<span style="color:#0D569F; font-weight: bold;">
입력하신 <c:out value="${ param.nickname }"/>
</span>는 
<c:choose>
<c:when test="${ empty resultNickname }">
<span style="color:#0D569F; font-weight: bold;">
사용가능합니다.
</span>
[<a href="javascript:sendId('${ param.nickname }')">사용</a>]
</c:when>
<c:otherwise>
<span style="color:#D75043; font-weight: bold;">
이미 사용중입니다.
</span>
</c:otherwise>
</c:choose>
</div>
</c:if>
</form>

</body>
</html>