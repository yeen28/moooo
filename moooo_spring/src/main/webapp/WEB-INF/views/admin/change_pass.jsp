<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 비밀번호 변경"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>    

<c:if test="${ empty sessionScope.admin_id }">
<c:redirect url="/admin/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
body{height:100%;}

.form { text-align: center; width: 250px; margin: 250px auto; }
.margin1{width: 300px; text-align: center; margin: 15px;}
.btn{background-color: #333; color: #fff; font-size: 15px; font-weight: bold; width:300px;}
.btn:hover{color:#333;}

.left-nav{width: 300px; height: 100%; background-color: #CFCFCF;position: absolute;}
.left-nav>ul{list-style: none;padding-top:30px;}
a{color: #333;}
.menu{border:1px solid #CFCFCF;}
</style>

<script type="text/javascript">
$(function () {
	$("#cha_pass_ad").click(function() {
		
		if( $("#beforepass_ad").val() == "" ){
			alert("기존 비밀번호를 입력해주세요.");
			return;
		} 
		
		if( $("#new_pass").val() == "" ){
			alert("새로운 비밀번호를 입력해주세요.");
			return;
		}
		
		if( $("#check_newpass_ad").val() == "" ){
			alert("새로운 비밀번호 확인을 입력해주세요.");
			return;
		}
		
		if( $("#new_pass").val() != $("#check_newpass_ad").val() ){
			alert("새 비밀번호와 새 비밀번호 확인이 다릅니다.\n다시 입력해주세요.");
			return;
		}
		
		var con_pass = confirm("비밀번호를 변경하시겠습니까?");
		if( con_pass ) {
			$("#frm_ad_pass").submit();
		}//end if
		
	}); //click
})//ready
</script>
</head>
<body style="background-color: #DFDFDF;">
<jsp:include page="layout/header.jsp"/>

  <form action="change_pass_proc.do" method="post" id="frm_ad_pass">
      <div class="form">
		<input type="hidden" name="admin_id" value="${ sessionScope.admin_id }"><%-- 변조하지 못하게 백엔드에서도 세션값을 받음 --%>
		<div class="margin1">
		<label>기존 비밀번호</label>
		<input type="password" class="form-control" id="beforepass_ad" name="before_pass" placeholder="기존 비밀번호를 입력하세요">
		</div>
		<div class="margin1">
		<label>새로운 비밀번호</label>
		<input type="password" class="form-control" id="new_pass" name="new_pass" placeholder="새로운 비밀번호를 입력하세요">
		</div>
		<div class="margin1">
		<label>새로운 비밀번호 확인</label>
		<input type="password" class="form-control" id="check_newpass_ad" name="check_newpass_ad" placeholder="새로운 비밀번호를 확인합니다">
            </div>
            <div class="margin1">
               <input type="button" class="btn" value="비밀번호 변경" id="cha_pass_ad">
            </div>
         </div>
   </form>

<c:if test="${ not empty msg }">
<script type="text/javascript">
$(function() {
	alert( "${msg}" );
	location.href="<%= commonUrl %>/admin/change_pass.do";
}); //ready
</script>
</c:if>
		
</body>
</html>