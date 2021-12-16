<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지-관심글 목록"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="/common/jsp/common_code.jsp" %>

	 <div class="list" style="width: 100%; margin: 0px auto;">
		<h3>관심글 목록</h3>
		
	<!-- <form name="frm_interest">
		<input type="button" value="관심글 해제" onclick="rmInterest()"/> -->
	<table class="table">
	<thead>
	<tr>
	<!-- <th><input type="checkbox" name="interest" onclick="selectAll(this)" value="none"/></th> -->
	<th>제목</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty requestScope.interestList }">
	<tr><td colspan="3">관심있는 글이 존재하지 않습니다.</td></tr>
	</c:if>
		<c:forEach var="list" items="${ requestScope.interestList }">
			<tr>
				<%-- <td style="font-size: 14px"><input type="checkbox" name="interest" value="${ list.sell_id }"/></td> --%>
				<td style="font-size: 14px"><a href="<%= commonUrl %>/want_sell/want_sell_detail.do?sell_id=${ list.sell_id }"><c:out value="${ list.title }"/></a></td>
				<td style="font-size: 14px"><c:out value="${ list.input_date }"/></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	<!-- </form> -->
	
	</div>