<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="header"
    %>
<%@ include file="/common/jsp/common_code.jsp" %>   
<style type="text/css">
#header{background-color: #D2ECBF; width: 100%; height: 300px;}
#header>h1{text-align: center;}
#title{ width: 300px; text-align: center; margin: 0px auto; padding: 150px 30px 0 30px; }
#title>img{float:left;}
</style>

<div id="header">
<div id="title">
<img src="<%= protocol %><%= domain %><%= contextRoot %>/common/images/moooo.png" width="50px;"/>
<h1><a href="<%= protocol %><%= domain %><%= contextRoot %>/index.jsp">MooOO</a></h1>
</div>
</div>