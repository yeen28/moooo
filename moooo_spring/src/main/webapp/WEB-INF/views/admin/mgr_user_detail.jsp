<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자 회원관리 상세페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sessionScope.admin_id }">
<c:redirect url="/admin/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>MooOO 관리자</title>

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<style type="text/css">
body{height:100%;}

.right { position: absolute; top: 100px; left: 450px; }

.left-nav{width: 300px; height: 100%; background-color: #CFCFCF;position: absolute;}
.left-nav>ul{list-style: none;padding-top:30px;}
a{color: #333;}
.menu{border:1px solid #CFCFCF;}

th{ background-color: #dfdfdf; width: 200px; font-size: 15px; text-align: center; }
#detailView{ margin-top: 50px; }
</style>

<script type="text/javascript">
$(function() {
	$("#deleteBtn").click(function() {
		if( confirm("정말 ${ member.user_id } 회원을 삭제하시겠습니까?") ){
			location.href="${ commonUrl }/admin/delete_member.do?user_id=${ member.user_id }";
		} //end if
	}); //click
	
	$("#listBtn").click(function() {
		location.href="${ commonUrl }/admin/mgr_user.do";
	}); //click
}); //ready

function detail( user_id ) {
	$.ajax({
		url: "report_detail.do",
		type: "get",
		data: { reported_user_id : user_id },
		dataType: "json",
		error: function( xhr ) {
			console.log(xhr.status+" / "+xhr.statusText);
		},
		success: function( jsonObj ) {
			let jsonArr=jsonObj.data;
			let result="<table class='table table-striped' style='width: 80%;'><thead><tr><td>신고 이유</td>";
			result+="</tr></thead><tbody><tr><td>";
			
			if(jsonArr.length == 0){
				result += "신고 이력이 없습니다.";
			} else {
				for(var i=0; i<jsonArr.length; i++){
					result += jsonArr[i].reason+"<br/>";
				} //end for
			} //end else
			result+="</td></tr>	</tbody></table>";
			$("#detailView").html( result );
		}
	}); //ajax
} //detail
</script>
</head>
<body>
<jsp:include page="layout/header.jsp"/>

<!-- 왼쪽 메뉴바 -->
<div class="left-nav">
<ul class="nav nav-pills nav-stacked">
  <li role="presentation"><a href="<%= commonUrl %>/admin/main.do"><span class="glyphicon glyphicon-home">&nbsp;홈</span></a></li>
  <li role="presentation" class="active"><a href="<%= commonUrl %>/admin/mgr_user.do"><span class="glyphicon glyphicon-user">&nbsp;회원관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_how_to.do"><span class="glyphicon glyphicon-pencil">&nbsp;이용방법수정</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/mgr_notice.do"><span class="glyphicon glyphicon-th-list">&nbsp;공지사항관리</span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/admin/change_pass.do"><span class="glyphicon glyphicon-cog">&nbsp;비밀번호변경</span></a></li>
<li></li>
</ul>
</div>
<!-- /왼쪽 메뉴바 -->

<div class="right">
<h2>회원관리</h2>
<br/>
<br/>
<div>
<table class="table table-striped" style="width: 80%;">
<thead>
<tr>
	<th>이미지</th>
	<td>
		<c:choose>
		<c:when test="${ empty member.img }">
			<img src="<%= commonUrl %>/common/images/defaultImg.png" alt="image" height="100"><br/>
		</c:when>
		<c:otherwise>
			<img src="<%= commonUrl %>/upload/${ member.img }" alt="image" height="100"><br/>
		</c:otherwise>
		</c:choose>
	</td>
</tr>
<tr>
	<th>아이디</th>
	<td><c:out value="${ member.user_id }"/></td>
</tr>
<tr>
	<th>닉네임</th>
	<td><c:out value="${ member.nickname }"/></td>
</tr>
<tr>
	<th>휴대폰번호</th>
	<td><c:out value="${ member.phone }"/></td>
</tr>
<tr>
	<th>신고누적수</th>
	<td><c:out value="${ member.reported_cnt }"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	<button class="btn btn-default" onclick="detail('${ requestScope.member.user_id }')">자세히 보기</button>
</tr>
<tr>
	<th>최초가입일</th>
	<td><c:out value="${ member.input_date }"/></td>
</tr>
</thead>
<tbody>
</tbody>
</table>
</div>

<button type="button" class="btn btn-default" id="listBtn">목록</button>
<button type="button" class="btn btn-danger" id="deleteBtn">삭제</button>

<div id="detailView"></div>
</div>

</body>
</html>