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
<div class="logo"><h3><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/main.jsp">MooOO</a></h3></div>
<table style="width: 100px;height:50px;text-align: center;float:right;">
<tr>
<td><a href=""><span class="glyphicon glyphicon-bell" style="color: #fff; font-size: 17px;"></span></a></td>
<td><a href="<%= protocol %><%= domain %><%= contextRoot %>/admin/change_pass.jsp"><span class="glyphicon glyphicon-cog" style="color: #fff; font-size: 17px;"></span></a></td>
</tr>
</table>
</div>
