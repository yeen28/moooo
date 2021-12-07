<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="공지사항"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<%
session.setAttribute("user_id", "user_id2");
%>
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

<script type="text/javascript">

</script>
</head>
<body>
<%-- <%
NoticeDAO nDAO = new NoticeDAO();

int numPerPage=10;
int totData=nDAO.selectNotiCnt();
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
List<NotiInsertVO> list=nDAO.selectNotiTitle(rowBegin, rowEnd);

pageContext.setAttribute("start", start);
pageContext.setAttribute("end", end);
pageContext.setAttribute("nowPage", nowPage);
pageContext.setAttribute("selectedNotice", list);
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
				<h2 style="font-weight: bold;">공지사항</h2>
			</div>
			<div>
				<table class="table table-hover">
					<thead>
						<tr>
							<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">번호</td>
							<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">제목</td>
							<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성자</td>
							<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">작성일</td>
							<td style="font-size: 18px; font-weight: bold; text-align: center; color: #333;">조회</td>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="notice" items="${ selectedNotice }"> 
							<tr>
								<td style="font-size: 16px; text-align: center; color: #333;">${ notice.notice_id }</td>
								<td style="font-size: 16px; text-align: center; color: #333;">
								  <a href="<%= commonUrl %>/notice/notice_detail.do?notice_id=${ notice.notice_id }">${ notice.title }</a>
								</td>
								<td style="font-size: 16px; text-align: center; color: #333;">${ notice.admin_id }</td>
								<td style="font-size: 16px; text-align: center; color: #333;">${ notice.input_date }</td>
								<td style="font-size: 16px; text-align: center; color: #333;">${ notice.view_cnt }</td>
							</tr> 
						</c:forEach>
					</tbody>
				</table>
			</div>
			<div class="text-center">
		<nav id="pagination">
					<ul class="pagination">
						<li>
						<c:if test="${ nowPage ne 1 }">
							<a href="<%= commonUrl %>/notice/notice_list.do?page=${ nowPage-1 }" aria-label="Previous">
								<span aria-hidden="true">&laquo;</span>
							</a>
						</c:if>
						</li>
						<c:set var="num" value="${ selectedNotice }"/>
						<c:forEach var="i" begin="${ start }" end="${ end }">
						<li><a href="<%= commonUrl %>/notice/notice_list.do?page=${ i }"><c:out value="${ i }"/></a></li>
						</c:forEach>
						<li>
						<%-- <% if(LastPage != 0 && nowPage != LastPage){ %> --%>
						<c:if test="${ LastPage ne 0 and nowPage ne LastPage }">
							<a href="<%= commonUrl %>/notice/notice_list.do?page=${ nowPage+1 }" aria-label="Next">
								<span aria-hidden="true">&raquo;</span>
							</a>
							<%-- <% } %> --%>
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