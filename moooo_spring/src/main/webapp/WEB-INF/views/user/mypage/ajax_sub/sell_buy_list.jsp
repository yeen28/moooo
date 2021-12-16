<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지-내가 쓴 글 리스트"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

	<div class="list" style="width: 100%; margin: 0px auto;">
	<h3>사고 싶어요</h3>
	<table class="table">
	<thead>
	<tr>
	<!-- <th><input type="checkbox"/></th> -->
	<th>제목</th>
	<th>조회수</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty requestScope.buyList }">
	<tr><td colspan=3>작성한 글이 존재하지 않습니다.</td>	</tr>
	</c:if>
	<c:forEach var="list" items="${ requestScope.buyList }">
	<tr>
	<!-- <td style="font-size: 14px"><input type="checkbox"/></td> -->
	<td style="font-size: 14px"><a href="<%= commonUrl %>/want_buy/want_buy_detail.do?buy_id=${ list.buy_id }"><c:out value="${ list.title }"/></a></td>
	<td style="font-size: 14px"><c:out value="${ list.input_date }"/></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
	
	<div class="list" style="width: 100%; margin: 50px auto;">
	<h3>팔아요</h3>
	<table class="table">
	<thead>
	<tr>
	<!-- <th><input type="checkbox"/></th> -->
	<th>제목</th>
	<th>조회수</th>
	<th>관심수</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty requestScope.sellList }">
	<tr><td colspan="4">작성한 글이 존재하지 않습니다.</td></tr>
	</c:if>
	<c:forEach var="list" items="${ requestScope.sellList }">
	<tr>
	<!-- <td style="font-size: 14px"><input type="checkbox"/></td> -->
	<td style="font-size: 14px"><a href="<%= commonUrl %>/want_sell/want_sell_detail.do?sell_id=${ list.sell_id }"><c:out value="${ list.title }"/></a></td>
	<td style="font-size: 14px"><c:out value="${ list.view_cnt }"/></td>
	<td style="font-size: 14px"><c:out value="${ list.interest_cnt }"/></td>
	<td style="font-size: 14px"><c:out value="${ list.input_date }"/></td>
	</tr>
	</c:forEach>
	</tbody>
	</table>
	</div>
