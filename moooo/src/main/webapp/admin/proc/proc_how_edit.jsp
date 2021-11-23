<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="kr.co.sist.admin.dao.HowToDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자-이용방법 수정"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<%
request.setCharacterEncoding("UTF-8");

String comm=request.getParameter("comments");

HowToDAO hd=new HowToDAO();
try{
hd.updateHowTo(comm);
%>
<script type="text/javascript">
alert("수정했습니다.");
location.href="<%= protocol %><%= domain %><%= contextRoot %>/admin/mgr_how_to.jsp";
</script>
<%
} catch (DataAccessException dae){
	dae.printStackTrace();
	out.print("문제가 발생했습니다.");
}
%>