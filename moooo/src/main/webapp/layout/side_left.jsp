<%@page import="kr.co.sist.user.vo.CategoryVO"%>
<%@page import="kr.co.sist.user.dao.MemberDAO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.sist.user.dao.CategoryDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="왼쪽 메뉴바"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %> 

<link rel="stylesheet" href="<%= protocol %><%= domain %><%= contextRoot %>/common/css/login_common.css">

<style type="text/css">
#container{ width: 80%; margin: 80px auto; position: relative; }
#side {width: 350px; border : 1px solid #333; margin: 30px 0 30px 0; padding: 30px 30px 30px 30px}
#user{
	width: 350px;
	text-align: left;
	padding: 30px;
	border: 1px solid #dfdfdf;
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
a{color:#333;}
</style>

<c:catch var="e">
<%
MemberDAO md=new MemberDAO();
%>
<div id="left" style="float:left">
<div id="login">
<!-- login -->
    <c:choose>
      <c:when test="${ empty sessionScope.sess_user_id }">
      <div id="left_login">
      <div id="account" class="sc_login">
      <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/login.jsp" class="link_login">로그인</a>
      <div class="sub_area">
        <div class="look_box">
        <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/find.jsp" class="link_look">아이디</a>
<a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/find.jsp" class="link_look">비밀번호찾기</a>
</div>
<a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/sign_up.jsp" class="link_join">회원가입</a>
</div>
</div>
</div>
</c:when>
<c:otherwise>
<%
String id=(String)session.getAttribute("sess_user_id");

String nickname=md.selNickname(id);
String phone=md.selectPhone(id);

pageContext.setAttribute("phone", phone);
%>
<div id="user">
  <h3><%= nickname %></h3>
  <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/mypage/mypage.jsp">내정보</a>
  <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/logout.jsp">로그아웃</a>
  <br/><br/><br/>
  <table class="table table2">
  <tr>
    <td class="t-label" style="padding-left: 0;">전화번호</td>
    <td class="t-label">
  <c:out value="${ phone }"/><br/>
	</td>
  </tr>
  </table>
</div>
</c:otherwise>
</c:choose>
</div><!-- /<div id="login"> -->


<%
CategoryDAO cd=new CategoryDAO();
List<CategoryVO> list=cd.selectAllCategory();

pageContext.setAttribute("list", list);
%>

<div id="side">
<h4><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/notice/notice.jsp">공지사항</a></h4>
<hr>
<h4><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_buy/want_buy.jsp">사고싶어요</a></h4>
<ul>
<c:forEach var="val" items="${ list }">
  <li><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_buy/want_buy.jsp?category=${ val.category_id }"><c:out value="${ val.name }"/></a></li>
</c:forEach>
</ul>
<hr>
<h4><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell.jsp">팔아요</a></h4>
<ul>
<c:forEach var="val" items="${ list }">
  <li><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/want_sell/want_sell.jsp?category=${ val.category_id }"><c:out value="${ val.name }"/></a></li>
</c:forEach>
</ul>
<hr>
<%-- <h4><a href="<%= protocol %><%= domain %><%= contextRoot %>/view/story/story.jsp">동네이야기</a></h4> --%>
</div>

</div><!-- /<div id="left"> -->
</c:catch>

<c:if test="${ not empty e }">
${ e }
문제발생했습니다. 잠시후 다시 시도해주세요.
</c:if>