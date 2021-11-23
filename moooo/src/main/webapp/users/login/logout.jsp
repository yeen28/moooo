<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="로그아웃 ( 세션 값 삭제 )"
    %>
<%@ include file="/common/jsp/common_code.jsp"%>

<%
session.invalidate();
response.sendRedirect("../../index.jsp");
%>
