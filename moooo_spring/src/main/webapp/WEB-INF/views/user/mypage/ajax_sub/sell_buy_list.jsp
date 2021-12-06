<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지-내가 쓴 글 리스트"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<% 
String sess_id=(String)session.getAttribute("sess_user_id"); 
pageContext.setAttribute("sess_user_id", sess_id);
%>

<style type="text/css">
.list{margin-top:30px;}
</style>

<%-- <% 
try{
	WantBuyDAO wbd=new WantBuyDAO();
	WantSellDAO wsd=new WantSellDAO();
	
	List<WantBuyVO> buyList=wbd.selectMypageBuy(sess_id);
	pageContext.setAttribute("buyList", buyList);
	
	List<WantSellVO> sellList=wsd.selectMypageSell(sess_id);
	pageContext.setAttribute("sellList", sellList);
%> --%>

<div class="right_wrap">
	 <div class="list">
		<h3>사고 싶어요</h3>
	<table class="table">
	<thead>
	<tr>
	<th><input type="checkbox"/></th>
	<th>제목</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty buyList }">
	<tr>
	<td>작성한 글이 존재하지 않습니다.</td>
	</tr>
	</c:if>
		<c:forEach var="list" items="${ buyList }">
			<tr>
				<td style="font-size: 14px"><input type="checkbox"/></td>
				<td style="font-size: 14px">
					<a href="<%= commonUrl %>/view/want_buy/want_buy_detail.jsp?buy_id=${ list.buy_id }"><c:out value="${ list.title }"/></a>
				</td>
				<td style="font-size: 14px"><c:out value="${ list.input_date }"/></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
	
	 <div class="list">
		<h3>팔아요</h3>
	<table class="table">
	<thead>
	<tr>
	<th><input type="checkbox"/></th>
	<th>제목</th>
	<th>작성일</th>
	</tr>
	</thead>
	<tbody>
	<c:if test="${ empty sellList }">
	<tr>
	<td>작성한 글이 존재하지 않습니다.</td>
	</tr>
	</c:if>
		<c:forEach var="list" items="${ sellList }">
			<tr>
				<td style="font-size: 14px"><input type="checkbox"/></td>
				<td style="font-size: 14px"><a href="<%= commonUrl %>/view/want_sell/want_sell_detail.jsp?sell_id=${ list.sell_id }"><c:out value="${ list.title }"/></a></td>
				<td style="font-size: 14px"><c:out value="${ list.input_date }"/></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</div><!-- /<div class="right_wrap"> -->
<%-- <%
} catch(DataAccessException dae){
	out.println("문제발생");
}//end catch
%> --%>