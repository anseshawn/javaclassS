<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<%
/*
	// 로그인창에 아이디 체크 유무에 대한 처리
	// 쿠키를 검색해서 cMid가 있을때 가져와서 아이디입력창에 뿌릴수 있게 한다.
	Cookie[] cookies = request.getCookies();

	if(cookies != null) { // 저장된 쿠키가 있으면 작동하도록 if문 먼저
		for(int i=0; i<cookies.length; i++) {
			if(cookies[i].getName().equals("cMid")) {
				pageContext.setAttribute("mid", cookies[i].getValue());
				break;
			}
		}
	}
*/
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>memberLogin.jsp</title>
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
  <script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
  <style>
		#spinner {
		  position: absolute;
		  left: 50%;
		  top: 50%;
		  z-index: 1;
		}
		
		.kakao {
			height: 90%;
		}
	</style>
  <script>
  	'use strict';
  	
  	$(function(){
  		$("#searchPassword").hide();
  		$("#searchMid").hide();
  	});
  	
  	// 아이디 찾기
  	function midSearch() {
  		$("#searchMid").show();
  		$("#searchPassword").hide();
  	}
  	
  	// 비밀번호 찾기
  	function pwdSearch() {
  		$("#searchPassword").show();
  		$("#searchMid").hide();
  	}
  	
  	// 임시 비밀번호 등록시켜주기
  	function newPassword(){
  		let mid = $("#midSearch").val().trim();
  		let email = $("#emailSearch2").val().trim();
  		if(mid=="" || email=="") {
  			alert("가입시 등록한 아이디와 메일 주소를 입력하세요.");
  			$("#midSearch").focus();
  			return false;
  		}
  		document.getElementById("spinner").style.display='block';
  		//document.getElementsByClassName("container")[0].style.display='none';
  		$.ajax({
  			url: "${ctp}/member/memberNewPassword",
  			type: "post",
  			data: {
  				mid : mid,
  				email : email
  			},
  			success: function(res){
  				document.getElementById("spinner").style.display='none';
		  		//document.getElementsByClassName("container")[0].style.display='block';
  				if(res != "0") alert("새로운 비밀번호가 메일로 발송 되었습니다.\n받은 메일함을 확인하세요.");
  				else alert("등록하신 정보가 일치하지 않습니다.\n확인 후 다시 처리하세요.");
  			},
  			error: function(){
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 아이디 발송하기
  	function searchMidBtn(){
  		let name = $("#nameSearch").val().trim();
  		let email = $("#emailSearch1").val().trim();
  		if(name=="" || email=="") {
  			alert("가입시 등록한 이름과 메일 주소를 입력하세요.");
  			$("#nameSearch").focus();
  			return false;
  		}
  		document.getElementById("spinner").style.display='block';
  		$.ajax({
  			url: "${ctp}/member/memberMidSearch",
  			type: "post",
  			data: {
  				name : name,
  				email : email
  			},
  			success: function(res){
  				document.getElementById("spinner").style.display='none';
  				if(res != "0") alert("회원님의 아이디가 메일로 발송 되었습니다.\n받은 메일함을 확인하세요.");
  				else alert("등록하신 정보가 일치하지 않습니다.\n확인 후 다시 처리하세요.");
					location.reload();
  			},
  			error: function(){
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 카카오 로그인(자바스크립트 앱키 등록)
  	
  	window.Kakao.init("72ddec57bb46287e893efd27beb4a21e");
  	
  	function kakaoLogin() {
  		// 항상 명령은 window.Kakao. 이렇게 들어가야 카카오에 들어가서 불러옴
  		window.Kakao.Auth.login({
  			scope: 'profile_nickname, account_email', //변수의 생명주기
  			success: function(autoObj) {
  				console.log(Kakao.Auth.getAccessToken(), "정상 토큰 발급됨...")
  				window.Kakao.API.request({
  					url: '/v2/user/me',
  					success: function(res) {
  						const kakao_account = res.kakao_account;
  						console.log(kakao_account);
  						location.href = "${ctp}/member/kakaoLogin?nickName="+kakao_account.profile.nickname+"&email="+kakao_account.email+"&accessToken="+Kakao.Auth.getAccessToken();
  					}
  				});
  			}
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="spinner-border text-primary" id="spinner" style="display:none;"></div>
<div class="container">
  <form name="myform" method="post">
    <table class="table table-bordered text-center">
      <tr>
        <td colspan="2"><font size="5">로 그 인</font></td>
      </tr>
      <tr>
        <th>아이디</th>
        <td><input type="text" name="mid" value="${mid}" autofocus required class="form-control"/></td>
      </tr>
      <tr>
        <th>비밀번호</th>
        <td><input type="password" name="pwd" value="1234" required class="form-control"/></td>
      </tr>
      <tr>
        <td colspan="2">
          <input type="submit" value="로그인" class="btn btn-success mr-2"/>
          <input type="reset" value="다시입력" class="btn btn-warning mr-2"/>
          <input type="button" value="회원가입" onclick="location.href='${ctp}/member/memberJoin';" class="btn btn-primary mr-4"/>
          <a href="javascript:kakaoLogin()"><img src="${ctp}/images/kakao_login_medium_narrow.png" class="kakao" width="150px"/></a>
        </td>
      </tr>
    </table>
    <table class="table p-0">
    	<tr>
    		<td class="text-center">
	    		<input type="checkbox" name="idSave" checked /> 아이디 저장 &nbsp; │ &nbsp;
	    		[<a href="javascript:midSearch()">아이디 찾기</a>] /
	    		[<a href="javascript:pwdSearch()">비밀번호 찾기</a>]
    		</td>
    	</tr>
    </table>
  </form>
  <div id="searchMid">
  	<hr/>
  	<table class="table table-bordered p-0 text-center">
  		<tr>
  			<td colspan="2">
  				<font size="4"><b>아이디 찾기</b></font>
  				(가입시 입력한 이름과 메일 주소를 입력하세요.)
  			</td>
  		</tr>
  		<tr>
  			<th>이름</th>
  			<td><input type="text" name="nameSearch" id="nameSearch" class="form-control" placeholder="이름을 입력하세요" /></td>
  		</tr>
  		<tr>
  			<th>이메일</th>
  			<td><input type="text" name="emailSearch1" id="emailSearch1" class="form-control" placeholder="이메일을 입력하세요" /></td>
  		</tr>
  		<tr>
  			<td colspan="2"><input type="button" value="아이디 찾기" onclick="searchMidBtn()" class="btn btn-primary form-control" /></td>
  		</tr>
  	</table>
  </div>
  <div id="searchPassword">
  	<hr/>
  	<table class="table table-bordered p-0 text-center">
  		<tr>
  			<td colspan="2">
  				<font size="4"><b>비밀번호 찾기</b></font>
  				(가입시 입력한 아이디와 메일 주소를 입력하세요.)
  			</td>
  		</tr>
  		<tr>
  			<th>아이디</th>
  			<td><input type="text" name="midSearch" id="midSearch" class="form-control" placeholder="아이디를 입력하세요" /></td>
  		</tr>
  		<tr>
  			<th>이메일</th>
  			<td><input type="text" name="emailSearch2" id="emailSearch2" class="form-control" placeholder="이메일을 입력하세요" /></td>
  		</tr>
  		<tr>
  			<td colspan="2"><input type="button" value="새 비밀번호 발급" onclick="newPassword()" class="btn btn-secondary form-control" /></td>
  		</tr>
  		
  		<!-- 2분안에 작성 안 하면 메일 초기화 기능(세션 혹은 메일 보낸 순간부터 타이머 설정해서) -->
  		
  	</table>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>