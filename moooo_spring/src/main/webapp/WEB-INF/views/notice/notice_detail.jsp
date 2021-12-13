<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="공지사항 상세페이지"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sessionScope.user_id }">
<c:redirect url="${ commonUrl }/user/login/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>공지사항</title>

<!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%= commonUrl %>/view/notice/notice_detail.css">

<style type="text/css">
.notice_wrap {
	position: absolute;
	top: 0px;
	width: 70%;
	font-size: 14px;
	list-style: disc;
	margin-left: 10px;
}
</style>

<script type="text/javascript">
function notice(){
	location.href="<%= commonUrl %>/notice/notice_list.do";
}//notice
</script>
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
			<div class="notice_head">
				<h2 style="font-weight: bold;">공지사항</h2>
			</div>
			<div class="notice_border">
				<div class="notice_content">
					<table class="table" style="background-color: rgba( 255, 255, 255, 0.7 );">
						<tbody>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
								<td colspan="5" style="font-size: 16px; color: #333;"><c:out value="${ nv.title }"/></td>
							</tr>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
								<td style="font-size: 16px; color: #333;">${ nv.getInput_date() }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
								<td style="font-size: 16px; color: #333;">${ nv.getView_cnt() }</td>
							</tr>
							<tr>
								<td colspan="6" style="font-size: 16px; color: #333;"><c:out value="${ nv.comments }" escapeXml="false"/></td>
							</tr>
						</tbody>
					</table>
				</div>
				<input type="button" value="목록" onclick="notice()" class="noticeBtn"/>
			</div>
		</div>
	</div>
	</div>
	
	<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>