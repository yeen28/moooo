<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="header"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %> 
<style type="text/css">
#header{background-color: #D2ECBF; width: 100%; height: 300px;}
#header>h1{text-align: center;}
#title{ width: 300px; text-align: center; margin: 0px auto; padding: 150px 30px 0 30px; }
#title>img{float:left;}
a{ color:#333; }

.nav_right>li{float: right;}
</style>


<div id="header">
<div id="navibar">
<ul class="nav nav-pills nav_right" role="tablist">
  <li role="presentation"><a href="<%= commonUrl %>/view/about/about.jsp">About</a></li>
<% if((String)session.getAttribute("sess_user_id") != null){ %>
  <li role="presentation"><a href="<%= commonUrl %>/users/login/logout.jsp"><span class="glyphicon glyphicon-log-out"></span></a></li>
  <li role="presentation"><a href="<%= commonUrl %>/users/mypage/mypage.jsp">MyPage</a></li>
  <%} else { %>
  <li role="presentation"><a href="<%= commonUrl %>/users/login/login.jsp"><span class="glyphicon glyphicon-log-in"></span></a></li>
  <!-- <li role="presentation"><a href="#">Login <span class="badge">42</span></a></li> -->
  <% } //end else %>
</ul>
</div>
<div id="title">
<img src="<%= commonUrl %>/common/images/moooo.png" width="50px;"/>
<h1><a href="<%= commonUrl %>/index.jsp">MooOO</a></h1>
</div>
</div>