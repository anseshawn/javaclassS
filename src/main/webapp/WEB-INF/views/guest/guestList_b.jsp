<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); // \n을 인식하지 못하기 때문에 자바에서 newLine으로 대체 후 아래 내용에서 replace함수 이용 %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>guestList.jsp</title>
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
</head>
<style>
	th {
		background-color: #AED9DA;
		text-align: center;
	}
	
</style>
<script>
	'use strict';
	
	function delCheck(idx) {
		let ans = confirm("현재 방명록을 삭제하시겠습니까?");
		if(ans) {
			location.href = '${ctp}/GuestDelete?idx='+idx;
			return false;
		}
	}
	
	function pageSizeCheck() {
		let pageSize = document.getElementById("pageSize").value;
		location.href = "${ctp}/GuestList?pag=${pag}&pageSize="+pageSize;
	}
</script>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2 class="text-center">방 명 록 리 스 트</h2>
	<table class="table table-borderless m-0 p-0">
		<tr>
			<!-- <td><a href="#" class="btn btn-secondary">관리자</a></td> -->
			<td><a href="${ctp}/guest/guestInput.jsp" class="btn btn-info">글쓰기</a></td>
			<td class="text-right">
				<c:if test="${pag > 1}">
					<a href="${ctp}/GuestList?pag=1&pageSize=${pageSize}" title="처음"><i class="fa-solid fa-backward-fast" style="color:#2A93D5">&nbsp;</i></a>
					<a href="${ctp}/GuestList?pag=${pag-1}&pageSize=${pageSize}" title="이전페이지"><i class="fa-solid fa-backward-step" style="color:#2A93D5">&nbsp;</i></a>
				</c:if>
				${pag}/${totPage}
				<c:if test="${pag < totPage}">
					<a href="${ctp}/GuestList?pag=${pag+1}&pageSize=${pageSize}" title="다음페이지"><i class="fa-solid fa-forward-step" style="color:#2A93D5">&nbsp;</i></a>
					<a href="${ctp}/GuestList?pag=${totPage}&pageSize=${pageSize}" title="끝"><i class="fa-solid fa-forward-fast" style="color:#2A93D5"></i></a>
				</c:if>
			</td>
		</tr>
		<tr>
			<td colspan="2" class="text-right">
				<select id="pageSize" name="pageSize" onchange="pageSizeCheck()" class="custom-select custom-select-sm" style="width:100px">
					<option value="2" <c:if test="${pageSize == '2'}">selected</c:if> >2개 보기</option>
					<option value="3" <c:if test="${pageSize == '3'}">selected</c:if> >3개 보기</option>
					<option value="5" <c:if test="${pageSize == '5'}">selected</c:if> >5개 보기</option>
					<option value="10" <c:if test="${pageSize == '10'}">selected</c:if> >10개 보기</option>
				</select>
			</td>
		</tr>
	</table>
	<c:set var="curScrStartNo" value="${curScrStartNo}"/>
	<c:forEach var="vo" items="${vos}" varStatus="st">
		<table class="table table-borderless m-0 p-0">
			<tr>
				<td class="text-left">
				<!-- 고유번호 노출x, 게시글의 개수는 vos의 사이즈와 같다 -->
					번호 : ${curScrStartNo}
					<c:if test="${sAdmin == 'OK' || sName == vo.name}">
						<a href="javascript:delCheck(${vo.idx})" class="btn btn-danger btn-sm">삭제</a>
					</c:if>
				</td>
				<td class="text-right">방문IP : ${vo.hostIp}</td>
			</tr>
		</table>
		<table class="table table-bordered">
			<tr>
				<th>성명</th>
				<td>${vo.name}</td>
				<th>날짜</th>
				<td>${fn:substring(vo.visitDate,0,19)}</td>
			</tr>
			<tr>
				<th>메일주소</th>
				<td colspan="3">
					<%-- <c:if test="${vo.email == '' || vo.email == null}">-</c:if>
					<c:if test="${vo.email != '' && vo.email != null}">${vo.email}</c:if> --%>
					<c:if test="${empty vo.email || fn:length(vo.email) < 6 || fn:indexOf(vo.email,'@')==-1 || fn:indexOf(vo.email, '.')==-1}">-</c:if>
					<!-- 이메일 조건에 맞춰서 -->
					<c:if test="${!empty vo.email && fn:length(vo.email)>=6 && fn:indexOf(vo.email,'@')!=-1 && fn:indexOf(vo.email, '.')!=-1}">${vo.email}</c:if>
				</td>
			</tr>
			<tr>
				<th>홈페이지</th>
				<td colspan="3">
					<c:if test="${empty vo.homePage || fn:length(vo.homePage)<10 || fn:indexOf(vo.homePage, '.')==-1}"> -</c:if>
					<c:if test="${!empty vo.homePage && fn:length(vo.homePage)>=10 && fn:indexOf(vo.homePage, '.')!=-1}">
						<a href="${vo.homePage}" target="_blank">${vo.homePage}</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td colspan="3">${fn:replace(vo.content,newLine,"<br/>")}</td>
			</tr>
		</table>
	<br/>
	<c:set var="curScrStartNo" value="${curScrStartNo-1}"/>
	</c:forEach>

<!-- 블록페이지 시작 -->	
<div class="text-center">
	<ul class="pagination justify-content-center" style="margin:20px 0">
		<c:if test="${pag > 1}"><li class="page-item"><a class="page-link" href="${ctp}/GuestList?pag=1&pageSize=${pageSize}">처음</a></li></c:if>
		<c:if test="${curBlock > 0}"><li class="page-item"><a class="page-link" href="${ctp}/GuestList?pag=${(curBlock-1)*blockSize+1}&pageSize=${pageSize}">이전블록</a></li></c:if>
		<c:forEach var="i" begin="${(curBlock*blockSize+1)}" end="${(curBlock)*blockSize+blockSize}" varStatus="st">
			<%-- <c:if test="${}">[${i}]</c:if> --%>
			<c:if test="${i <= totPage && i == pag}"><li class="page-item active"><a class="page-link" href="${ctp}/GuestList?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
			<c:if test="${i <= totPage && i != pag}"><li class="page-item"><a class="page-link" href="${ctp}/GuestList?pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
		</c:forEach>
		<c:if test="${curBlock < lastBlock}"><li class="page-item"><a class="page-link" href="${ctp}/GuestList?pag=${(curBlock+1)*blockSize+1}&pageSize=${pageSize}">다음블록</a></li></c:if>
		<c:if test="${pag < totPage}"><li class="page-item"><a class="page-link" href="${ctp}/GuestList?pag=${totPage}&pageSize=${pageSize}">끝</a></li></c:if>
	</ul>
</div>
<!-- 블록페이지 끝 -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>