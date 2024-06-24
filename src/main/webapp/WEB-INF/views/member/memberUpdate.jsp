<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>memberUpdate.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
  <script src="${ctp}/js/woo.js"></script>
  <script>
    'use strict';
    
    let nickCheckSw = 0;
    
    $(function(){
    	document.getElementById("demoImg").src = "${ctp}/member/${vo.photo}";
    })
    
    function fCheck() {
    	// 필수항목 입력여부 확인 
    	let mid = document.getElementById("mid").value.trim();
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
    	let regName = /^[a-zA-Z가-힣]{2,10}$/; 
    	let regEmail = /^[a-zA-Z0-9]([-_]?[a-zA-Z0-9])*$/i;
    	let regHomePage = /(https?:\/\/)?([a-zA-Z\d-]+)\.([a-zA-Z\d-]{2,8})([\/\w\.-]*)*\/?$/;
    	let regTel = /\d{2,3}-\d{3,4}-\d{4}$/;
    	
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
    	
    	if(nickCheckSw == 0) {
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
    
    // 닉네임 중복체크
		function nickCheck() {
    	let nickName = document.getElementById("nickName").value.trim();
    	let mid = document.getElementById("mid").value.trim();
    	let regNickName = /^[a-zA-Z0-9가-힣]{2,10}$/;
    	if(nickName.trim() == "") {
    		alert("닉네임을 입력하세요.");
    		myform.nickName.focus();
    	}
    	else if(nickName == "${sNickName}") {
    		nickCheckSw = 1;
    		$("#nickNameBtn").attr("disabled",true);
    		return false;
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
    			data: {nickName:nickName},
    			success: function(res) {
    				/* 
        		if('${sNickName}' == nickName){
        			nickCheckSw = 1;
        			alert("사용 가능한 닉네임 입니다.");
        			$("#nickNameBtn").attr("disabled",true);
        			return;
        		}
        		 */
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
  <form name="myform" method="post" class="was-validated" enctype="multipart/form-data">
    <h2>회 원 가 입</h2>
    <br/>
    <div class="form-group">
      <label for="mid">아이디 : </label>
      <input type="text" class="form-control" name="mid" id="mid" value="${sMid}" readonly/>
    </div>
    <div class="form-group">
      <label for="nickName">닉네임 : &nbsp; &nbsp;<input type="button" id="nickNameBtn" value="닉네임 중복체크" class="btn btn-secondary btn-sm" onclick="nickCheck()"/></label>
      <input type="text" class="form-control" id="nickName" name="nickName" value="${vo.nickName}" required />
    </div>
    <div class="form-group">
      <label for="name">성명 :</label>
      <input type="text" class="form-control" id="name"  value="${vo.name}" name="name" required />
    </div>
    <div class="form-group">
      <label for="email1">Email address:</label>
        <div class="input-group mb-3">
        	<c:set var="email" value="${fn:split(vo.email,'@')}"/>
          <input type="text" class="form-control" id="email1" name="email1" value="${email[0]}" required />
          <div class="input-group-append">
            <select name="email2" class="custom-select">
              <option value="naver.com" ${email[1]=='naver.com' ? 'selected' : ''}>naver.com</option>
              <option value="hanmail.net" ${email[1]=='hanmail.net' ? 'selected' : ''}>hanmail.net</option>
              <option value="hotmail.com" ${email[1]=='hotmail.com' ? 'selected' : ''}>hotmail.com</option>
              <option value="gmail.com" ${email[1]=='gmail.com' ? 'selected' : ''}>gmail.com</option>
              <option value="nate.com" ${email[1]=='nate.com' ? 'selected' : ''}>nate.com</option>
              <option value="yahoo.com" ${email[1]=='yahoo.com' ? 'selected' : ''}>yahoo.com</option>
            </select>
          </div>
        </div>
    </div>
    <div class="form-group">
      <div class="form-check-inline">
        <span class="input-group-text">성별 :</span> &nbsp; &nbsp;
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="남자" <c:if test="${vo.gender=='남자'}">checked</c:if> >남자
        </label>
      </div>
      <div class="form-check-inline">
        <label class="form-check-label">
          <input type="radio" class="form-check-input" name="gender" value="여자" <c:if test="${vo.gender=='여자'}">checked</c:if> >여자
        </label>
      </div>
    </div>
    <div class="form-group">
      <label for="birthday">생일</label>
      <input type="date" name="birthday" value="${fn:substring(vo.birthday,0,10)}" class="form-control"/>
    </div>
    <div class="form-group">
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">전화번호 :</span> &nbsp;&nbsp;
            <select name="tel1" class="custom-select">
              <option value="010" ${tel1=='010' ? 'selected' : ''}>010</option>
              <option value="02" ${tel1=='02' ? 'selected' : ''}>서울</option>
              <option value="031" ${tel1=='031' ? 'selected' : ''}>경기</option>
              <option value="032" ${tel1=='032' ? 'selected' : ''}>인천</option>
              <option value="041" ${tel1=='041' ? 'selected' : ''}>충남</option>
              <option value="042" ${tel1=='042' ? 'selected' : ''}>대전</option>
              <option value="043" ${tel1=='043' ? 'selected' : ''}>충북</option>
              <option value="051" ${tel1=='051' ? 'selected' : ''}>부산</option>
              <option value="052" ${tel1=='052' ? 'selected' : ''}>울산</option>
              <option value="061" ${tel1=='061' ? 'selected' : ''}>전북</option>
              <option value="062" ${tel1=='062' ? 'selected' : ''}>광주</option>
            </select>-
        </div>
        <input type="text" name="tel2" value="${tel2}" size=4 maxlength=4 class="form-control"/>-
        <input type="text" name="tel3" value="${tel3}" size=4 maxlength=4 class="form-control"/>
      </div>
    </div>
    <div class="form-group">
      <label for="address">주소</label>
      <div class="input-group mb-1">
        <input type="text" name="postcode" id="sample6_postcode" value="${postcode}" class="form-control">
        <div class="input-group-append">
          <input type="button" onclick="sample6_execDaumPostcode()" value="우편번호 찾기" class="btn btn-secondary">
        </div>
      </div>
      <input type="text" name="roadAddress" id="sample6_address" size="50" value="${roadAddress}" class="form-control mb-1">
      <div class="input-group mb-1">
        <input type="text" name="detailAddress" id="sample6_detailAddress" value="${detailAddress}" class="form-control"> &nbsp;&nbsp;
        <div class="input-group-append">
          <input type="text" name="extraAddress" id="sample6_extraAddress" value="${extraAddress}" class="form-control">
        </div>
      </div>
    </div>
    <div class="form-group">
      <label for="homePage">Homepage address:</label>
      <input type="text" class="form-control" name="homePage" value="${vo.homePage}" id="homePage"/>
    </div>
    <div class="form-group">
      <label for="name">직업</label>
      <select class="form-control" id="job" name="job">
        <!-- <option value="">직업선택</option> -->
        <option ${vo.job == '학생' ? 'selected' : ''}>학생</option>
        <option ${vo.job == '회사원' ? 'selected' : ''}>회사원</option>
        <option ${vo.job == '공무원' ? 'selected' : ''}>공무원</option>
        <option ${vo.job == '군인' ? 'selected' : ''}>군인</option>
        <option ${vo.job == '의사' ? 'selected' : ''}>의사</option>
        <option ${vo.job == '법조인' ? 'selected' : ''}>법조인</option>
        <option ${vo.job == '세무인' ? 'selected' : ''}>세무인</option>
        <option ${vo.job == '자영업' ? 'selected' : ''}>자영업</option>
        <option ${vo.job == '기타' ? 'selected' : ''}>기타</option>
      </select>
    </div>
    <div class="form-group">
    	취미 : &nbsp;
    	<c:set var="varHobbys" value="${fn:split(('등산/낚시/수영/독서/영화감상/바둑/축구/기타'),'/')}"/>
    	<c:forEach var="tempHobby" items="${varHobbys}" varStatus="st">
    		<%-- <input type="checkbox" name="hobby" value="${tempHobby}" <c:if test="${fn:contains(hobby,varHobbys[st.index])}">checked</c:if> /> ${tempHobby}&nbsp; --%>
    		<input type="checkbox" name="hobby" value="${tempHobby}" <c:if test="${fn:contains(vo.hobby,tempHobby)}">checked</c:if> /> ${tempHobby}&nbsp;
    	</c:forEach>
    </div>
    <div class="form-group">
      <label for="content">자기소개</label>
      <textarea rows="5" class="form-control" id="content" name="content">${vo.content}</textarea>
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
      회원 사진(파일용량:2MByte이내) : <img id="demoImg" width="100px">
      <input type="file" name="fName" id="file" onchange="imgCheck(this)" class="form-control-file border"/>
    </div>
    <button type="button" class="btn btn-secondary" onclick="fCheck()">수정하기</button> &nbsp;
    <button type="reset" class="btn btn-secondary">다시작성</button> &nbsp;
    <button type="button" class="btn btn-secondary" onclick="location.href='MemberMain.mem';">돌아가기</button>
    
    <input type="hidden" name="email" />
    <input type="hidden" name="tel" />
    <input type="hidden" name="address" />
    <input type="hidden" name="mid" value="${sMid}" />
    <input type="hidden" name="photo" value="${vo.photo}" />
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>