<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>validatorForm.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		$(document).ready(function(){
			$("#btnShow").show();
			$("#btnHide").hide();
			$("#userInput").hide();
			
			$("#btnShow").click(function(){
				$("#userInput").show();
				$("#btnShow").hide();
				$("#btnHide").show();
			});
			$("#btnHide").click(function(){
				$("#userInput").hide();
				$("#btnShow").show();
				$("#btnHide").hide();
			});
		});
		
		function deleteCheck(idx) {
			let ans = confirm("선택한 회원을 삭제하겠습니까?");
			if(!ans) return false;
			
			location.href="${ctp}/dbtest/dbtestDelete?tempFlag=validator&idx="+idx;
		}
		
    function idCheck(){
    	let mid = myform.mid.value;
    	let url = "${ctp}/dbtest/dbtestWindow?mid="+mid;
    	window.open(url,"dbtestWindow","width=500px,height=250px");
    }
		
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<pre>데이터베이스에 저장할 때 백엔드에서 체크하는 방법</pre>
	<h2>회원 리스트</h2>
	<hr/>
	<input type="button" value="회원가입창 보이기" id="btnShow" class="btn btn-outline-primary btn-sm mb-3"/>
	<input type="button" value="회원가입창 가리기" id="btnHide" class="btn btn-outline-primary btn-sm mb-3"/>
	<div id="userInput">
		<form name="myform" method="post">
			<table class="table table-bordered">
				<tr>
					<th>아이디</th>
					<td>
						<div class="input-group">
							<input type="text" name="mid" class="form-control"/>
							<div class="input-group-append">
								<input type="button" value="아이디 중복체크" onclick="idCheck()" class="btn btn-info"/>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" name="name" class="form-control"/></td>
				</tr>
				<tr>
					<th>나이</th>
					<td><input type="number" name="age" class="form-control"/></td>
				</tr>
				<tr>
					<th>주소</th>
					<td><input type="text" name="address" class="form-control"/></td>
				</tr>
				<tr>
					<td colspan="2" class="text-center">
						<input type="submit" value="회원가입" class="btn btn-primary" />
						<input type="reset" value="다시입력" class="btn btn-secondary" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<table class="table table-hover">
		<tr class="table-secondary">
			<th>번호</th>
			<th>아이디</th>
			<th>이름</th>
			<th>나이</th>
			<th>주소</th>
			<th>비고</th>
		</tr>
		<c:forEach var="vo" items="${vos}" varStatus="st">
			<tr>
				<td>${vo.idx}</td>
				<td>${vo.mid}</td>
				<td>${vo.name}</td>
				<td>${vo.age}</td>
				<td>${vo.address}</td>
        <td>
          <a href="javascript:deleteCheck(${vo.idx})" class="badge badge-danger">삭제</a>
        </td>
      </tr>
		</c:forEach>
		<tr><td colspan="6" class="m-0 p-0"></td></tr>
	</table>
	<br/>
	
  <div class="row">
  	<div class="col"><a href="${ctp}/" class="btn btn-warning">돌아가기</a></div>
  </div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>