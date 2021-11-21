<%@page import="kr.co.sist.user.vo.WantSellVO"%>
<%@page import="kr.co.sist.user.dao.WantSellDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 상세페이지"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>팔아요</title>

<!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

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
	/* border-bottom: 1px solid; */
	margin-bottom: 10px;
}
.notice_border {
	margin-top: 40px;
	margin-bottom: 40px;
	margin-left: 10px;
	margin-right: 10px;
	padding: 10px;
}
.noticeBtn {
	font-size: 20px; 
	background-color: #494949; 
	color: #FFFFFF;
	border: 1px solid #494949; 
	border-radius: 5px; 
	width: 90px;
	margin-top: 40px;
}
</style>

<script type="text/javascript">
function notice(){
	location.href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell.jsp";
}//notice
</script>
</head>
<body>
<%
String param1 = request.getParameter("sell_id");
int sell_id = Integer.parseInt(param1);
WantSellDAO wd = new WantSellDAO();
WantSellVO wv = wd.selectSell(sell_id);
%>
	<!-- header -->
	<jsp:include page="/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.jsp"/>
	
	<div id="right">
		<div class="notice_wrap">
			<div class="notice_head">
				<h2 style="font-weight: bold;">팔아요</h2>
			</div>
			
			<%-- <div style="float: right;">
			<c:if test="${ not empty param.user_id }">
      	<c:import url="../../users/report/report.jsp"/>
      	</c:if>
			<a href="want_sell_detail.jsp?user_id=<%= wv.getUser_id() %>">신고하기</a>
			</div> --%>
			
			<div class="notice_border">
				<div class="notice_content">
					<table class="table">
						<tbody>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
								<td colspan="5" style="font-size: 16px; color: #333;"><%= wv.getTitle() %></td>
							</tr>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성자</td>
								<td style="font-size: 16px; color: #333;"><%= wv.getUser_id() %></td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
								<td style="font-size: 16px; color: #333;"><%= wv.getInput_date() %></td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
								<td style="font-size: 16px; color: #333;"><%= wv.getView_cnt() %></td>
							</tr>
							<tr>
								<td colspan="6" style="font-size: 16px; color: #333;"><%= wv.getComments() %></td>
							</tr>
						</tbody>
					</table>
				</div>
				<a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell.jsp">목록</a>
				<a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell_edit.jsp?sell_id=<%= wv.getSell_id() %>">수정</a>
				<a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell_delete.jsp?sell_id=<%= wv.getSell_id() %>">삭제</a>
			</div>
		</div>
	</div>
	</div>
	
	<div style="clear:both;">
<jsp:include page="/layout/footer.jsp"/>
</div>

</body>
</html>