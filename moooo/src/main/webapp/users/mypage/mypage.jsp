<%@page import="kr.co.sist.upload.UploadFileService"%>
<%@page import="kr.co.sist.user.vo.UserVO"%>
<%@page import="kr.co.sist.user.dao.MypageDAO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sess_user_id }">
<c:redirect url="../login/login.jsp"/>
</c:if>

<% 
String sess_id=(String)session.getAttribute("sess_user_id"); 
pageContext.setAttribute("sess_user_id", sess_id);
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<style type="text/css">
#container{	margin: 80px auto; }
#nav{
height: 50px;
}
#nav_table{
height: 50px;
}
.right_wrap {
	position: absolute;
	top: 0px;
	width: 70%;
	font-size: 14px;
	list-style: disc;
	margin-left: 10px;
}
#Logo{
	width: 1400px;
	height: 130px;
	text-align:left;
/* 	background-color: #FFE08C; */
}
#profile{
	border: 1px solid #000000; 
	height: 1000px; 
	margin: auto;
	position: relative;
/* 	background-color: #B7F0B1;	 */
}
#passCh{
float: right;
	/* top: 50px;
	left: 550px;
	position: absolute; */
}
#leave{
float: right;
	/* top: 50px;
	left: 680px;
	position: absolute; */
}
#myImg{
	top: 80px;
	left: 30px;
	position: absolute;
/* 	background-color: #D4F4FA; */
}
#viewImg{
	border: none;
	width: 100px;
	height: 100px;
	top: 30px;
	left: 5px;
	position: absolute;
/* 	background-color: #FFE08C; */
}
#img{
	top: 130px;
	left: 5px;
	position: absolute;
}
#id1{
	top: 350px;
	left: 30px;
	position: absolute;
}
#phone1{
	top: 500px;
	left: 30px;
	position: absolute;
}
#save{
	top: 600px;
	left: 600px;
	position: absolute;
}
#item_margin {
	margin: 0px auto;
}
#main_user {
	position: absolute;
	top: 700px;
	width : 780px;
	height: 200px;
	margin: 0px auto;
	padding: 20px;
	border: 1px solid #333;
	border-radius: 15px;
	overflow: scroll;
}
</style>
<!-- jQuery CDN -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<script type="text/javascript">
$(function() {
	$("#chk_nickname_dup").click(function() {
		window.open("dup_nickname.jsp", "dup", "width=500,height=400");
	});	//click
	
	$("#addSave").click(function() {
		
		if( confirm("회원정보를 변경하시겠습니까?") ){

			let upload=$("#img").val();
			if(upload != ""){
			//업로드가능 확장자의 유효성검증.
			//서버에서 실행되는 언어와 같은 소스코드는 업로드하지 못하도록 막아야 함.
			//이미지파일(jpg, png, gif, bmp)만 업로드할 수 있도록 검증.
			
			let fileExt = ["jpg","png","gif","bmp"];
			let fileFlag = false;
			
			let ext = (upload.substring(upload.lastIndexOf(".")+1)).toLowerCase();
			for( var i=0; i<fileExt.length; i++ ){
				if( ext == fileExt[i] ){
					fileFlag = true;
					break;
				}//end if
			}//end for
			
			if( !fileFlag ){
				alert("업로드 가능 확장자가 아닙니다.");
				return;
			}//end if
			}
			
			//hidden form 에 존재하는 Control에 값 설정
			//$("#img").val(upload);
			
			$("#frm").submit();
		}//end if
		
	}); //click
}); //ready

// 이미지 업로드한 거 보이기
function readURL(input) {
if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
        $('#viewImg').attr('src', e.target.result);
    }
    reader.readAsDataURL(input.files[0]);
}
}

/* function update(id) {
	if( confirm(id+"님의 회원정보를 변경하시겠습니까?") ){
		//hidden form 에 존재하는 Control에 값 설정
		$("#update_nickname").val($("#nickname").val());
		$("#update_phone").val($("#phone").val());
		$("#update_img").val($("#img").val());
		
		$("#hiddenFrm").submit();
	}//end if
}//update */
</script>
</head>

<body>
<!-- header -->
<jsp:include page="/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.jsp"/>
	
	<div id="right">
	<div class="right_wrap">
	<div id="nav">
	<table id="nav_table" class="table" border="1">
	<tr>
	<td>
	 <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/mypage/mypage.jsp?nav=info" style="padding: 10px;">정보변경</a>
	</td>
	<td>
	 <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/mypage/mypage.jsp?nav=writeList" style="padding: 10px;">내가 쓴 글</a>
	</td>
	<td>
	<a href="<%= protocol %><%= domain %><%= contextRoot %>/users/mypage/mypage.jsp?nav=interest" style="padding: 10px;">관심글</a>
	</td>
	<td>
	<a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/change_password.jsp" style="padding: 10px;">비밀번호 변경</a>
	</td>
	<td>
	 <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/leave.jsp" style="padding: 10px;">회원탈퇴</a>
	</td>
	</tr>
	</table>
	</div>
	
	<%
	MypageDAO md=new MypageDAO();
	UserVO uv=md.selMypage(sess_id);
	pageContext.setAttribute("uv", uv);
	%>
	<%
	UploadFileService ufs=new UploadFileService();
	//ufs.searchFile();
	pageContext.setAttribute( "imgFile", ufs.searchFile() );
	%>
	
	<form action="../mypage/proc/update_proc.jsp" id="frm" method="post" enctype="multipart/form-data">
	<div id="profile"><br/>
		<div id="myImg">
			<div style="text-align: left;">이미지</div>
			<% if( uv.getImg() == null ) { %>
			<img id="viewImg" src="<%= protocol %><%= domain %><%= contextRoot %>/common/images/defaultImg.png" alt="image"><br/>
			<% } else { %>
			<img id="viewImg" src="<%= protocol %><%= domain %><%= contextRoot %>/common/images/upload/${uv.img}" alt="image"><br/>
			<% } %>
			<input type="file" name="img" id="img" onchange="readURL(this);"/><br/><br/>
			<%-- <c:if test="">
			<input type="hidden" name="img" value="${ uv.img }"/>
			</c:if> --%>
		</div>

	<div id="id1">
		<div style="text-align: left;">*닉네임</div>
		<input type="text" value="${ uv.nickname }" name="nickname" id="nickname" class="form-control" style="width: 150px; height: 40px; font-size: 15px;"/>		
		<input type="button" value="중복확인" class="check_btn" id="chk_nickname_dup"/>
		<input type="hidden" name="user_id" value="${ sess_user_id }"/> 
	</div>
	
	<div id="phone1">
		<div style="text-align: left;">*휴대폰 번호</div>
		<input type="text" name="phone" id="phone" class="form-control" Placeholder="핸드폰 번호" value="${ uv.phone }"
				style="width: 300px; height: 40px; font-size: 15px;"/>		
	</div>
	
	<%-- <div class="item_margin" id="item_margin">
	 <div class="main1" id="main_user">
		<%
		request.setCharacterEncoding("UTF-8");
		%>
		<%
		MainDAO mDAO = new MainDAO();
		List<UserVO> ulist = mDAO.selectAllUser();
		pageContext.setAttribute("userData", ulist);
		%>
		<h3>관심글 목록</h3>
		<table id="tbl">
			<tbody>
				<c:forEach var="users" items="${ userData }">
					<tr>
						<td style="font-size: 14px"><c:out value="${ users.id }"></c:out></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div> --%>
	
	<div>
		<p id="save">
			<button type="button" class="btn btn-primary btn-lg" id="addSave" style="width: 150px;">수정</button>
		</p>
	</div>
	
	</div>
	</form>
	</div><!-- /<div class="notice_wrap"> -->
	</div><!-- /<div id="right"> -->
	</div><!-- /<div id="container"> -->
	
<%-- <form name="hiddenFrm" id="hiddenFrm" method="post" action="proc/update_proc.jsp">
	<input type="hidden" name="user_id" value="${ sess_user_id }"/> 
	<input type="hidden" name="nickname" id="update_nickname"/> 
	<input type="hidden" name="phone" id="update_phone"/> 
	<input type="hidden" name="img" id="update_img"/> 
</form> --%>

<div style="clear:both;">
<jsp:include page="/layout/footer.jsp"/>
</div>

</body>
</html>