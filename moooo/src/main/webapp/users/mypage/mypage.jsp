<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<style type="text/css">
div{
	text-align: center;
}
#container{
	margin: 80px auto;
}
#Logo{
	width: 1400px;
	height: 130px;
	text-align:left;
/* 	background-color: #FFE08C; */
}
#profile{
	border: 1px solid #000000; 
	width: 800px; 
	height: 1000px; 
	margin: auto;
	position: relative;
/* 	background-color: #B7F0B1;	 */
}
#passCh{
	top: 50px;
	left: 550px;
	position: absolute;
}
#leave{
	top: 50px;
	left: 680px;
	position: absolute;
}
#img{
	top: 80px;
	left: 30px;
	position: absolute;
/* 	background-color: #D4F4FA; */
}
#profimg{
	border: none;
	width: 200px;
	height: 200px;
	top: 30px;
	left: 5px;
	position: absolute;
/* 	background-color: #FFE08C; */
}
#upfile{
	top: 230px;
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
	});	
}); //ready

function insert() {
	let upfile=$("#upfile").val();
	
	//업로드가능 확장자의 유효성검증.
	//서버에서 실행되는 언어와 같은 소스코드는 업로드하지 못하도록 막아야 함.
	//이미지파일(jpg, png, gif, bmp)만 업로드할 수 있도록 검증.
	
	let fileExt = ["jpg","png","gif","bmp"];
	let fileFlag = false;
	
	let ext = (upfile.substring(upfile.lastIndexOf(".")+1)).toLowerCase();
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
	
	$("#frm").submit();
}//insert	

// 이미지 업로드한 거 보이기
function readURL(input) {
if (input.files && input.files[0]) {
    var reader = new FileReader();
    reader.onload = function (e) {
        $('#profimg').attr('src', e.target.result);
    }
    reader.readAsDataURL(input.files[0]);
}
}

function update(id) {
//업데이트
//function updateData( id, i ){
if( confirm(id+"님의 회원정보를 변경하시겠습니까?") ){
	
	//hidden form 에 존재하는 Control에 값 설정
	$("#update_phone").val($("#phone").val());
	$("#update_description").val($("#description").val());
	$("#update_url").val($("#url").val());
	$("#update_img").val($("#img").val());
	$("#update_tech_idx").val($("#tech_idx").val());
	
	$("#hiddenFrm").submit();
	
}//end if

//};//updateData
}//update
</script>
</head>
<body>
<jsp:include page="/layout/header.jsp"/>
	<div id="container">
	
	<form id="frm" name="frm" method="post" action="insert_proc.jsp">
		
	<div id="profile"><br/>
			<div id="passCh">
			  <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/change_password.jsp">비밀번호 변경</a>
			</div>
			<div id="leave">
			  <a href="<%= protocol %><%= domain %><%= contextRoot %>/users/login/leave.jsp">회원탈퇴</a>
			</div>
				<div id="img">
					<div style="text-align: left;">*이미지</div>
					
					<img id="profimg"><br/>
						<input type="file" name="img" id="upfile" onchange="readURL(this);"/><br/><br/>
						<c:out value="${ pv.img }"/>
			</div>

	<div id="id1">
		<div style="text-align: left;">*닉네임</div>
		<input type="text" value="${ nickname }" name="nickname" class="form-control" style="width: 150px; height: 40px; font-size: 15px;"/>		
		<input type="button" value="중복확인" class="check_btn" id="chk_nickname_dup"/>
		<input type="hidden" name="user_id" value=""/>
	</div>
	
	<div id="phone1">
		<div style="text-align: left;">*휴대폰 번호</div>
		<input type="text" name="phone" id="phone" class="form-control" Placeholder="핸드폰 번호를 입력하세요." value=""
				style="width: 300px; height: 40px; font-size: 15px;"/>		
	</div>
	
	<div class="item_margin" id="item_margin">
	 <div class="main1" id="main_user">
		<%-- <%
		request.setCharacterEncoding("UTF-8");
		%>
		<%
		MainDAO mDAO = new MainDAO();
		List<UserVO> ulist = mDAO.selectAllUser();
		pageContext.setAttribute("userData", ulist);
		%> --%>
		<h3>관심글 목록</h3>
		<table id="tbl">
			<tbody>
				<%-- <c:forEach var="users" items="${ userData }"> --%>
					<tr>
						<td style="font-size: 14px"><c:out value="${ users.id }"></c:out></td>
					</tr>
				<%-- </c:forEach> --%>
			</tbody>
		</table>
	</div>
</div>
	
	<div>
		<p id="save">
			<button type="button" class="btn btn-primary btn-lg" id="addSave"
				style="width: 150px;" onclick="">수정</button>
		</p>
	</div>
	
	</div>
	</form>
	</div>
	
<jsp:include page="/layout/footer.jsp"/>

<form name="hiddenFrm" id="hiddenFrm" method="post" action="update_proc.jsp">
	<input type="hidden" name="user_id" value="${ id }"/> 
	<input type="hidden" name="phone" id="update_phone"/> 
	<input type="hidden" name="img" id="update_img"/> 
</form>

</body>
</html>