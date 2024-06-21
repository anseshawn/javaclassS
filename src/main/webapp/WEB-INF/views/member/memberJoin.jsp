<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberJoin.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
    'use strict';
    
    let idCheckSw = 0;
    let nickCheckSw = 0;
    
    function fCheck() {
    	// 필수항목 입력여부 확인 
    	let mid = document.getElementById("mid").value.trim();
    	let pwd = document.getElementById("pwd").value.trim();
    	let nickName = document.getElementById("nickName").value.trim();
    	let name = document.getElementById("name").value.trim();
    	let gender = myform.gender.value;
    	//let birthday = myform.birthday.value;
    	let homePage = document.getElementById("homePage").value.trim();
    	//let job = myform.job.value;
    	let email1 = myform.email1.value.trim();
    	
    	
    	if(mid == "") {
    		alert("아이디를 입력하세요");
    		myform.mid.focus();
    		return false;
    	}
    	else if(pwd == "") {
    		alert("비밀번호를 입력하세요");
    		myform.pwd.focus();
    		return false;
    	}
    	else if(nickName == "") {
    		alert("닉네임을 입력하세요");
    		myform.nickName.focus();
    		return false;
    	}
    	else if(name == "") {
    		alert("이름을 입력하세요");
    		myform.name.focus();
    		return false;
    	}
    	else if(email1 == "") {
    		alert("이메일을 입력하세요");
    		myform.email1.focus();
    		return false;
    	}
    	
    	// 1.정규식을 이용한 유효성 검사처리
    	// 아이디와 닉네임은 중복체크 검사시에 수행...
    	let regPwd = /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\W_]).{4,20}$/; 
    	let regName = /^[a-zA-Z가-힣]{2,10}$/; 
    	let regEmail = /^[a-zA-Z0-9]([-_]?[a-zA-Z0-9])*$/i;
    	let regHomePage = /(https?:\/\/)?([a-zA-Z\d-]+)\.([a-zA-Z\d-]{2,8})([\/\w\.-]*)*\/?$/;
    	let regTel = /\d{2,3}-\d{3,4}-\d{4}$/;
			/* 
    	if(!regPwd.test(pwd)) {
    		alert("비밀번호는 영문 대/소문자와 숫자, 특수문자를 포함하여 4~20자까지 가능합니다. 특수문자를 꼭 1개 이상 포함해주세요.");
    		document.getElementById("pwd").focus();
    		return false;
    	}
    	*/
    	if(!regName.test(name)) {
    		alert("이름은 영문과 한글만 사용하여 2~10자까지 가능합니다.");
    		document.getElementById("name").focus();
    		return false;
    	}
    	if(!regEmail.test(email1)) {
    		alert("이메일 형식에 맞도록 작성해주세요.");
    		myform.email1.focus();
    		return false;
    	}    	
    	if(homePage.length > 7){
	    	if(!regHomePage.test(homePage)) {
	    		alert("홈페이지 주소 형식에 맞도록 작성해주세요.");
	    		document.getElementById("homePage").focus();
	    		return false;
	    	}
    	}
    	
    	// 2.검사 후 필요한 내용들을 변수에 담아 회원가입 처리한다.
    	
    	let email2 = myform.email2.value;
			let email = email1+"@"+email2;
			
			let tel1 = myform.tel1.value;
			let tel2 = myform.tel2.value.trim();
			let tel3 = myform.tel3.value.trim();
    	let tel = tel1+"-"+tel2+"-"+tel3;
    	if(tel2 != "" || tel3 != ""){
	    	if(!regTel.test(tel)) {
	    		alert("전화번호 형식(000-0000-0000)에 맞도록 작성해주세요.");
	    		myform.tel2.focus();
	    		return false;
	    	}
    	}
    	else {
    		tel2 = " ";
    		tel3 = " ";
    		tel = tel1+"-"+tel2+"-"+tel3;
    	}
    	
    	let postcode = myform.postcode.value + " ";
    	let roadAddress = myform.roadAddress.value + " ";
    	let detailAddress = myform.detailAddress.value + " ";
    	let extraAddress = myform.extraAddress.value + " ";
    	let address = postcode+"/"+roadAddress+"/"+detailAddress+"/"+extraAddress;
    	
    	/*
    	// 이미지 등록 시키기(파일에 관련된 사항들 체크)
    	let fName = document.getElementById("file").value;
    	if(fName.trim() != null && fName.trim() != "") {
	    	let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
	    	let maxSize = 1024 * 1024 * 2;
	    	let fileSize = document.getElementById("file").files[0].size;
    		
	    	if(ext != 'jpg' && ext != 'png' && ext != 'gif' && ext != 'jpeg') {
	    		alert("사진 파일(jpg/png/gif/jpeg)만 등록 가능합니다.");
	    		return false;
	    	}
	    	else if(fileSize > maxSize) {
	    		alert("회원 사진의 최대 용량은 2MByte 입니다.");
	    		return false;
	    	}
    	}
    	*/
    	
    	// 회원 가입 전 체크
    	if(idCheckSw == 0){
    		alert("아이디 중복 체크를 수행해주세요.");
    		document.getElementById("midBtn").focus();
    	}
    	else if(nickCheckSw == 0) {
    		alert("닉네임 중복 체크를 수행해주세요.");
    		document.getElementById("nickNameBtn").focus();
    	}
    	else {
    		myform.email.value = email; // email 결합
    		myform.tel.value = tel;
    		myform.address.value = address;
    		
    		myform.submit();
    	}
    }
    
    // 아이디 중복체크
    function idCheck() {
    	let regMid = /^[a-zA-Z0-9_]{4,20}$/;
    	let mid = myform.mid.value;
    	if(mid.trim() == "") {
    		alert("아이디를 입력하세요.");
    		myform.mid.focus();
    	}
    	else if(!regMid.test(mid)){
    		alert("아이디는 영문 대/소문자와 숫자, 밑줄을 포함하여 4~20자까지 가능합니다.");
    		document.getElementById("mid").focus();
    	}
    	else {
    		idCheckSw = 1;
    		$.ajax({
    			url: "${ctp}/member/memberIdCheck",
    			type: "get",
    			data: {mid:mid},
    			success: function(res) {
    				if(res != 0) {
    					alert("이미 사용중인 아이디 입니다. 다시 입력하세요.");
    					idCheckSw = 0;
    					myform.mid.focus();
    				}
    				else {
    					alert("사용 가능한 아이디 입니다.");
    					$("#midBtn").attr("disabled",true);
    				}
    			},
    			error : function() {
    				alert("전송 오류");
    			}
    		});
    	}
    }
    
    // 닉네임 중복체크
    function nickCheck() {
    	let nickName = document.getElementById("nickName").value.trim();
    	let regNickName = /^[a-zA-Z0-9가-힣]{2,10}$/;
    	if(nickName.trim() == "") {
    		alert("닉네임을 입력하세요.");
    		myform.nickName.focus();
    	}
    	else if(!regNickName.test(nickName)){
    		alert("닉네임은 영문과 한글, 숫자만 사용하여 2~10자까지 가능합니다.");
    		document.getElementById("nickName").focus();
    	}
    	else {
    		nickCheckSw = 1;
    		$.ajax({
    			url: "${ctp}/member/memberNickCheck",
    			type: "get",
    			data: {nickName:nickName, mid:""},
    			success: function(res) {
    				if(res != 0) {
    					alert("이미 사용중인 닉네임 입니다. 다시 입력하세요.");
    					nickCheckSw = 0;
    					myform.nickName.focus();
    				}
    				else {
    					alert("사용 가능한 닉네임 입니다.");
    					$("#nickNameBtn").attr("disabled",true);
    				}
    			},
    			error : function() {
    				alert("전송 오류");
    			}
    		});
    	}
    }
    
    // 입력창 누르면 스위치 리셋...?
    window.onload = function(){
    	mid.addEventListener('click',function(){
    		idCheckSw = 0;
    		$("#midBtn").removeAttr("disabled");
    	});
    	nickName.addEventListener('click',function(){
    		nickCheckSw = 0;
    		$("#nickNameBtn").removeAttr("disabled");
    	});
    }
    
    // 회원 사진 선택 시 이미지 미리보기
    function imgCheck(e) {
    	if(e.files && e.files[0]) {
    		let reader = new FileReader();
    		reader.onload = function(e) {
    			document.getElementById("demoImg").src = e.target.result;
    		}
    		reader.readAsDataURL(e.files[0]);
    	}
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <!-- <form name="myform" method="post" class="was-validated" enctype="multipart/form-data"> -->
  <form name="myform" method="post" class="was-validated">
    <h2>회 원 가 입</h2>
    <br/>
    <div class="form-group">
      <label for="mid">아이디 : &nbsp; &nbsp;<input type="button" value="아이디 중복체크" id="midBtn" class="btn btn-secondary btn-sm" onclick="idCheck()"/></label>
      <input type="text" class="form-control" name="mid" id="mid" placeholder="아이디를 입력하세요." required autofocus/>
    </div>
    <div class="form-group">
      <label for="pwd">비밀번호 :</label>
      <input type="password" class="form-control" id="pwd" placeholder="비밀번호를 입력하세요." name="pwd" required />
    </div>
    <div class="form-group">
      <label for="nickName">닉네임 : &nbsp; &nbsp;<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/></label>
      <input type="text" class="form-control" id="nickName" placeholder="별명을 입력하세요." name="nickName" required />
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name" placeholder="성명을 입력하세요." name="name" required />
    </div>
    <div class="form-group">
      <label for="email1">Email address:</label>
        <div class="input-group mb-3">
          <input type="text" class="form-control" placeholder="Email을 입력하세요." id="email1" name="email1" required />
          <div class="input-group-append">
            <select name="email2" class="custom-select">
              <option value="naver.com" selected>naver.com</option>
              <option value="hanmail.net">hanmail.net</option>
              <option value="hotmail.com">hotmail.com</option>
              <option value="gmail.com">gmail.com</option>
              <option value="nate.com">nate.com</option>
              <option value="yahoo.com">yahoo.com</option>
            </select>
          </div>
        </div>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="남자" checked>남자
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="여자">여자
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
      <input type="date" name="birthday" value="<%=java.time.LocalDate.now() %>" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
            <select name="tel1" class="custom-select">
              <option value="010" selected>010</option>
              <option value="02">서울</option>
              <option value="031">경기</option>
              <option value="032">인천</option>
              <option value="041">충남</option>
              <option value="042">대전</option>
              <option value="043">충북</option>
              <option value="051">부산</option>
              <option value="052">울산</option>
              <option value="061">전북</option>
              <option value="062">광주</option>
            </select>-
        </div>
        <input type="text" name="tel2" size=4 maxlength=4 class="form-control"/>-
        <input type="text" name="tel3" size=4 maxlength=4 class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label for="address">주소</label>
      <div class="input-group mb-1">
        <input type="text" name="postcode" id="sample6_postcode" placeholder="우편번호" class="form-control">
        <div class="input-group-append">
          <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">
        </div>
      </div>
      <input type="text" name="roadAddress" id="sample6_address" size="50" placeholder="주소" class="form-control mb-1">
      <div class="input-group mb-1">
        <input type="text" name="detailAddress" id="sample6_detailAddress" placeholder="상세주소" class="form-control"> &nbsp;&nbsp;
        <div class="input-group-append">
          <input type="text" name="extraAddress" id="sample6_extraAddress" placeholder="참고항목" class="form-control">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="homePage">Homepage address:</label>
      <input type="text" class="form-control" name="homePage" value="http://" placeholder="홈페이지를 입력하세요." id="homePage"/>
    </div>
    <div class="form-group">
      <label for="name">직업</label>
      <select class="form-control" id="job" name="job">
        <!-- <option value="">직업선택</option> -->
        <option>학생</option>
        <option>회사원</option>
        <option>공무원</option>
        <option>군인</option>
        <option>의사</option>
        <option>법조인</option>
        <option>세무인</option>
        <option>자영업</option>
        <option selected>기타</option>
      </select>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">취미</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="등산" name="hobby"/>등산
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="낚시" name="hobby"/>낚시
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="수영" name="hobby"/>수영
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="독서" name="hobby"/>독서
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="영화감상" name="hobby"/>영화감상
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="바둑" name="hobby"/>바둑
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="축구" name="hobby"/>축구
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="checkbox" class="form-check-input" value="기타" name="hobby" checked/>기타
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content" placeholder="자기소개를 입력하세요."></textarea>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">정보공개</span>  &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" value="공개" checked/>공개
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="userInfor" value="비공개"/>비공개
        </label>
      </div>
    </div>
    <div  class="form-group">
      회원 사진(파일용량:2MByte이내) :
      <input type="file" name="fName" id="file" onchange="imgCheck(this)" class="form-control-file border"/>
    </div>
    <div><img id="demoImg" width="100px"/></div>
    <hr/>
    <button type="button" class="btn btn-secondary" onclick="fCheck()">회원가입</button> &nbsp;
    <button type="reset" class="btn btn-secondary">다시작성</button> &nbsp;
    <button type="button" class="btn btn-secondary" onclick="location.href='${ctp}/member/memberLogin';">돌아가기</button>
    
    <input type="hidden" name="email" />
    <input type="hidden" name="tel" />
    <input type="hidden" name="address" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>