<%@page import="java.sql.SQLException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="로그인 처리"
%>
<%@ include file="/common/jsp/common_code.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script type="text/javascript">
<c:choose>
<c:when test="${ not empty msg }">
alert("${ msg }");
</c:when>
<c:otherwise>
<c:redirect url="${ commonUrl }/index.do"/>
</c:otherwise>
</c:choose>
</script>