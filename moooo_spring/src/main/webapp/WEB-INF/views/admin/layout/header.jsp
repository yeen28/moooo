<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자페이지 헤더"
    %>
<%@ include file="/common/jsp/common_code.jsp" %>

<style type="text/css">
.top-nav{height: 50px; background-color: #2D394A;}
.logo{float:left; width: 100px; margin-left: 10px;}
.logo>h3>a{color:#fff;}
.logo>h3>a:hover{text-decoration: none;}
</style>

<div class="top-nav">
<div class="logo"><h3><a href="<%= commonUrl %>/admin/main.do">MooOO</a></h3></div>
<table style="width: 100px;height:50px;text-align: center;float:right;">
<tr>
<td><a href=""><span class="glyphicon glyphicon-bell" style="color: #fff; font-size: 17px;"></span></a></td>
<td>
<div class="dropdown">
  <a id="dLabel" data-target="#" href="http://example.com" data-toggle="dropdown" aria-haspopup="true" role="button" aria-expanded="false">
    <span class="glyphicon glyphicon-cog" style="color: #fff; font-size: 17px;"></span>
    <span class="caret"></span>
  </a>

  <ul class="dropdown-menu dropdown-menu-right" role="menu" aria-labelledby="dLabel" style="position: absolute; right: -500;">
  <li><a href="<%= commonUrl %>/admin/change_pass.do">비밀번호 변경</a></li>
  <li><a href="logout.do">로그아웃</a></li>
  </ul>
</div>
</td>
</tr>
</table>
</div>
