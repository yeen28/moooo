<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="๋ก๊ทธ์์"
    %>
<%
session.invalidate();
response.sendRedirect("login.jsp");
%>