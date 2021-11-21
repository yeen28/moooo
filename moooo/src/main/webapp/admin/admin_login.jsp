<%@page import="kr.co.sist.admin.dao.LoginDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>

<style type="text/css">
.wrap { text-align: center; width: 250px; margin: 0 auto; height: 150px }
.margin1 {margin-top: 30px; font-size: 15px;}
.subtitle{font-weight: bold; text-align: center; vertical-align: middle; margin-top: 50px; font-size: 25px}
</style>

<!-- jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function () {
	$("#login").click(function() {
		if( $("#admin_id").val() == "" ){
			alert("아이디를 입력해주세요.");
			return;
		}
		
		if( $("#admin_pass").val() == "" ){
			alert("비밀번호를 입력해주세요.");
			return;
		}
		
		$("#admin_frm").submit();
	})//login
})//ready
</script>
</head>
<body>
<c:if test="${ empty sessionScope.admin_id and empty param.admin_id}">
<div class="container">
	<h2 style="margin-left: 20px; margin-top: 50px; margin-bottom :200px; font-weight: bold; ">이력서 관리 및 채용공고 관리자</h2>
	
	<form action="http://211.63.89.151/admin/admin_login.jsp" id="admin_frm" method="post">
		<div>
			<div style="padding-bottom: 30px;">
				<h3 class="subtitle">관리자 로그인</h3>
			</div>
			<div class="wrap">
				<div class="margin1">
					<label>아이디</label>
					<div class="input-group">
						<div class="input-group-addon" aria-label="center Align">
							<span class="glyphicon glyphicon-user"  style="height: 28px; padding-top: 8px;"  aria-hidden="true"></span>
						</div>
						 <input type="text" class="form-control"  name="admin_id" style="height: 50px"  aria-label="Large"  id="admin_id" placeholder="아이디를 입력하세요">
					</div>
				</div>
				<div class="margin1">
					<label>비밀번호</label>
					<div class="input-group">
						<div class="input-group-addon" aria-label="center Align">
							<span class="glyphicon glyphicon-align-center"  style="height: 28px; padding-top: 8px;"  aria-hidden="true"></span>
						</div>
						 <input type="password" class="form-control"   style="height: 50px"  aria-label="Large"  id="admin_pass" name="admin_pass" placeholder="비밀번호를 입력하세요">
					</div>
				</div>
				<div class="margin1">
					<input type="button" class="btn btn-lg" value="로그인" id="login">
				</div>
			</div>
	</div>
	</form>
</div>
</c:if>

<c:if test="${ not empty param.admin_id }">
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="aVO" class="kr.co.sist.admin.vo.LoginVO" scope="page"/>
<jsp:setProperty property="*" name="aVO"/>

<c:catch var="e">
<%
String password=request.getParameter("admin_pass");

LoginDAO mDAO=new LoginDAO();
session.setAttribute("sess_id", mDAO.selectLogin(aVO.getAdmin_id(), aVO.getPass()));
%>
<c:redirect url="<%= protocol %><%= domain %><%= contextRoot %>/admin/admin_main.jsp"/>
</c:catch>

<c:if test="${ not empty e }">
<%-- ${ e } --%>
<script type="text/javascript">
alert("존재하지 않는 아이디, 비밀번호입니다.");
location.href="javascript:history.back()";
</script>
</c:if>
</c:if>

</body>
</html>