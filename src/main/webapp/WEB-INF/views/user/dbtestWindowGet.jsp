<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>dbtestWindowGet.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		function wClose(){
    	opener.window.myform.mid.value = '${mid}';
    	opener.window.myform.name.focus();
    	
    	window.close();
		}
		
		function idCheck() {
			let mid = childForm.mid.value;
			if(mid.trim()==""){
				alert("검색할 아이디를 입력하세요.");
				childForm.mid.focus();
				return false;
			}
			childForm.submit();
		}
	</script>
</head>
<body>
<p><br/></p>
<div class="container">
	<h3>아이디 중복체크</h3>
	<hr/>
	<div class="text-center">
		<c:if test="${res!=0}">
			<font color="red"><b>${mid}는 ${message}</b></font><br/>
			<form name="childForm" method="get" action="${ctp}/dbtest/dbtestWindow">
				<p>아이디 :
					<input type="text" name="mid" />
					<input type="button" value="아이디 검색" onclick="idCheck()"/>
				</p>
			</form>
		</c:if>
		<c:if test="${res==0}">
			<font color="blue"><b>${message}</b></font>
		</c:if>
		<hr/>
		<input type="button" value="닫기" onclick="wClose()" class="btn btn-success"/>
	</div>
</div>
<p><br/></p>
</body>
</html>