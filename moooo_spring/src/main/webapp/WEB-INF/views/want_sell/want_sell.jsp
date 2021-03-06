<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 첫 화면"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty user_id }">
<c:redirect url="/user/login/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
.notice_wrap { position: absolute; top: 0px; width: 70%; font-size: 14px; list-style: disc; margin-left: 10px; }
.notice_head { border-bottom: 1px solid; margin-bottom: 10px; }
</style>
</head>

<body>
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.do"/>
	
	<div id="right">
	<div class="notice_wrap">
	<form class="navbar-form navbar-right" action="search.do">
		<input type="text" class="form-control" placeholder="Search..." name="searchWord">
	</form>
	<a class="btn btn-success navbar-right" href="<%= commonUrl %>/want_sell/ws_write.do?control=add" role="button">글쓰기</a>
	
	<div class="notice_head">
	<h2 style="font-weight: bold;">팔아요</h2>
				
	<div>
		<a href="<%= commonUrl %>/want_sell/want_sell.do">전체 글 보기</a>
	</div>
				
	</div>
	<div>
	<table class="table table-hover" style="background-color: rgba( 255, 255, 255, 0.7 );">
	<thead>
	<tr>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">제목</td>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">가격</td>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성자</td>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성일</td>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">관심수</td>
		<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">조회수</td>
	</tr>
	</thead>
  <tbody>
  <c:if test="${ empty list }">
		<tr>
		  <td colspan="6">작성된 글이 없습니다.</td>
		</tr>
		</c:if>
     <c:forEach var="sell" items="${ list }"> 
       <tr>
          <td style="font-size: 16px; text-align: center; color: #333;">
              <a href="<%= commonUrl %>/want_sell/want_sell_detail.do?sell_id=${ sell.sell_id }">${ sell.title }</a>
          </td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ sell.string_price }원</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ sell.user_id }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ sell.input_date }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ sell.interest_cnt }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ sell.view_cnt }</td>
       </tr> 
     </c:forEach>
  </tbody>
</table>
</div>
<div class="text-center">
		<nav id="pagination">
			<ul class="pagination">
	<li>
		<c:if test="${ param.page lt 1 and param.page ne null }">
			<a href="<%=  commonUrl %>/want_sell/want_sell.do?page=${ pagination.nowPage-1 }" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
			</a>
		</c:if>
	</li>
	<c:forEach var="i" begin="${ pagination.start }" end="${ pagination.end }">
		<li><a href="<%=  commonUrl %>/want_sell/want_sell.do?page=${ i }"><c:out value="${ i }"/></a></li>
	</c:forEach>
	<li>
	<c:if test="${ pagination.lastPage ne 0 and pagination.nowPage ne pagination.lastPage }">
		<a href="<%=  commonUrl %>/want_sell/want_sell.do?page=${ pagination.nowPage+1 }" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		</a>
	</c:if>
	</li>
</ul>
</nav>
</div>
</div>
</div>
</div>

<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>