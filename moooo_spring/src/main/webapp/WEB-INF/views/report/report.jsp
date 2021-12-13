<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="신고 처리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<input type="hidden" name="form_flag" value="write_process"/>

<h3><span style="text-align: center;">신고이유</span></h3>
<ul style="list-style: none">
	<c:forEach var="list" items="${ reportList }">
	<li><input type="radio" name="reason_id" id="reason" value="${ list.reason_id }"/><c:out value="${ list.reason }"/></li>
	</c:forEach>
</ul>
<input type="button" value="신고하기" class="inputBox" id="reportBtn" style="width: 80px"/>
<input type="button" value="창닫기" class="inputBox" id="closeBtn" style="width: 80px"/>