<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    info="회원가입"
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="/common/jsp/common_code.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title><%= title %></title>

<!--jQuery CDN-->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.2.4/jquery.min.js"></script>
<!-- Bootstrap CDN -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>

<link rel="stylesheet" href="<%= commonUrl %>/common/css/sign_up.css"/>

<script type="text/javascript">
$(function() {
	$("#check_duplicate").click(function() {
		window.open("id_dup.do", "dup", "width=500,height=400");
	});
	
	$("#chk_nickname_dup").click(function() {
		window.open("dup_nickname.do", "dup", "width=500,height=400");
	});
	
	$("#btn-sign-up").click(function() {
		let pw=$("#pass").val();
		/* let email=$("#user_email").val(); */
		
		if( $("#user_id").val() == "" ){
			alert("아이디 중복확인을 해주세요.");
			return;
		}//end if
		
		if( $("#nickname").val() == "" ){
			alert("닉네임 중복확인을 해주세요.");
			return;
		}//end if
		
		if($("#phone").val() == ""){
			alert("휴대폰번호를 입력해주세요."); //나중에는 pass인증으로 바꾸기
			$("#phone").focus();
			return;
		}
		
		/* if( email == "" ){
			alert("이메일을 입력해주세요.");
			frm.email.focus();
			return;
		}//end if 
		
		if( email.indexOf("@") == -1 || email.indexOf(".") == -1 ){
			alert("이메일형식이 아닙니다.");
			frm.email.focus();
			return;
		}//end if */
		
		if( pw == "" ){
			alert("비밀번호를 입력해주세요.");
			$("#pass").focus();
			return;
		}//end if
		
		if( pw.search(/[0-9]/g) < 0 || pw.search(/[a-z]/g) < 0 || pw.length < 6 ) {
			alert("비밀번호는 소문자와 숫자 포함 6자 이상입니다.");
			$("#pass").focus();
			return;
		}
		
		if( $("#pass_confirmation").val() == "" ){
			alert("비밀번호 확인을 입력해주세요.");
			$("#pass_confirmation").focus();
			return;
		}//end if
		
		if( $("#pass").val() != $("#pass_confirmation").val()){
			alert("비밀번호가 일치하지 않습니다.");
			$("#pass_confirmation").val("");
			$("#pass_confirmation").focus();
			return;
		}//end if
		
		if( !$("#tos_agreed").is(":checked") ){
			alert("이용약관에 동의해주세요."); 
			return;
		}//end if
		
		$("#new_user").submit();
	}); //click
}); //ready
</script>
</head>

<body>
<div id="wrap">
<!-- header -->
<jsp:include page="/WEB-INF/views/layout/header.jsp"/>

<div id="container">
      <h2 class="sign-title"><img src="<%= commonUrl %>/common/images/icons/sign_up.PNG" width="50px"/>&nbsp;<strong>회원가입</strong></h2>
        <form action="<%= commonUrl %>/user/login/sign_up_proc.do" role="form" class="new_user" id="new_user" name="new_user" accept-charset="UTF-8" method="post">
        <div>
        </div>
        
        <!-- <input type="hidden" name="authenticity_token" value="" />-->
      <div class="top">
        <div class="input-area">
          <label class="control-label">아이디</label>
          <div class="form-group">
            <input required="required" placeholder="아이디" class="form-control" type="text" name="user_id" id="user_id" readonly="readonly"/>
            <input type="button" value="중복확인" class="check_btn" id="check_duplicate"/>
          </div><br/>
          <label for="user_name">닉네임</label>
          <div class="form-group">
            <input required="required" placeholder="닉네임" class="form-control" type="text" name="nickname" id="nickname"/>
            <!-- <input type="button" value="중복확인" class="check_btn" id="chk_nickname_dup"/> -->
          </div><br/>
          <label for="user_phone">휴대폰번호</label>
          <div class="form-group">
            <input required="required" placeholder="휴대폰번호" class="form-control" type="text" name="phone" id="phone" />
          </div>
          <label for="user_password">비밀번호 <span>(소문자와 숫자 포함 최소 6자)</span></label>
          <div class="form-group"><input required="required" placeholder="비밀번호" class="form-control" type="password" name="pass" id="pass" /></div>
          <label for="user_password_confirmation">비밀번호 확인</label>
          <div class="form-group"><input required="required" placeholder="비밀번호 확인" class="form-control" type="password" name="pass_confirmation" id="pass_confirmation" /></div>
        </div>
      </div>
      
      <div id="agree">
          <h6>
            <input type="checkbox" id="tos_agreed" name="tos_agreed" value="tos_agreed">
            <a target="_blank" rel="noopener" href="<%= commonUrl %>/common/jsp/tos.jsp" style="color:#0000FF;">이용약관</a>에 동의합니다. (필수)
          </h6>
      </div>
      
      <div>
      <button type="button" id="btn-sign-up" class="btn-color btn btn-block btn-lg">가입하기</button>
      </div>
      </form> 
  </div>

</div>

<!-- footer -->
<jsp:include page="/WEB-INF/views/layout/footer.jsp"/>
</body>
</html>