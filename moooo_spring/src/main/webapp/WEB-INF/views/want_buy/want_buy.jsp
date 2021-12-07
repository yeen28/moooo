<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="사고싶어요 첫 화면"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sess_user_id }">
<c:redirect url="/user/login/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<style type="text/css">
.notice_wrap {
	position: absolute;
	top: 0px;
	width: 70%;
	font-size: 14px;
	list-style: disc;
	margin-left: 10px;
}
.notice_head {
	border-bottom: 1px solid;
	margin-bottom: 10px;
}
</style>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
</head>

<body>
<%-- <%
//category가 있는 경우, 해당 카테고리의 글만 얻기
int category=0;
try{
	category=Integer.parseInt(request.getParameter("category"));
} catch (NumberFormatException nfe){
	category=0;
}//end catch

//페이지네이션 처리
WantBuyDAO wd = new WantBuyDAO();

int numPerPage=10; //한 페이지 당 보여줄 게시물의 수
int totData=wd.selectBuyCnt(category);
int LastPage=totData/numPerPage;
if(totData % numPerPage > 0){
	++LastPage;
}
int blockPage=10;
int nowPage=1; //현재 페이지
try{
	nowPage=Integer.parseInt(request.getParameter("page"));
} catch (NumberFormatException nfe){
	nowPage=1;
}
int start=((nowPage-1)/blockPage)*10+1;
int end=start+blockPage-1;
if( end > LastPage ){
	end=LastPage;
}

int rowBegin=(nowPage-1)*numPerPage+1;
int rowEnd=nowPage*numPerPage;
List<WantBuyVO> list=wd.selectBuyTitle(category, rowBegin, rowEnd);

pageContext.setAttribute("start", start);
pageContext.setAttribute("end", end);
pageContext.setAttribute("nowPage", nowPage);
pageContext.setAttribute("list", list);
%> --%>
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	
<!-- container -->
<div id="container">
	
<!-- 왼쪽 바 -->
<jsp:include page="/layout/side_left.do"/>
	
<div id="right">
	<div class="notice_wrap">
		<div class="notice_head">
		<h2 style="font-weight: bold;">사고싶어요</h2>
		
		<div>
		<a href="<%= commonUrl %>/want_buy/wb_write.do">+추가</a>
		<a href="<%= commonUrl %>/want_buy/want_buy.do">전체 글 보기</a>
		</div>
			
		</div>
			
		<div>
		<table class="table table-hover">
		<thead>
		<tr>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">번호</td>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">제목</td>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">가격</td>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성자</td>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성일</td>
			<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">조회</td>
		</tr>
		</thead>
		<tbody>
		<c:if test="${ empty list }">
		<tr>
		  <td colspan="6">작성된 글이 없습니다.</td>
		</tr>
		</c:if>
		<c:forEach var="buy" items="${ list }"> 
		<tr>
          <td style="font-size: 16px; text-align: center; color: #333;">${ buy.buy_id }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">
              <a href="<%= commonUrl %>/want_buy/want_buy_detail.do?buy_id=${ buy.buy_id }"><c:out value="${ buy.title }"/></a>
          </td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ buy.price }원</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ buy.user_id }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ buy.input_date }</td>
          <td style="font-size: 16px; text-align: center; color: #333;">${ buy.view_cnt }</td>
       </tr> 
     </c:forEach>
  </tbody>
</table>
</div>
<div class="text-center">
		<nav id="pagination">
			<ul class="pagination">
	<li>
		<c:if test="${ nowPage ne 1 and param.page ne null }">
			<a href="<%= commonUrl %>/want_buy/want_buy.do?page=${ nowPage-1 }" aria-label="Previous">
			<span aria-hidden="true">&laquo;</span>
			</a>
		</c:if>
	</li>
	<c:forEach var="i" begin="${ start }" end="${ end }">
		<li><a href="<%= commonUrl %>/want_buy/want_buy.do?page=${ i }"><c:out value="${ i }"/></a></li>
	</c:forEach>
	<li>
	<%-- <% if(LastPage != 0 && nowPage != LastPage){ %> --%>
		<a href="<%= commonUrl %>/want_buy/want_buy.do?page=${ nowPage+1 }" aria-label="Next">
			<span aria-hidden="true">&raquo;</span>
		</a>
	<%-- <% } %> --%>
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