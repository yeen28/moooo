<%@page import="org.springframework.dao.DataAccessException"%>
<%@page import="kr.co.sist.user.dao.WantSellDAO"%>
<%@page import="kr.co.sist.user.vo.CategoryVO"%>
<%@page import="java.util.List"%>
<%@page import="kr.co.sist.user.dao.WantBuyDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="팔아요 삭제"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sess_user_id }">
<c:redirect url="/users/login/login.jsp"/>
</c:if>

<script type="text/javascript">
if(confirm("삭제하시겠습니까?")){
<%
try{
WantSellDAO wd=new WantSellDAO();
wd.delSell(Integer.parseInt(request.getParameter("sell_id")), (String)session.getAttribute("sess_user_id"));
%>
alert("삭제되었습니다.");
location.href="<%= commonUrl %>/view/want_sell/want_sell.jsp";
<%
} catch (DataAccessException dae){
	//dae.printStackTrace();
%>
alert("접근 권한이 없습니다.");
location.href="javascript:history.back()";
<%
}//end catch
%>

}else{
	location.href="javascript:history.back()";
}
</script>