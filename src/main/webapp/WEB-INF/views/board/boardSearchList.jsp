<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>boardList.jsp</title>
  <%@ include file = "/WEB-INF/views/include/bs4.jsp" %>
  <script>
  	'use strict';
  	
  	function pageSizeCheck(){
  		let pageSize = $("#pageSize").val();
  		location.href = "BoardSearchList.bo?search=${search}&searchString=${searchString}&pageSize="+pageSize;
  	}
  	
  	function modalCheck(hostIp,mid,nickName,idx){
  		$("#myModal1 #modalHostIp").text(hostIp);  		
  		$("#myModal1 #modalMid").text(mid);  		
  		$("#myModal1 #modalNickName").text(nickName);  		
  		$("#myModal1 #modalIdx").text(idx);  		
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
	<table class="table table-borderless m-0 p-0">
		<tr>
			<td colspan="2">
				<!-- 게시판 조건별 검색 리스트 -->
				<h2 class="text-center">검 색 결 과</h2>
				 (<font color="blue">${searchTitle}</font>(으)로 <font color="blue">${searchString}</font>(을)를 검색한 결과 <font color="red"><b>${searchCount}</b></font>건의 자료가 검색되었습니다.)
			</td>
		</tr>
		<tr>
			<td><c:if test="${sLevel != 1}"><a href="BoardInput.bo" class="btn btn-primary btn-sm">글쓰기</a></c:if></td>
			<td class="text-right">
				<select name="pageSize" id="pageSize" onchange="pageSizeCheck()">
					<option value="3" ${pageSize==3 ? "selected" : "" } >3개 보기</option>
					<option value="5" ${pageSize==5 ? "selected" : "" }>5개 보기</option>
					<option value="10" ${pageSize==10 ? "selected" : "" }>10개 보기</option>
					<option value="20" ${pageSize==20 ? "selected" : "" }>20개 보기</option>
				</select>
			</td>
		</tr>
	</table>
	<table class="table table-bordered m-0 p-0 text-center">
		<tr class="table-dark text-dark text-center	">
			<th>글번호</th>
			<th>글제목</th>
			<th>글쓴이</th>
			<th>작성일</th>
			<th>조회수(❤)</th>
		</tr>
		<c:set var="curScrStartNo" value="${curScrStartNo}"/>
		<c:forEach var="vo" items="${vos}" varStatus="st">
			<c:if test="${vo.openSw == 'OK' || sLevel == 0 || sNickName == vo.nickName}">
				<tr>
					<td>${curScrStartNo}</td>
					<td class="text-left"> <%-- 페이지 처리하게 되면 페이지 사이즈, 서치 등등 ... 함께 넘겨야함 --%>
						<a href="BoardContent.bo?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}&flag=search&search=${search}&searchString=${searchString}">${vo.title}</a>
						<c:if test="${vo.hour_diff <= 24}"><img src="${ctp}/images/new.gif"/></c:if> 
					</td>
					<td>
						${vo.nickName}
						<c:if test="${sLevel == 0}">
							<!-- 숫자 넘길 땐 따옴표 없어도 되지만 모달에 넘길 때 타입이 일정하지 않으면 에러 발생 가능성이 있기 때문에 그냥 문자 형태로.. -->
							<a href="#" onclick="modalCheck('${vo.hostIp}','${vo.mid}','${vo.nickName}','${vo.idx}')" data-toggle="modal" data-target="#myModal1" class="badge badge-primary">모달출력</a>
						</c:if>
					</td>
					<td>
					<!-- 1일(24시간)이내는 시간만 표시, 이후는 날짜와 시간을 표시 : 2024-05-14 10:43:52 -->
						${vo.date_diff == 0 ? fn:substring(vo.WDate,11,19) : fn:substring(vo.WDate,0,10) }
					</td>
					<td>${vo.readNum}(❤${vo.good})</td>
				</tr>
			</c:if>
			<c:set var="curScrStartNo" value="${curScrStartNo - 1}"/>
		</c:forEach>
		<tr><td colspan="5" class="m-0 p-0"></td></tr>
	</table>
	<br/>
	<!-- 블록페이지 시작 -->	
	<div class="text-center">
		<ul class="pagination justify-content-center" style="margin:20px 0">
			<c:if test="${pag > 1}"><li class="page-item"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=1&pageSize=${pageSize}">처음</a></li></c:if>
			<c:if test="${curBlock > 0}"><li class="page-item"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=${(curBlock-1)*blockSize+1}&pageSize=${pageSize}">이전</a></li></c:if>
			<c:forEach var="i" begin="${(curBlock*blockSize+1)}" end="${(curBlock)*blockSize+blockSize}" varStatus="st">
				<c:if test="${i <= totPage && i == pag}"><li class="page-item active"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
				<c:if test="${i <= totPage && i != pag}"><li class="page-item"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=${i}&pageSize=${pageSize}">${i}</a></li></c:if>
			</c:forEach>
			<c:if test="${curBlock < lastBlock}"><li class="page-item"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=${(curBlock+1)*blockSize+1}&pageSize=${pageSize}">다음</a></li></c:if>
			<c:if test="${pag < totPage}"><li class="page-item"><a class="page-link" href="${ctp}/BoardSearchList.bo?search=${search}&searchString=${searchString}&pag=${totPage}&pageSize=${pageSize}">끝</a></li></c:if>
		</ul>
	</div>
	<!-- 블록페이지 끝 -->
	<br/>
	<!-- 검색기 시작 -->
	<!-- 
	<div class="container text-center">
		<form name="searchForm" method="post" action="BoardSearch.bo">
			<b>검색 : </b>
			<select name="search" id="search">
				<option value="title">제목</option>
				<option value="nickName">작성자</option>
				<option value="content">내용</option>
			</select>
			<input type="text" name="searchString" id="searchString" required />
			<input type="submit" value="검색" class="btn btn-secondary btn-sm"/>
		</form>
	</div>
	 -->
	<!-- 검색기 끝 -->
	<input type="button" value="돌아가기" onclick="location.href='BoardList.bo';" class="btn btn-warning form-control" />
</div>
<p><br/></p>

<!-- 글쓴이 정보 모달에 출력하기 -->
<div class="modal fade" id="myModal1">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">회원 정보</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
      	<form name="midModalForm">
      		<!-- 
      		아이디 : 
      		<input type="text" name="mid" id="mid" placeholder="아이디를 입력하세요" class="form-control mb-2" required />
      		<input type="button" value="이름확인" onclick="nameCheck()" class="btn btn-success form-control" />
      		 -->
      		 게시글 고유번호 : <span id="modalIdx"></span><br/>
      		 IP : <span id="modalHostIp"></span><br/>
      		 아이디 : <span id="modalMid"></span><br/>
      		 닉네임 : <span id="modalNickName"></span><br/>
      	</form>
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
      </div>
      
    </div>
  </div>
</div>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>