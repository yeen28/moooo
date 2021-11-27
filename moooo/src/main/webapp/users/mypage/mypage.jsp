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
<c:redirect url="/users/login/login.jsp"/>
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
#view{
	/* border: 1px solid #000000;  */
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
.list {
	/* position: absolute;
	top: 30px;  */
	width : 135%;
	height: 600px;
	margin: 30px auto;
	padding: 20px;
	/* border: 1px solid #dfdfdf;
	border-radius: 15px; */
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
			url: "ajax_sub/myInfo.jsp",
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
			url: "ajax_sub/sell_buy_list.jsp",
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
			url: "ajax_sub/interest_list.jsp",
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
	$("#chk_nickname_dup").click(function() {
		window.open("dup_nickname.jsp", "dup", "width=500,height=400");
	});	//click
	
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
			}
			
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

             //clickcr(this,'prf.upimg','', '', event)
/* function clickcr(g,    H,           u, D, E,      A, P) {
	if (arguments.length == 1) {
		if (typeof nclk.generateCC != "undefined") {
			nclk.generateCC(arguments[0])
		}
		return
	}
	var F = navigator.userAgent.toLowerCase(); //소문자로
	var k = (F.indexOf("safari") != -1 ? true : false); //true-safari, false-safari아님
	var C = /msie/.test(F) && !/opera/.test(F);
	var l = (window.location.protocol == "https:") ? "https:" : "http:";
	var a = ccsrv.substring(ccsrv.indexOf(".") + 1);
	var t = window.event ? window.event : E;
	var s = -1;
	var q = -1;
	var p = -1;
	var n = -1;
	var S, f, i;
	var r, j, m;
	var M, J, I, L, z, B, w;
	var O;
	var G = 0;
	if (!A) {
		A = "0"
	} else {
		A = String(A)
	}
	if (!P) {
		P = ""
	}
	if (A.indexOf("n") == 0) {
		G = 0
	} else {
		if (window.g_ssc != undefined && window.g_query != undefined) {
			G = 1
		} else {
			G = 0
		}
	}
	try {
		L = nclk.windowSize(window);
		i = nclk.checkIframe(g);
		if (i) {
			var v = nclk.findPos(document.getElementById(i));
			if (t.clientX && t.clientX != undefined) {
				S = document.body;
				if (S.clientLeft && S.clientTop) {
					ifrSx = t.clientX - S.clientLeft;
					ifrSy = t.clientY - S.clientTop
				} else {
					ifrSx = t.clientX;
					ifrSy = t.clientY
				}
			}
			p = v[0] + ifrSx;
			n = v[1] + ifrSy;
			if (document.body
					&& (document.body.scrollTop || document.body.scrollLeft)) {
				S = document.body;
				s = p - S.scrollLeft;
				q = n - S.scrollTop
			} else {
				if (document.documentElement
						&& (document.documentElement.scrollTop || document.documentElement.scrollLeft)) {
					f = document.documentElement;
					s = p - f.scrollLeft;
					q = n - f.scrollTop
				} else {
					s = p;
					q = n
				}
			}
		} else {
			if (t.clientX && t.clientX != undefined) {
				S = document.body;
				if (S.clientLeft && S.clientTop) {
					s = t.clientX - S.clientLeft;
					q = t.clientY - S.clientTop
				} else {
					s = t.clientX;
					q = t.clientY
				}
			}
			if (document.body
					&& (document.body.scrollTop || document.body.scrollLeft)) {
				p = document.body.scrollLeft + (s < 0 ? 0 : s);
				n = document.body.scrollTop + (q < 0 ? 0 : q)
			} else {
				if (document.documentElement
						&& (document.documentElement.scrollTop || document.documentElement.scrollLeft)) {
					f = document.documentElement;
					if (f.scrollLeft != undefined) {
						p = f.scrollLeft + (s < 0 ? 0 : s)
					}
					if (f.scrollTop != undefined) {
						n = f.scrollTop + (q < 0 ? 0 : q)
					}
				} else {
					p = (s < 0 ? 0 : s);
					n = (q < 0 ? 0 : q)
				}
			}
			if (t.pageX) {
				p = t.pageX
			}
			if (t.pageY) {
				n = t.pageY
			}
		}
	} catch (Q) {
	}
	if (H == "" || typeof H == "undefined") { //H : 'prf.upimg' 파일클릭시 파라메터로 넘어옴
		return  //이미지가 없는 경우 그냥 리턴하는 것 같음
	}
	if (A.indexOf("1") != -1) {
		r = 0
	} else {
		if (g.href) {
			z = g.nodeName.toLowerCase(); //g : this(파일클릭시 파라메터로 넘어옴) //소문자로
			B = g.href.toLowerCase();
			if ((g.target && g.target != "_self" && g.target != "_top" && g.target != "_parent")
					|| (B.indexOf("javascript:") != -1)
					|| (g.getAttribute("href", 2) && g.getAttribute("href", 2)
							.charAt(0) == "#")
					|| (B.indexOf("#") != -1 && (B.substr(0, B.indexOf("#")) == document.URL))
					|| z.toLowerCase() == "img"
					|| C
					&& window.location.host.indexOf(a) == -1) {
				r = 0
			} else {
				r = 1
			}
		} else {
			r = 0
		}
	}
	if (g.href && g.href.indexOf(l + "//" + ccsrv) == 0) {
		j = g.href
	} else {
		j = l + "//" + ccsrv + "/" + nclkModule + "?a=" + H + "&r=" + D + "&i="
				+ u;
		j += "&bw=" + L + "&px=" + p + "&py=" + n + "&sx=" + s + "&sy=" + q
				+ "&m=" + r;
		if (G == 0) {
			j += "&nsc=" + nsc
		} else {
			if (G == 1) {
				j += "&ssc=" + g_ssc + "&q=" + encodeURIComponent(g_query)
						+ "&s=" + g_sid + "&p=" + g_pid
			}
		}
		if (P) {
			j += "&g=" + encodeURIComponent(P)
		}
		if (B && B.indexOf(l + "//" + ccsrv) != 0 && z.toLowerCase() != "img") {
			var N = g.href;
			if (g.outerHTML && !window.XMLHttpRequest) {
				N = (/\shref=\"([^\"]+)\"/i.test(g.outerHTML) && RegExp.$1)
						.replace(/\\/g, "\\\\").replace(/%([A-Z0-9]{2})/ig,
								"\\$1");
				(d = document.createElement("div")).innerHTML = N;
				N = d.innerText.replace(/\\([A-Z0-9]{2})/gi, "%$1").replace(
						/\\\\/g, "\\")
			}
			B = N.toLowerCase();
			if (B.indexOf("http:") == 0 || B.indexOf("https:") == 0
					|| B.indexOf("javascript:") == 0) {
				j += "&u=" + encodeURIComponent(N)
			} else {
				w = nclk.absPath(N);
				j += "&u=" + encodeURIComponent(w)
			}
		} else {
			if (g.href) {
				if (g.href.length > 0) {
					j += "&u=" + encodeURIComponent(g.href)
				} else {
					j += "&u=about%3Ablank"
				}
			} else {
				j += "&u=about%3Ablank"
			}
		}
	}
	if (r == 1) {
		O = g.innerHTML;
		g.href = j;
		if (g.innerHTML != O) {
			g.innerHTML = O
		}
	} else {
		if (document.images) {
			var K = new Date().getTime();
			j += "&time=" + K;
			if (k && !g.href) {
				var R = c = new Date();
				while ((R.getTime() - c.getTime()) < 100) {
					R = new Date()
				}
				var M = new Image();
				nclkImg.push(M);
				M.src = j
			} else {
				var M = new Image();
				nclkImg.push(M);
				M.src = j
			}
		}
	}
	return true
}; */

function upload() {
	alert("adsf")
	//let output="<input type='file' id='img' name='img' accept='image/gif, image/jpeg, image/png, image/jpg' onchange='readURL(this);'/>"
	//$("#view").html(output);
} //upload
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
	<ul class="nav nav-tabs">
  <li role="presentation" class="active"><a href="" id="myInfo" style="cursor: pointer;">정보변경</a></li>
  <li role="presentation"><a id="sell_buy" style="cursor: pointer;">내가 쓴 글</a></li>
  <li role="presentation"><a id="interest" style="cursor: pointer;">관심글</a></li>
  <li role="presentation"><a href="<%= commonUrl %>/users/login/change_password.jsp">비밀번호 변경</a></li>
  <li role="presentation"><a href="<%= commonUrl %>/users/login/leave.jsp">회원탈퇴</a></li>
</ul>
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
	
	<div id="view"><br/>
	<form action="../mypage/proc/updateImg_proc.jsp" id="frmImg" method="post" enctype="multipart/form-data">
		<div id="myImg">
			<div style="text-align: left;">이미지</div>
			<% if( uv.getImg() == null ) { %>
			<img id="viewImg" src="<%= commonUrl %>/common/images/defaultImg.png" alt="image" width="100" height="100"><br/>
			<% } else { %>
			<img id="viewImg" src="<%= commonUrl %>/common/images/upload/${uv.img}" alt="image"><br/>
			<!-- 사진변경 버튼 / 클릭시 팝업? -->
			<% } %>
			<!-- <img id="imgThumb" src="https://static.nid.naver.com/images/web/user/default.png" width="100" height="100"> -->
			<!-- <br/><br/><br/><br/><br/><br/>
			 <label for="inputImage" class="btn_model"><b id="btnChangeProfile" class="btn2" onclick="clickcr(this,'prf.upimg','','',event);">사진등록</b></label> -->
			 <label onclick="upload()">사진등록</label>
			<input type="file" id="img" name="img" accept="image/gif, image/jpeg, image/png, image/jpg" onchange="readURL(this);" onclick="upload()"/><br/><br/>
			<!-- <input type="file" name="img" accept="image/*" id="img" onchange="readURL(this);"/> -->
			<%-- <c:if test="">
			<input type="hidden" name="img" value="${ uv.img }"/>
			</c:if> --%>
		</div>
		</form>

	<form action="../mypage/proc/update_proc.jsp" id="frm" method="post">
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
<jsp:include page="/layout/footer.jsp"/>
</div>

<%-- <form name="hiddenFrm" id="hiddenFrm" method="post" action="proc/update_proc.jsp">
	<input type="hidden" name="user_id" value="${ sess_user_id }"/> 
	<input type="hidden" name="nickname" id="update_nickname"/> 
	<input type="hidden" name="phone" id="update_phone"/> 
	<input type="hidden" name="img" id="update_img"/> 
</form> --%>

</body>
</html>