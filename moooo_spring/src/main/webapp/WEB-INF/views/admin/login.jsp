<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 로그인"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>    

<c:if test="${ not empty sessionScope.admin_id }">
<c:redirect url="${ commonUrl }/admin/main.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>

<!-- jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
.wrap { text-align: center; width: 250px; margin: 0 auto; height: 150px }
.margin1 {margin-top: 30px; font-size: 15px;}
.subtitle{font-weight: bold; text-align: center; vertical-align: middle; margin-top: 50px; font-size: 25px}
.loginBtn{background-color: #333; color: #fff; font-size: 15px; font-weight: bold; width:240px;}
</style>

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
<body style="background-color: #DFDFDF; margin-top: 100px;">
<c:if test="${ empty sessionScope.admin_id }">
<div class="container">
	<form action="<%= commonUrl %>/admin/login_proc.do" id="admin_frm" method="post">
		<div>
			<div style="padding-bottom: 30px;">
				<h3 class="subtitle">MooOO<span class="glyphicon glyphicon-cog"></span><span class="glyphicon glyphicon-cog"></span></h3>
			</div>
			<div class="wrap">
				<div class="margin1">
					<label>아이디</label>
					<div class="input-group">
						<div class="input-group-addon" aria-label="center Align">
							<span class="glyphicon glyphicon-user"  style="height: 28px; padding-top: 8px;"  aria-hidden="true"></span>
						</div>
						 <input value="admin" type="text" class="form-control"  name="admin_id" style="height: 50px"  aria-label="Large"  id="admin_id" placeholder="아이디">
					</div>
				</div>
				<div class="margin1">
					<label>비밀번호</label>
					<div class="input-group">
						<div class="input-group-addon" aria-label="center Align" style="padding:5px;">
						<img alt="pw" src="<%= commonUrl %>/common/images/icons/pass_icon.PNG" width="25px;"/>
						</div>
						 <input value="admin" type="password" class="form-control"   style="height: 50px"  aria-label="Large"  id="admin_pass" name="pass" placeholder="비밀번호">
					</div>
				</div>
				<div class="margin1">
					<input type="button" class="btn btn-lg loginBtn" value="로그인" id="login">
				</div>
			</div>
	</div>
	</form>
</div>
</c:if>

<c:if test="${ not empty requestScope.msg }">
<script type="text/javascript">
alert("${ msg }");
//location.href="javascript:history.back()";
</script>
</c:if>

</body>
</html>