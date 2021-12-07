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
</script>
</head>

<body>
<%-- <%
String param1 = request.getParameter("buy_id");
int buy_id = Integer.parseInt(param1);
WantBuyDAO wd = new WantBuyDAO();
WantBuyVO wv = wd.selectBuy(buy_id);
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
			</div>
			<div class="notice_border">
				<div class="notice_content">
					<table class="table">
						<tbody>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
								<td colspan="3" style="font-size: 16px; color: #333;">${ buy.getTitle() }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성자</td>
								<td style="font-size: 16px; color: #333;">${ buy.getUser_id() }</td>
							</tr>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
								<td style="font-size: 16px; color: #333;">${ buy.getInput_date() }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">가격</td>
								<td style="font-size: 16px; color: #333;">${ buy.getPrice() }원</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
								<td style="font-size: 16px; color: #333;">${ buy.getView_cnt() }</td>
							</tr>
							<tr>
								<td colspan="6" style="font-size: 16px; color: #333;">${ buy.getComments() }</td>
							</tr>
						</tbody>
					</table>
				</div>
				<a href="<%= commonUrl %>/want_buy/want_buy.do">목록</a>
				<a href="#void" onclick="chkUserEdit('${sessionScope.user_id}','${ buy.getUser_id() }')">수정</a>
				<a href="#void" onclick="chkUserDelete('${sessionScope.user_id}','${ buy.getUser_id() }')">삭제</a>
			</div>
		</div>
	</div>
	</div>
	
<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

</body>
</html>