<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 상세페이지"%>
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
<title>팔아요</title>

<!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
.notice_wrap { position: absolute; top: 0px; width: 70%; font-size: 14px; list-style: disc; margin-left: 10px; }
.notice_head { margin-bottom: 10px; }
.notice_border { margin-top: 40px; margin-bottom: 40px; margin-left: 10px; margin-right: 10px; padding: 10px; }
.noticeBtn { font-size: 20px; 	background-color: #494949; color: #FFFFFF; border: 1px solid #494949; border-radius: 5px; 
	width: 90px; margin-top: 40px; }
#writeFrm{border:1px solid #333; background-color: #FFFFFF; padding:50px; width:400px;height: 300px;}
#formArea{position: absolute;top:100px;left:250px;}
a:hover{ text-decoration: none; }
.btnArea{ width: 200px; margin: 0px auto; }
.sell_content{ background-color: rgba( 255, 255, 255, 0.7 ); }
</style>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=key!!!"></script>
<script>
$(function() {
	var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
	    mapOption = { 
	        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
	        level: 3 // 지도의 확대 레벨
	    };
	
	// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
	var map = new kakao.maps.Map(mapContainer, mapOption); 
});
</script>

<script type="text/javascript">
$(function() {
	<c:if test="${ not empty param.msg }">
	alert("${ param.msg }");
	location.href="${ commonUrl }" + "${ param.url }"+${ sell.sell_id };
	</c:if>
	
	$("#reportBtn").click(function() {
		let radioNode=document.frm.reason;
		let reasonChk="";
		for(var i=0; i<radioNode.length; i++){
			if(radioNode[i].checked){
				reasonChk=radioNode[i].value;
			}//end if
		}//end for
		
		if(reasonChk == ""){
			alert("신고이유를 선택해주세요.");
			return;
		}//end if
		
		if( confirm("신고 후 취소할 수 없습니다.\n신고하시겠습니까?") ){
			//alert(reasonChk);
			$("#frm").submit();
		} //end if
	}); //click
	
	$("#closeBtn").click(function() {
		location.href="<%= commonUrl %>/want_sell/want_sell_detail.do?sell_id="+${ sell.sell_id };
	}); //click
	
	$("#listBtn").click(function() {
		location.href="<%= commonUrl %>/want_sell/want_sell.do";
	}); //click
}); //ready

function chkMe( sell_id, write_user_id ) {
	if( write_user_id == "${ sessionScope.user_id }" ){
		alert("본인은 신고할 수 없습니다.");
		return;
	} else {
		location.href="want_sell_detail.do?sell_id="+sell_id+"&report_id="+write_user_id;
	} //end else
} //chkMe

function chkUserEdit( sessionId, writer ) {
	if(sessionId == writer){
		location.href="<%= commonUrl %>/want_sell/ws_write.do?sell_id="+${ sell.sell_id };
	} else{
		alert("작성자만 수정할 수 있습니다.");
	}
} //chkUserEdit

function chkUserDelete( sessionId, writer ) {
	if(sessionId == writer){
		location.href="<%= commonUrl %>/want_sell/want_sell_delete.do?sell_id="+${ sell.sell_id };
	} else{
		alert("작성자만 삭제할 수 있습니다.");
	}
} //chkUserDelete

//이미지 업로드한 거 보이기
function readURL(input) {
	if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function (e) {
	        $('#viewImg').attr('src', e.target.result);
	    }
	    reader.readAsDataURL(input.files[0]);
	}//end if
}//readURL
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
				<h2 style="font-weight: bold;">팔아요</h2>
			</div>
			
		<div style="float: right;">
			<div id="formArea">
			<c:if test="${ not empty param.report_id }">
			<div id="writeFrm">
			<form id="frm" name="frm" action="report_proc.do" method="get">
				<input type="hidden" name="reported_user_id" value="${ sell.user_id }"/>
				<input type="hidden" name="sell_id" value="${ sell.sell_id }"/>
      			<c:import url="${ commonUrl }/report.do"/>
      		</form>
      		</div>
      		</c:if>
			</div>
			<c:if test="${ isInterest eq false }">
			<a href="interest.do?sell_id=${ sell.sell_id }&interest=n"><span style="font-size: 35px;font-weight: bold;">&star;</span></a>
			</c:if>
			<c:if test="${ isInterest eq true }">
			<a href="interest.do?sell_id=${ sell.sell_id }&interest=y"><span style="font-size: 35px;font-weight: bold;">&starf;</span></a>
			</c:if>
			<a href="#" onclick="chkMe('${ sell.sell_id }', '${ sell.user_id }')">신고하기</a>
		</div>
			
			<div class="notice_border">
				<div class="sell_content">
					<table class="table">
						<tbody>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">제목</td>
								<td colspan="5" style="font-size: 16px; color: #333;">${ sell.title }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성자</td>
								<td style="font-size: 16px; color: #333;">${ sell.user_id }</td>
							</tr>
							<tr>
								<td style="font-weight: bold; font-size: 16px; color: #333;">작성일</td>
								<td style="font-size: 16px; color: #333;">${ sell.input_date }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">가격</td>
								<td style="font-size: 16px; color: #333;">${ sell.string_price }원</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">조회수</td>
								<td style="font-size: 16px; color: #333;">${ sell.view_cnt }</td>
								<td style="font-weight: bold; font-size: 16px; color: #333;">관심수</td>
								<td style="font-size: 16px; color: #333;">${ sell.interest_cnt }</td>
							</tr>
							<tr>
								<td colspan="8" style="font-size: 16px; color: #333;">${ sell.comments }</td>
							</tr>
						</tbody>
					</table>
					<div>
					<c:if test="${ not empty requestScope.sell_img }">
					<c:forEach var="img" items="${ requestScope.sell_img }">
					<img id="viewImg" src="<%= commonUrl %>/upload/${ img }" width="300px;">
					</c:forEach>
					</c:if>
					</div>
				</div>
				
				<div id="map" style="width:100%;height:350px;"></div>
				
				<div class="btnArea">
				<button type="button" class="btn btn-default" id="listBtn">목록</button>
				<button type="button" class="btn btn-primary" onclick="chkUserEdit('${sessionScope.user_id}','${ sell.user_id }')">수정</button>
				<button type="button" class="btn btn-danger" onclick="chkUserDelete('${sessionScope.user_id}','${ sell.user_id }')">삭제</button>
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
