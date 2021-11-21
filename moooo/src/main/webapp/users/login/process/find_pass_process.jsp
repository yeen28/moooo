<%@page import="kr.co.sist.util.cipher.DataEncrypt"%>
<%@page import="kr.co.sist.user.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="비밀번호 찾기 처리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta nickname="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<link rel="stylesheet" href="<%= protocol %><%= domain %><%= contextRoot %><%= common_css %>/find.css"/>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>
<body>
<div id="find_wrap">
<!-- header -->
<jsp:include page="/layout/header.jsp"/>

<div class="find_container">

<c:catch var="e">
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="mVO" class="kr.co.sist.user.vo.UserVO" scope="page"/>
<jsp:setProperty property="*" name="mVO"/>

<%
MemberDAO mDAO=new MemberDAO();
String pw=mDAO.selectFindPw(mVO.getUser_id(), mVO.getPhone());

String id=mVO.getUser_id();
String nickname=mVO.getNickname();

pw = "";
for(int i=0; pw.length()<8; i++) {
	int rand = (int) (Math.random() * 75) + 48;
	if (rand < 58 || (rand > 64 && rand < 91) || rand > 96) { // 숫자, 대문자, 소문자
		pw += (char) rand;
	} else continue;
}//end while
	
pageContext.setAttribute("id", id);
pageContext.setAttribute("pw", pw);
%>
<jsp:useBean id="uVO" class="kr.co.sist.user.vo.UpdatePwdVO" scope="page"/>
<jsp:setProperty property="user_id" name="uVO" value="${ id }"/>
<jsp:setProperty property="new_pass" name="uVO" value="${ pw }"/>

<%
uVO.setNew_pass( DataEncrypt.messageDigest( "SHA-1", uVO.getNew_pass() ));
mDAO.updatePw(uVO);
%>
<div class="find_img">
<img alt="find" src="<%= protocol %><%= domain %><%= contextRoot %>/common/images/find.png">
</div>

<div class="container-mid result-mid">
<span><strong><%= nickname %></strong>님의 임시비밀번호입니다.<br/>
임시비밀번호 : <strong><c:out value="${ pw }"/></strong></span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/login.jsp">로그인</a>
</div>

</c:catch>

<c:if test="${ not empty e }">
<div class="find_img">
<img alt="find" src="<%= protocol %><%= domain %><%= contextRoot %>/common/images/find.png">
</div>

<div class="container-mid result-mid">
<span>입력하신 데이터에 일치하는 회원정보가 존재하지 않습니다.</span>
</div>

<div id="result-bottom">
<a class="btn" href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/find.jsp">확인</a>
</div>

</c:if>
</div>

<!-- footer -->
<jsp:include page="/layout/footer.jsp"/>
</div>

</body>
</html>