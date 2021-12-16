<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="header"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %> 

<style type="text/css">
#header{background-color: rgba( 255, 255, 255, 0.5 ); width: 100%; height: 300px;}
#header>h1{text-align: center;}
#title{ width: 300px; text-align: center; margin: 0px auto; padding: 150px 30px 0 30px; }
#title>img{float:left;}
a{ color:#333; }

.nav_right>li{float: right;}

body{
	width:100%;
	height:100%;
    position: relative;
}
body::after {
	width: 100%;
	height: 100%;
	content: "";
	background-image: url(${ commonUrl }/common/images/backgroundImg.jpg);
    background-repeat: no-repeat;
    background-size : cover;
    position: absolute;
    top:0;
    left: 0;
    z-index: -1; /* z-index 설정으로 가상요소를 원본의 뒤로 밀어줍니다. */
    opacity: 0.5; /* 가상요소의 opacity를 설정 */
}
</style>

<div id="header">
<div id="navibar">
<ul class="nav nav-pills nav_right" role="tablist">
  <li role="presentation"><a href="<%= commonUrl %>/about/about.do">About</a></li>
<% if((String)session.getAttribute("user_id") != null){ %>
  <li role="presentation"><a href="<%= commonUrl %>/user/login/logout_proc.do"><span class="glyphicon glyphicon-log-out"></span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/user/mypage/mypage_form.do">MyPage</a></li>
  <%} else { %>
  <li role="presentation"><a href="<%= commonUrl %>/user/login/login.do"><span class="glyphicon glyphicon-log-in"></span></a></li>
  <!-- <li role="presentation"><a href="#">Login <span class="badge">42</span></a></li> -->
  <% } //end else %>
</ul>
</div>
<div id="title">
<%-- <img src="<%= commonUrl %>/common/images/" width="50px;"/> --%>
<h1><a href="<%= commonUrl %>/index.do"><span style="font-weight: bold;">MooOO</span></a></h1>
</div>
</div>