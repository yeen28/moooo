<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지-관심글 목록"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<%-- <% 
String sess_id=(String)session.getAttribute("sess_user_id"); 
pageContext.setAttribute("sess_user_id", sess_id);

try{
MypageDAO md=new MypageDAO();
List<Integer> idList=md.selInterNumList(sess_id);
List<WantSellVO> wvList=md.selInterList(idList);
%> --%>

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
	<c:if test="${ empty list }">
	<tr>
	<td>관심있는 글이 존재하지 않습니다.</td>
	</tr>
	</c:if>
		<c:forEach var="list" items="${ list }">
			<tr>
				<td style="font-size: 14px"><a href="<%= commonUrl %>/view/want_sell_detail.do?sell_id=${ list.sell_id }"><c:out value="${ list.title }"/></a></td>
				<td style="font-size: 14px"><c:out value="${ requestScope.input_date }"/></td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	</div>
</div><!-- /<div class="right_wrap"> -->
<%-- <%
} catch(SQLException se){
	out.println("문제발생");
}//end catch
%> --%>