<%@page import="java.sql.SQLException"%>
<%@page import="kr.co.sist.user.vo.UserVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.sist.util.cipher.DataEncrypt"%>
<%@page import="kr.co.sist.user.dao.MemberDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="로그인 처리"
%>
<%@ include file="/common/jsp/common_code.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="lVO" class="kr.co.sist.user.vo.LoginVO" scope="page"/>
<jsp:setProperty property="*" name="lVO"/>

<c:catch var="e">
<%
String pass=DataEncrypt.messageDigest( "SHA-1", request.getParameter("pass"));

MemberDAO mDAO=new MemberDAO();
	String user_id=mDAO.selectLogin(lVO.getUser_id(), pass);
	session.setAttribute("sess_user_id", user_id);

/* List<String> listSub=mDAO.selectSub( mVO.getId() );
List<String> listUrl=mDAO.selectUrl( mVO.getId() );
session.setAttribute("listSub", listSub);
session.setAttribute("listUrl", listUrl); */
%>
<c:redirect url="../../../index.jsp"/>
</c:catch>
<c:if test="${ not empty e }">
<script type="text/javascript">
/* ${ e } */
alert("존재하지 않는 아이디, 비밀번호입니다.");
location.href="javascript:history.back()";
</script>
</c:if>