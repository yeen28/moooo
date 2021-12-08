<%@page import="org.springframework.dao.DataAccessException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="관리자-이용방법 수정"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<%-- <%
String comm=request.getParameter("comments");

HowToDAO hd=new HowToDAO();
try{
hd.updateHowTo(comm);
%> --%>
<script type="text/javascript">
alert("수정했습니다.");
location.href="<%= commonUrl %>/admin/mgr_how_to.do";
</script>
<%-- <%
} catch (DataAccessException dae){
	out.print("문제가 발생했습니다.");
}
%> --%>