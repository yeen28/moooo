<%@page import="kr.co.sist.admin.dao.LoginDAO"%>
<%@page import="kr.co.sist.util.cipher.DataEncrypt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 비밀번호 변경"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>    

<%-- <c:if test="${ empty sess_id }">
<c:redirect url="login.jsp"/>
</c:if> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>관리자 비밀번호 변경페이지</title>
<style type="text/css">

/* 비밀번호 변경란 여백 */
.wrap { text-align: center; width: 250px; margin: 0 auto; margin-top: 100px}
.margin1{width: 300px; text-align: center; margin: 15px;}
.title2{  margin-bottom: 20px; margin-top: 50px; font-weight: bold; font-size: 25px;}

</style>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function () {
	$("#cha_pass_ad").click(function() {
		
		if( $("#beforepass_ad").val() == "" ){
			alert("기존 비밀번호를 입력해주세요.");
			return;
		} 
		
		if( $("#newpass_ad").val() == "" ){
			alert("새로운 비밀번호를 입력해주세요.");
			return;
		}
		
		if( $("#check_newpass_ad").val() == "" ){
			alert("새로운 비밀번호 확인을 입력해주세요.");
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
<body>
<jsp:include page="/layout/header.jsp"/>
<c:if test="${ not empty sessionScope.sess_id and empty param.pass_ad or param.hid eq 'fail' }">
      <form action="change_pass.jsp" method="post" id="frm_ad_pass">
      <div>
         <div style="margin: 0px auto; width: 600px;text-align: left;">
         </div>
         <div class="wrap">
            <div class="margin1">
               <label>기존 비밀번호</label>
               <input type="password" class="form-control" id="beforepass_ad" name="pass_ad" placeholder="기존 비밀번호를 입력하세요">
            </div>
            <div class="margin1">
               <label>새로운 비밀번호</label>
               <input type="password" class="form-control" id="newpass_ad" name="newpass_ad" placeholder="새로운 비밀번호를 입력하세요">
            </div>
            <div class="margin1">
               <label>새로운 비밀번호 확인</label>
               <input type="password" class="form-control" id="check_newpass_ad" name="check_newpass_ad" placeholder="새로운 비밀번호를 확인합니다">
            </div>
            <div class="margin1">
               <input type="button" class="btn" value="비밀번호 변경" id="cha_pass_ad">
            </div>
         </div>
      </div>
   </form>
</c:if>
   
<c:if test="${ not empty param.pass_ad and param.hid ne 'fail' }">
<%
request.setCharacterEncoding("UTF-8");
%>
<c:catch var="e">
<form action="<%= protocol %><%= domain %><%= contextRoot %>/admin/change_pass.jsp" id="procFrm">
<%
/* 입력한 이전 비밀번호가 맞는지 확인 */
String beforePwd=request.getParameter("pass_ad");
String newPwd=request.getParameter("newpass_ad");
String chkNewPwd=request.getParameter("check_newpass_ad");
String msg="";

LoginDAO aDAO=new LoginDAO();
String id=(String)session.getAttribute("sess_id"); 
String dbPwd=aDAO.selChangePwd( id );

if( beforePwd.equals(dbPwd) && newPwd.equals(chkNewPwd) ){
	aDAO.updatePwd(id, newPwd);
	msg="비밀번호가 변경됐습니다.";
%>
	<input type="hidden" name="hid" value="succeed"/>
	<%
} else {
	msg="비밀번호를 틀렸습니다. 다시 입력해주세요.";
	%>
	<input type="hidden" name="hid" value="fail"/>
	<%
}//end else

pageContext.setAttribute("msg", msg);
%>
</form>
<script type="text/javascript">
$(function() {
	alert( "${msg}" );
	$("#procFrm").submit();
}); //ready
</script>

</c:catch>

<c:if test="${ not empty e }">
 <%-- ${ e } --%>
죄송합니다. 비밀번호 변경 중 문제가 발생했습니다.<br/>잠시후 다시 시도해주세요.
</c:if>
</c:if>
		
</body>
</html>