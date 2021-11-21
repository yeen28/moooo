<%@page import="java.util.Random"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
if(new Random().nextBoolean()){
	throw new Exception();
}
%>
정상동작