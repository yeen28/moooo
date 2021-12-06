<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@page import="java.io.File"%>
<%@page import="org.springframework.dao.DataAccessException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<%-- <%
	//1. 파일업로드 컴포넌트 생성. ( 파일이 업로드 된다. )
//File uploadPath=new File("C:/Users/user/git/moooo/moooo/src/main/webapp/common/images/upload");
File uploadPath=new File("E:/moooo/common/images/upload");
if( !uploadPath.exists() ){ //업로드 폴더가 존재하지 않으면 (반드시 있을 거라고 생각하면 안 써도 된다.)
	uploadPath.mkdirs();
}//end if
int maxSize=1024*1024*10;
//생성되면 파일업로드가 된다.
MultipartRequest mr=new MultipartRequest( request, uploadPath.getAbsolutePath(), maxSize, "UTF-8", new DefaultFileRenamePolicy() );

//파일 컴포넌트를 사용하여 파라메터 받기
String user_id=mr.getParameter("user_id");
//파일명 얻기
String originName=mr.getOriginalFileName("img"); //원본 파일명
String fileSystemName=mr.getFilesystemName("img"); //변경된 파일명

MemberVO uv=new MemberVO();
uv.setImg(fileSystemName);
%> --%>

<script type="text/javascript">
<%-- <%
MypageDAO md = new MypageDAO();
try{
	//이전 파일은 삭제하기
	String beforeFileName=md.selMypage(user_id).getImg();
	//File deleteImg=new File("C:/Users/user/git/moooo/moooo/src/main/webapp/common/images/upload/"+beforeFileName);
	File deleteImg=new File("E:/moooo/common/images/upload/"+beforeFileName);
	if(deleteImg.exists() && deleteImg.isFile()){ //이전 이미지가 존재한다면
		if(deleteImg.delete()){ //사진 삭제
		//System.out.println("삭제 : "+beforeFileName);
		}//else{System.out.println("실패");}
	}
	md.updateMypage(uv);
%> --%>
location.href="javascript:history.back()";
<%-- <%} catch (DataAccessException dae){ %>
location.href="<%= protocol %><%= domain %><%= contextRoot %>/common/error/error.do";
<%}%> --%>
</script>	