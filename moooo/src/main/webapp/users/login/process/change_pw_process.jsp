<%@page import="kr.co.sist.user.dao.MemberDAO"%>
<%@page import="kr.co.sist.user.vo.UserVO"%>
<%@page import="kr.co.sist.util.cipher.DataEncrypt"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="비밀번호 변경 처리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<%
request.setCharacterEncoding("UTF-8");
%>
<jsp:useBean id="uVO" class="kr.co.sist.user.vo.UpdatePwdVO" scope="page"/>
<jsp:setProperty property="*" name="uVO"/>

<c:catch var="e">
<%
/* 입력한 이전 비밀번호가 맞는지 확인 */
uVO.setBefore_pass(DataEncrypt.messageDigest("SHA-1", uVO.getBefore_pass()));
String pw1=uVO.getNew_pass();
String pw2=request.getParameter("new_pass2");
String msg="비밀번호를 틀렸습니다. 다시 입력해주세요.";
String url="javascript:history.back()";

MemberDAO mDAO=new MemberDAO();
String dbPw=mDAO.selChangePw(uVO.getUser_id());

if( dbPw.equals(uVO.getBefore_pass()) && pw1.equals(pw2) ){
	uVO.setNew_pass(DataEncrypt.messageDigest("SHA-1", uVO.getNew_pass()));
	mDAO.updatePw(uVO);
	msg="비밀번호를 변경했습니다.";
	url="../../mypage/mypage.jsp"; /* 마이페이지로 이동 */
}

pageContext.setAttribute("msg", msg);
pageContext.setAttribute("url", url);
%>
<script type="text/javascript">
alert( "${msg}" );
location.href="${ url }";
</script>
</c:catch>

<c:if test="${ not empty e }">
<%-- ${ e } --%>
<c:redirect url="../../../common/error/error.jsp"/>
</c:if>