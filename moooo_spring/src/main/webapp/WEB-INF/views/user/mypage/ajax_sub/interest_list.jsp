<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지-관심글 목록"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<div class="right_wrap">
	 <div class="list">
		<h3>관심글 목록</h3>
	<table class="table">
	<thead>
	<tr>
	<th>제목</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty interestList }">
	<tr>
	<td>관심있는 글이 존재하지 않습니다.</td>
	</tr>
	</c:if>
		<c:forEach var="list" items="${ interestList }">
			<tr>
				<td style="font-size: 14px"><a href="<%= commonUrl %>/want_sell/want_sell_detail.do?sell_id=${ list.sell_id }"><c:out value="${ list.title }"/></a></td>
				<td style="font-size: 14px"><c:out value="${ requestScope.input_date }"/></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</div><!-- /<div class="right_wrap"> -->