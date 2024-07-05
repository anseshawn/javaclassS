<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>csvForm.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <script>
    'use strict';
    
    function fCheck() {
    	let spin = "<div class='text-center'><div class='spinner-border text-muted'></div> 작업중입니다. 잠시만 기다려주세요 <div class='spinner-border text-muted'></div></div>";
      $("#demoSpin").html(spin);
      
    	let formData = new FormData();
    	formData.append("fName", document.getElementById("fName").files[0]);
    	
    	$.ajax({
    		url  : "${ctp}/study/csv/csvForm",
    		type : "post",
    		data : formData,
    		processData: false,
    		contentType: false,
    		success:function(res) {
    			$("#demoSpin").hide();
    			if(res != "0") {
    				alert("처리완료");
    				$("#demo").html(res);
    			}
    			else alert("실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
    
    function fDelete() {
    	let csvTable = prompt("삭제할 테이블명을 기입하시오.");
    	let ans = confirm(csvTable + " 테이블을 삭제하시겠습니까?");
    	if(!ans) return false;
    	
    	$.ajax({
    		url  : "${ctp}/study/csv/csvDeleteTable",
    		type : "post",
    		data : {csvTable : csvTable},
    		success:function(res) {
    			if(res != "0") alert("삭제 완료!!");
    			else alert("삭제 실패~~");
    		},
    		error : function() {
    			alert("전송오류!");
    		}
    	});
    }
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
  <h2>CSV파일을 MySQL에 저장하기</h2>
  <hr/>
  <div class="input-group">
    <div class="input-group-text">변환할 파일 선택</div>
    <div class="input-group-append"><input type="file" name="fName" id="fName" class="form-control-file border"/></div>
    <div class="input-group-append"><input type="button" value="변환후DB저장" onclick="fCheck()" class="btn btn-success mr-1"/></div>
    <div class="input-group-append"><input type="button" value="DB삭제" onclick="fDelete()" class="btn btn-danger mr-5"/></div>
    <div class="input-group-append" id="demoSpin"></div>
  </div>
	<hr/>
	<div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>