<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="왼쪽 메뉴바"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %> 

<link rel="stylesheet" href="<%= commonUrl %>/common/css/login_common.css">

<style type="text/css">
#container{ width: 80%; margin: 80px auto; position: relative; }
#side {width: 350px; margin: 30px 0 30px 0; padding: 30px 30px 30px 30px}
#user{
	width: 350px;
	text-align: left;
	padding: 30px;
}
#left{
	width: 350px;
	float: left;
}
#right {
	height: 1000px;
	float: left;
	margin-left: 50px;
}
#login, #side{
	background-color: #D2ECBF;background-color: rgba( 255, 255, 255, 0.9 );
}
</style>

<div id="left" style="float:left">
<div id="login">
<!-- login -->
<c:choose>
<c:when test="${ empty sessionScope.user_id }">
<div id="left_login">
	<div id="account" class="sc_login">
	<a href="<%= commonUrl %>/user/login/login.do" class="link_login">로그인</a>
	<div class="sub_area">
	<div class="look_box">
		<a href="<%= commonUrl %>/user/login/find_form.do" class="link_look">아이디</a>
		<a href="<%= commonUrl %>/user/login/find_form.do" class="link_look">비밀번호찾기</a>
	</div>
	<a href="<%= commonUrl %>/user/login/sign_up.do" class="link_join">회원가입</a>
	</div>
	</div>
</div>
</c:when>
<c:otherwise>
<div id="user">
  <h3>${ sessionScope.user_id }</h3>
  <a href="<%= commonUrl %>/user/mypage/mypage_form.do">내정보</a>
  <a href="<%= commonUrl %>/user/login/logout_proc.do">로그아웃</a>
  <br/><br/><br/>
  <table class="table table2">
  <tr>
    <td class="t-label" style="padding-left: 0;">전화번호</td>
    <td class="t-label">
  <c:out value="${ requestScope.phone }"/><br/>
	</td>
  </tr>
  </table>
</div>
</c:otherwise>
</c:choose>
</div><!-- /<div id="login"> -->


<div id="side">
<h4><a href="<%= commonUrl %>/notice/notice_list.do">공지사항</a></h4>
<hr>
<h4><a href="<%= commonUrl %>/want_buy/want_buy.do">사고싶어요</a></h4>
<ul>
<c:forEach var="category" items="${ requestScope.listCategory }">
  <li><a href="<%= commonUrl %>/want_buy/want_buy.do?category=${ category.category_id }"><c:out value="${ category.name }"/></a></li>
</c:forEach>
</ul>
<hr>
<h4><a href="<%= commonUrl %>/want_sell/want_sell.do">팔아요</a></h4>
<ul>
<c:forEach var="category" items="${ requestScope.listCategory }">
  <li><a href="<%= commonUrl %>/want_sell/want_sell.do?category=${ category.category_id }"><c:out value="${ category.name }"/></a></li>
</c:forEach>
</ul>
<hr>
</div>

</div><!-- /<div id="left"> -->