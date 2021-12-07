<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="메인화면"
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

<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%= commonUrl %>/common/css/login_common.css">
<link rel="stylesheet" href="<%= commonUrl %>/common/css/index.css">
</head>

<body>
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div id="container">

<!-- 왼쪽 바 -->
<jsp:include page="/layout/side_left.do"/>

<div id="right">
<!-- 이용방법 -->
<div id="notice" class="panel panel-default">
	<div class="panel-heading notice_subtitle">
		<span class="notice_subtitle_text">이용방법</span>
	</div>
<div class="panel-body how">
  <c:out value="${ howToUse }" escapeXml="false"/>
</div>
</div><!-- /<div id="notice"> -->

<div class="blank"></div>

<div id="right-mid">
<!-- 사고싶어요 -->
<div id="want_buy" class="panel panel-default">
	<div class="panel-heading notice_subtitle">
		<span class="notice_subtitle_text">사고싶어요</span>
			<div id="moreView">
				<a href="<%= commonUrl %>/want_buy/want_buy.do">
					<span id="moreView"><img src="<%= commonUrl %>/common/images/icons/moreView.PNG" width="25px"/></span>
				</a>
			</div>
	</div>
<div class="panel-body">
	<ul id="ul-list">
		<c:forEach var="buy" items="${ listBuyTitle }">
			<li><a href="<%= commonUrl %>/view/want_buy/want_buy.do"><c:out value="${ buy.title }"/></a></li>
		</c:forEach>
	</ul>
</div>
</div><!-- /<div id="want_buy"> -->

<!-- 팔아요 -->
<div id="want_sell" class="panel panel-default">
	<div class="panel-heading notice_subtitle">
		<span class="notice_subtitle_text">팔아요</span>
			<div id="moreView">
				<a href="<%= commonUrl %>/view/want_sell/want_sell.do">
					<span id="moreView"><img src="<%= commonUrl %>/common/images/icons/moreView.PNG" width="25px"/></span>
				</a>
			</div>
	</div>
<div class="panel-body">
	<ul id="ul-list">
		<c:forEach var="sell" items="${ listSellTitle }">
			<li><a href="<%= commonUrl %>/view/want_sell/want_sell.do"><c:out value="${ sell.title }"/></a></li>
		</c:forEach>
	</ul>
</div>
</div><!-- /<div id="want_sell"> -->
</div><!-- /<div id="right-mid"> -->
</div><!-- /<div id="right"> -->
</div><!-- /<div id="container"> -->

<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>
</body>
</html>