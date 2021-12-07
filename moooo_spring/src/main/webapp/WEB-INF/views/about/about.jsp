<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="동네이야기 첫 화면"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<style type="text/css">
.right_wrap {
	position: absolute;
	top: 0px;
	width: 70%;
	font-size: 14px;
	list-style: disc;
	margin-left: 10px;
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
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.do"/>
	
	<div id="right">
	<div class="right_wrap">
	<br/><br/><br/>
	<h4><strong>프로젝트 소개</strong></h4>
	<p>배운 지식을 최대한 적용하여 코딩실력을 향상하기 위해 제작하게 되었습니다.<br/>
	해당 프로젝트는 사용자들이 사고싶은 물건이나 팔고싶은 물건을 올릴 수 있습니다. 해당 사이트의 목적에 맞지 않게 사용하는 사용자는 신고할 수 있는 기능을 넣었습니다.<br/>
	모든 게시글들은 로그인을 해야 이용할 수 있습니다.<br/>
	추후 관리자가 신고 당한 사용자를 공고한다면 사용자들이 신고자의 아이디를 확인할 수 있는 기능을 추가하고 싶습니다.
	또한, 휴대폰 인증할 수 있는 기능을 추가하고 싶습니다.
	<br/><br/>
	1차 JAVA 프로젝트와 2차 Model1 프로젝트까지는 팀원과 함께했고, 
	3차 Spring 프로젝트는 xml 설정, 값 전달 흐름 등 전반적인 Spring에 대한 이해를 위해 혼자 해보았습니다.</p>
	<hr/>
	<h4><strong>공지사항</strong></h4>
	<a href="<%= commonUrl %>/view/notice/notice.jsp" style="color:#0000FF">[이동]</a>
	<p>관리자가 공지할 내용을 게시하면 사용자들이 확인할 수 있습니다.</p>
	<hr/>
	<h4><strong>사고싶어요</strong></h4>
	<a href="<%= commonUrl %>/view/want_buy/want_buy.jsp" style="color:#0000FF">[이동]</a>
	<p>사용자가 사고싶은 물품과 원하는 가격을 제시하여 게시글을 작성합니다.</p>
	<hr/>
	<h4><strong>팔아요</strong></h4>
	<a href="<%= commonUrl %>/view/want_sell/want_sell.jsp" style="color:#0000FF">[이동]</a>
	<p>사용자가 팔고싶은 물품과 팔기 원하는 가격, 해당 물품의 적절한 카테고리를 선택하여 게시글을 작성합니다.</p>
	<hr/>
	<h4><strong>마이페이지</strong></h4>
	<p>AJAX를 사용하여 정보변경과 사용자가 작성한 글, 관심글을 확인할 수 있도록 했습니다.</p>
	</div>
	</div><!-- /<div id="right"> -->
	</div>
<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</body>
</html>