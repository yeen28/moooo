<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="로그아웃"
    %>
<%
session.invalidate();
response.sendRedirect("login.jsp");
%>