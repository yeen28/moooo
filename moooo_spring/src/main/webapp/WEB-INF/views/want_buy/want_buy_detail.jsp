<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="사고싶어요 상세페이지"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<title>사고싶어요</title>

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
.btnArea{ width: 200px; margin: 0px auto; }
.buy_content{ background-color: rgba( 255, 255, 255, 0.7 ); }
</style>

<script type="text/javascript">
function notice(){
	location.href="<%= commonUrl %>/want_buy/want_buy.do";
}//notice

function chkUserEdit( sessionId, writer ) {
	if(sessionId == writer){
		location.href="<%= commonUrl %>/want_buy/wb_write.do?buy_id="+${ buy.getBuy_id() };
	} else{
		alert("작성자만 수정할 수 있습니다.");
	}
} //chkUserEdit

function chkUserDelete( sessionId, writer ) {
	if(sessionId == writer){
		location.href="<%= commonUrl %>/want_buy/want_buy_delete.do?buy_id="+${ buy.getBuy_id() };
	} else{
		alert("작성자만 삭제할 수 있습니다.");
	}
} //chkUserDelete

$(function() {
	$("#listBtn").click(function() {
		location.href="<%= commonUrl %>/want_buy/want_buy.do";
	});
}); //ready
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
				<h2 style="font-weight: bold;">사고싶어요</h2>
			</div>
			<div class="notice_border">
				<div class="buy_content">
					<table class="table">
						<tbody>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
								<td colspan="3" style="font-size: 16px; color: #333;">${ buy.title }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성자</td>
								<td style="font-size: 16px; color: #333;">${ buy.user_id }</td>
							</tr>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
								<td style="font-size: 16px; color: #333;">${ buy.input_date }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">가격</td>
								<td style="font-size: 16px; color: #333;">${ buy.stringPrice }원</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
								<td style="font-size: 16px; color: #333;">${ buy.view_cnt }</td>
							</tr>
							<tr>
								<td colspan="6" style="font-size: 16px; color: #333;">${ buy.comments }</td>
							</tr>
						</tbody>
					</table>
				</div>
				<div class="btnArea">
				<button type="button" class="btn btn-default" id="listBtn">목록</button>
				<button type="button" class="btn btn-primary" onclick="chkUserEdit('${sessionScope.user_id}','${ buy.user_id }')">수정</button>
				<button type="button" class="btn btn-danger" onclick="chkUserDelete('${sessionScope.user_id}','${ buy.user_id }')">삭제</button>
				</div>
			</div>
		</div>
	</div>
	</div>
	
<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>