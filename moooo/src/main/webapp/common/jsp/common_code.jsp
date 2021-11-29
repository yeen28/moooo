<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%--
//////////////////////// 공통 정보를 저장하는 JSP ////////////////////////////////
// include 지시자로 include하여 사용
/////////////////////////////////////////////////////////////////////////////////////
--%>

<%
String title="MooOO";

String protocol="http://";
String domain="localhost";
//String domain="211.63.89.151";
String contextRoot="/moooo";
//String contextRoot="";

String common_css="/common/css";
String common_js="/common/js";
String common_jsp="/common/jsp";
String common_images="/common/images";

String commonUrl=protocol+domain+contextRoot;

pageContext.setAttribute("commonUrl", commonUrl);
%>