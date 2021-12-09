<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    info="마이페이지"
    %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>

<c:if test="${ empty sessionScope.user_id }">
<c:redirect url="${ commonUrl }/user/login/login.do"/>
</c:if>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title><%= title %></title>

<style type="text/css">
#container{	margin: 80px auto; }
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
}
#view{
	height: 1000px; 
	margin: auto;
	position: relative;
}
#passCh{
float: right;
}
#leave{
float: right;
}
#myImg{
	top: 80px;
	left: 30px;
	position: absolute;
}
#viewImg{
	border: none;
	width: 100px;
	height: 100px;
	top: 30px;
	left: 5px;
	position: absolute;
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
.list {
	width : 135%;
	height: 600px;
	margin: 30px auto;
	padding: 20px;
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
	$("#myInfo").click(function() {
		/* $.ajax({
			url: "ajax_sub/myInfo.do",
			type: "post",
			dataType: "html",
			error: function( xhr ) {
				console.log( xhr.status + " / " + xhr.statusText );
			},
			success: function( htmlData ) {
				$("#view").html( htmlData );
			}
		}); //ajax */
	});
	
	$("#sell_buy").click(function() {
		$.ajax({
			url: "ajax_sub/write_list_form.do",
			type: "post",
			dataType: "html",
			error: function( xhr ) {
				console.log( xhr.status + " / " + xhr.statusText );
			},
			success: function( htmlData ) {
				$("#view").html( htmlData );
			}
		}); //ajax
	}); //click
	
	$("#interest").click(function() {
		$.ajax({
			url: "ajax_sub/interest_list.do",
			type: "post",
			dataType: "html",
			error: function( xhr ) {
				console.log( xhr.status + " / " + xhr.statusText );
			},
			success: function( htmlData ) {
				$("#view").html( htmlData );
			}
		}); //ajax
	}); //click
});//ready

$(function() {
	/* $("#chk_nickname_dup").click(function() {
		window.open("dup_nickname.jsp", "dup", "width=500,height=400");
	});	//click */
	
	$("#addSave").click(function() {
		
		if( confirm("회원정보를 변경하시겠습니까?") ){

			let upload=$("#img").val();
			if(upload != "") {
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
			}//end if
			
			//hidden form 에 존재하는 Control에 값 설정
			//$("#img").val(upload);
			
			$("#frm").submit();
		}//end if
		
	}); //click
}); //ready

//이미지 업로드한 거 보이기
function readURL(input) {
	if (input.files && input.files[0]) {
	    var reader = new FileReader();
	    reader.onload = function (e) {
	        $('#viewImg').attr('src', e.target.result);
	    }
	    reader.readAsDataURL(input.files[0]);
	}//end if
}//readURL

$(function(){
	var naviBtn = $("ul > li");    //  ul > li 이를 pageBtn 으로 칭한다. (클릭이벤트는 li에 적용 된다.)
	naviBtn .find("a").click(function(){   // pageBtn 에 속해 있는  a 찾아 클릭.
		naviBtn .removeClass("active");     // pageBtn 속에 (active) 클래스를 삭제.
		$(this).parent().addClass("active"); // 클릭한 a에 (active)클래스를 넣는다.
	})
});//ready

function upload() {
	//alert("adsf")
	//let output="<input type='file' id='img' name='img' accept='image/gif, image/jpeg, image/png, image/jpg' onchange='readURL(this);'/>"
	//$("#view").html(output);
} //upload
</script>
</head>

<body>
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>
	
	<!-- container -->
	<div id="container">
	
	<!-- 왼쪽 바 -->
	<jsp:include page="/layout/side_left.do"/>
	
	<div id="right">
	<div class="right_wrap">
	<div id="nav">
	<ul class="nav nav-tabs">
  <li role="presentation" class="active"><a href="" id="myInfo" style="cursor: pointer;">정보변경</a></li>
  <li role="presentation"><a id="sell_buy" style="cursor: pointer;">내가 쓴 글</a></li>
  <li role="presentation"><a id="interest" style="cursor: pointer;">관심글</a></li>
  <li role="presentation"><a href="<%= commonUrl %>/user/login/change_pass_form.do">비밀번호 변경</a></li>
  <li role="presentation"><a href="<%= commonUrl %>/user/login/leave_form.do">회원탈퇴</a></li>
</ul>
</div>

	<div id="view"><br/>
	<form action="${ commonUrl }/user/mypage/proc/update_proc.do" id="frm" method="post" enctype="multipart/form-data">
		<div id="myImg">
			<div style="text-align: left;">이미지</div>
			<%-- <% if( uv.getImg() == null ) { %> --%>
			<c:choose>
			<c:when test="${ empty mVO.getImg() }">
			<img id="viewImg" src="<%= commonUrl %>/common/images/defaultImg.png" alt="image" width="100" height="100"><br/>
			</c:when>
			<%-- <% } else { %> --%>
			<c:otherwise>
			<img id="viewImg" src="<%= commonUrl %>/common/images/upload/${ mVO.getImg() }" alt="image"><br/>
			<!-- 사진변경 버튼 / 클릭시 팝업? -->
			</c:otherwise>
			</c:choose>
			<%-- <% } %> --%>
			<!-- <img id="imgThumb" src="https://static.nid.naver.com/images/web/user/default.png" width="100" height="100"> -->
			<!-- <br/><br/><br/><br/><br/><br/>
			 <label for="inputImage" class="btn_model"><b id="btnChangeProfile" class="btn2" onclick="clickcr(this,'prf.upimg','','',event);">사진등록</b></label> -->
			 <!-- <label onclick="upload()">사진등록</label> -->
			<input type="file" id="img" name="img" accept="image/gif, image/jpeg, image/png, image/jpg" onchange="readURL(this);"/><br/><br/>
			<!-- <input type="file" name="img" accept="image/*" id="img" onchange="readURL(this);"/> -->
			<%-- <c:if test="">
			<input type="hidden" name="img" value="${ uv.img }"/>
			</c:if> --%>
		</div>

	<div id="id1">
		<div style="text-align: left;">*닉네임</div>
		<input type="text" value="${ mVO.nickname }" name="nickname" id="nickname" class="form-control" style="width: 150px; height: 40px; font-size: 15px;"/>		
		<!-- <input type="button" value="중복확인" class="check_btn" id="chk_nickname_dup"/> -->
		<input type="hidden" name="user_id" value="${ sessionScope.user_id }"/> 
	</div>
	
	<div id="phone1">
		<div style="text-align: left;">*휴대폰 번호</div>
		<input type="text" name="phone" id="phone" class="form-control" Placeholder="핸드폰 번호" value="${ mVO.phone }" style="width: 300px; height: 40px; font-size: 15px;"/>		
	</div>
	
	<div>
		<p id="save">
			<button type="button" class="btn btn-primary btn-lg" id="addSave" style="width: 150px;">수정</button>
		</p>
	</div>
	
	</form>
	</div>
</div><!-- /<div class="right_wrap"> -->
	
	</div><!-- /<div id="right"> -->
	</div><!-- /<div id="container"> -->
<div style="clear:both;">
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</div>

<%-- <form name="hiddenFrm" id="hiddenFrm" method="post" action="proc/update_proc.jsp">
	<input type="hidden" name="user_id" value="${ sess_user_id }"/> 
	<input type="hidden" name="nickname" id="update_nickname"/> 
	<input type="hidden" name="phone" id="update_phone"/> 
	<input type="hidden" name="img" id="update_img"/> 
</form> --%>

</body>
</html>