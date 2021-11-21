<%@page import="kr.co.sist.user.dao.ReportDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="신고 처리"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>



<div id="writeFrm">
<form id="frm" action="calendar.jsp" method="post">
<input type="hidden" name="form_flag" value="write_process"/>
<table>
<tr>
   <td colspan="2" style="text-align: center;"><h2>일정쓰기</h2></td>
</tr>
<tr>
   <td style="height:40px;">제목</td>
   <td>
   <input type="text" name="subject" id="subject" class="inputBox" style="width:400px"/>
   </td>
</tr>
<tr>
   <td style="height:40px;">내용</td>
   <td>
   <textarea name="content" id="content" class="inputBox" style="width:400px;height:200px"></textarea>
   </td>
</tr>
<tr>
   <td style="height:40px;">작성자</td>
   <td>
   <input type="text" id="writer" name="writer" class="inputBox" style="width:400px"/>
   </td>
</tr>
<tr>
   <td>비밀번호</td>
   <td>
   <input type="password" id="pass" name="pass" class="inputBox" style="width:400px"/>
   </td>
</tr>
<tr>
   <td colspan="2" style="text-align: center;height:40px;">
   <input type="button" value="이벤트작성" class="inputBox" id="writeBtn" style="width: 80px"/>
   <input type="button" value="창닫기" class="inputBox" id="writeCloseBtn" style="width: 80px"/>
   </td>
</tr>
</table>
</form>
</div>