<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 삭제"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty user_id }">
<c:redirect url="/user/login/login.do"/>
</c:if>

<script type="text/javascript">
if(confirm("삭제하시겠습니까?")){
<%-- <%
try{
WantBuyDAO wd=new WantBuyDAO();
wd.delBuy(Integer.parseInt(request.getParameter("buy_id")));
%> --%>
alert("삭제되었습니다.");
location.href="<%= commonUrl %>/view/want_buy/want_buy.jsp";
<%-- <%
} catch (DataAccessException dae){
%> --%>
문제발생
<%-- <%
}//end catch
%> --%>
}else{
	location.href="javascript:history.back()";
}
</script>