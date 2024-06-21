<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>ajaxTest3_4.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		function fCheck(){
			let name = document.getElementById("name").value;
			if(name.trim()==""){
				alert("이름을 선택하세요");
				return false;
			}
			
			$.ajax({
				url: "${ctp}/study/ajax/ajaxTest3_4",
				type: "post",
				data: {name : name},
				success: function(res){
					//console.log(res.mid);
					//console.log(res.age);
					let str = "<hr/><h4>선택한 회원의 정보</h4><br/>";
					str += "<b>이름</b> : "+res.name+"<br/>";
					str += "<b>아이디</b> : "+res.mid+"<br/>";
					str += "<b>나이</b> : "+res.age+"<br/>";
					str += "<b>주소</b> : "+res.address+"<br/>";
					$("#demo").html(str);
				},
				error: function(){
					alert("전송오류");
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
	<h2>ajaxTest3_4.jsp(DB이름으로 자료검색)</h2>
	<hr/>
	<form>
		<h3>회원 이름을 선택하세요</h3>
		<select name="name" id="name">
			<option value="">이름선택</option>
			<c:forEach var="vo" items="${vos}" varStatus="st">
				<option>${vo.name}</option>
			</c:forEach>
		</select>
		<input type="button" value="선택" onclick="fCheck()" class="btn btn-info mr-3 mb-3" />
		<input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-warning mr-3 mb-3" />
	</form>
	<div id="demo"></div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>