<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<% pageContext.setAttribute("newLine", "\n"); %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>boardContent.jsp</title>
  <jsp:include page="/WEB-INF/views/include/bs4.jsp" />
  <style>
  	th {
  		text-align: center;
  		background-color: #eee;
  	}
  </style>
  <script>
  	'use strict';
  	
  	function boardDelete() {
  		let ans = confirm("현재 게시글을 삭제 하시겠습니까?");
  		if(ans) location.href = "boardDelete?idx=${vo.idx}";
  	}
  	
  	// 좋아요 처리
  	function goodCheck() {
  		$.ajax({
  			url : "boardGoodCheck",
  			type : "post",
  			data : {idx : ${vo.idx}},
  			success : function(res){
  				if(res != "0") location.reload();
  			},
  			error : function(){
  				alert("전송 오류");
  			}
  		});
  	}
  	// 좋아요 처리(중복불허)
  	function goodCheck2() {
  		$.ajax({
  			url : "boardGoodCheck2",
  			type : "post",
  			data : {idx : ${vo.idx}},
  			success : function(res){
  				if(res != "0") location.reload();
  				else alert("이미 좋아요 버튼을 클릭하셨습니다.");
  			},
  			error : function(){
  				alert("전송 오류");
  			}
  		});
  	}
  	
  	// 좋아요(따봉) 수 증가(중복허용)
  	function goodCheckPlus() {
  		$.ajax({
  			url : "boardGoodCheckPlusMinus",
  			type : "post",
  			data : {
  				idx : ${vo.idx},
  				goodCnt : +1
  			},
  			success : function(res){
  				location.reload();
  			},
  			error : function(){
  				alert("전송 오류");
  			}
  		});
  	}
  	
  	// 좋아요 수 감소(싫어요)(중복허용)
  	function goodCheckMinus() {
  		$.ajax({
  			url : "boardGoodCheckPlusMinus",
  			type : "post",
  			data : {
  				idx : ${vo.idx},
  				goodCnt : -1
  			},
  			success : function(res){
  				location.reload();
  			},
  			error : function(){
  				alert("전송 오류");
  			}
  		});
  	}
  	
  	// 신고 사유 '기타' 선택 시 입력 창 보이기
  	function etcShow() {
  		$("#complaintTxt").show();
  	}
  	
  	// 신고 사유 선택 후 확인 버튼(신고사항 전송하기)
  	function complaintCheck() {
  		// type = 'radio' (따옴표 신경쓰기) 혹은 따옴표 없으면 띄어쓰기도 없이 type=radio
  		if(!$("input[type=radio][name=complaint]:checked").is(':checked')) {
  			alert("신고 항목을 선택해주세요.");
  			return false;
  		}
  		//if($("input[type=radio][id=complaint7]:checked") && $("#complaintTxt").val() == "")
  		if($("input[type=radio]:checked").val() == '기타' && $("#complaintTxt").val() == "") {
  			alert("기타 사유를 입력해주세요.");
  			return false;
  		}
  		
  		let cpContent = modalForm.complaint.value;
  		if(cpContent == '기타') cpContent += "/"+$("#complaintTxt").val();
  		
  		//alert("신고내용 : " + cpContent);
  		let query = {
  				part : 'board',
  				partIdx : ${vo.idx},
  				cpMid : '${sMid}',
  				cpContent : cpContent
  		}
  		
  		$.ajax({
  			url: "boardComplaintInput.ad",
  			type: "post",
  			data : query,
  			success: function(res){
  				if(res != "0") {
  					alert("신고가 완료 되었습니다.");
  					location.reload();
  				}
  				else alert("신고 실패");
  			},
  			error: function(){
  				alert("전송 오류");
  			}
  		});
  	}
  	
  	// 원본글에 댓글 달기
  	function replyCheck() {
  		let content = $("#content").val();
  		if(content.trim() == "") {
  			alert("댓글을 입력하세요");
  			return false;
  		}
  		let query = {
  				boardIdx : ${vo.idx},
  				mid : '${sMid}',
  				nickName : '${sNickName}',
  				hostIp : '${pageContext.request.remoteAddr}',
  				content : content
  		}
  		
  		$.ajax({
  			url : "${ctp}/board/boardReplyInput",
  			type : "post",
  			data : query,
  			success : function(res){
  				if(res != "0") {
  					alert("댓글이 입력되었습니다.");
  					location.reload(); // 부분 리로드는 어떻게...?
  				}
  				else alert("댓글 입력 실패");
  			},
  			error : function () {
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 댓글 삭제
  	function replyDelete(idx) {
  		let ans = confirm("현재 댓글을 삭제하시겠습니까?");
  		if(!ans) return false;
  		
  		$.ajax({
  			url : "boardReplyDelete",
  			type : "post",
  			data : {idx : idx},
  			success : function(res){
  				if(res != "0") {
  					alert("댓글이 삭제되었습니다.");
  					location.reload();
  				}
  				else alert("삭제 실패");
  			},
  			error: function(){
  				alert("전송오류");
  			}
  		});
  	}
  	
  	// 처음에는 대댓글 닫기 버튼 감추기
  	$(function(){
  		$(".replyCloseBtn").hide();
  	});
  	// 답글 버튼 클릭시 대댓글 입력박스 보여주기
  	function replyShow(idx) {
  		$("#replyShowBtn"+idx).hide();
  		$("#replyCloseBtn"+idx).show();
  		$("#replyDemo"+idx).slideDown(100);
  	}
  	// 닫기 버튼 클릭 시 대댓글 입력 박스 감추기
  	function replyClose(idx) {
  		$("#replyShowBtn"+idx).show();
  		$("#replyCloseBtn"+idx).hide();
  		$("#replyDemo"+idx).slideUp(100);
  	}
  	
  	// 대댓글(부모댓글의 답변글)의 입력처리
  	function replyCheckRe(idx,re_step,re_order) {
  		let content = $("#contentRe"+idx).val();
  		if(content.trim()=="") {
  			alert("답변 글을 입력하세요.");
  			$("#contentRe"+idx).focus();
  			return false;
  		}
  		
  		let query = {
  				boardIdx : ${vo.idx},
  				re_step : re_step,
  				re_order : re_order,
  				mid : '${sMid}',
  				nickName : '${sNickName}',
  				hostIp : '${pageContext.request.remoteAddr}',
  				content : content
  		}
  		
  		$.ajax({
  			url: "${ctp}/board/boardReplyInputRe",
  			type: "post",
  			data: query,
  			success: function(res){
  				if(res != "0") {
  					alert("대댓글이 입력되었습니다.");
  					location.reload();
  				}
  				else alert("답변 등록 실패");
  			},
  			error: function(){
  				alert("전송 오류");
  			}
  		});
  	}
  </script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<p><br/></p>
<div class="container">
	<h2 class="text-center mb-5">글 내 용 보 기</h2>
	<table class="table table-bordered">
		<tr>
			<th>글쓴이</th>
			<td>${vo.nickName}</td>
			<th>작성일</th>
			<td>${fn:substring(vo.WDate,0,16)}</td>
		</tr>
		<tr>
			<th>조회수</th>
			<td>${vo.readNum}</td>
			<th>접속IP</th>
			<td>${vo.hostIp}</td>
		</tr>
		<tr>
			<th>글제목</th>
			<td colspan="3">
				${vo.title} (<a href="javascript:goodCheck()">❤</a> : ${vo.good}) /
				<a href="javascript:goodCheckPlus()"> 👍</a>
				<a href="javascript:goodCheckMinus()"> 👎</a> /
				(<a href="javascript:goodCheck2()"><font color="blue" size="5">♥</font></a> : ${vo.good}) 
			</td>
		</tr>
		<tr>
			<th>글내용</th>
			<td colspan="3" style="height:220px">${fn:replace(vo.content,newLine,"<br/>")}</td>
		</tr>
		<tr>
			<td colspan="4">
				<div class="row">
	      	<div class="col">
						<c:if test="${empty flag}"><input type="button" value="돌아가기" onclick="location.href='boardList?pag=${pag}&pageSize=${pageSize}';" class="btn btn-secondary"/></c:if>
						<c:if test="${!empty flag}"><input type="button" value="돌아가기" onclick="location.href='boardSearch?pag=${pag}&pageSize=${pageSize}&search=${search}&searchString=${searchString}';" class="btn btn-secondary"/></c:if>
					</div>
					<c:if test="${sNickName == vo.nickName || sLevel == 0}">
						<div class="col text-right">
							<input type="button" value="수정" onclick="location.href='boardUpdate?idx=${vo.idx}&pag=${pag}&pageSize=${pageSize}';" class="btn btn-warning"/>
							<input type="button" value="삭제" onclick="boardDelete()" class="btn btn-danger ml-auto"/>
						</div>
					</c:if>
					<c:if test="${sNickName != vo.nickName}">
						<div class="col text-right">
							<c:if test="${report == 'OK'}"><font color="red"><b>신고중입니다.</b></font></c:if>
							<c:if test="${report != 'OK'}"><input type="button" value="신고하기" class="btn btn-danger btn-sm ml-auto" data-toggle="modal" data-target="#myModal"/></c:if>
						</div>
					</c:if>
<%-- 					<c:if test="${sNickName != vo.nickName}">
		        <div class="col text-right">
		          <c:if test="${report == 'OK'}"><font color='red'><b>현재 이글은 신고중입니다.</b></font></c:if>
			        <c:if test="${report != 'OK'}"><input type="button" value="신고하기" data-toggle="modal" data-target="#myModal" class="btn btn-danger text-right" /></c:if>
		        </div>
	        </c:if> --%>
				</div>
			</td>
		</tr>
	</table>
	<hr/>
	<!-- 이전글 / 다음글 출력하기 -->
	<table class="table table-borderless">
		<tr>
			<td>
				<c:if test="${!empty nextVo.title}">
					<i class="fa-solid fa-angles-up"></i><a href="boardContent?idx=${nextVo.idx}"> 다음글 : ${nextVo.title}</a><br/>
				</c:if>
				<c:if test="${!empty preVo.title}">
					<i class="fa-solid fa-angles-down"></i><a href="boardContent?idx=${preVo.idx}"> 이전글 : ${preVo.title}</a><br/>
				</c:if>
			</td>
		</tr>
	</table>
	<!-- 이전글 / 다음글 출력 끝 -->
</div>
<p><br/></p>

<!-- 댓글 처리(리스트/입력) 시작 -->
<div class="container">
	<h4>댓 글</h4>
	<!-- 댓글 리스트 보여주기 -->
	<table class="table table-hover text-center">
		<tr>
			<th>작성자</th>
			<th>내용</th>
			<th>작성일자</th>
			<th>접속IP</th>
			<th>답글</th>
		</tr>
		<c:forEach var="replyVo" items="${replyVos}" varStatus="st">
			<tr>
				<td class="text-left">
					<c:if test="${replyVo.re_step >= 1}">
						<c:forEach var="i" begin="1" end="${replyVo.re_step}">&nbsp;&nbsp;</c:forEach>└─▶
					</c:if>
					${replyVo.nickName}
					<c:if test="${sMid == replyVo.mid || sLevel == 0}">
						<a href="javascript:replyDelete(${replyVo.idx})" title="댓글삭제"> x</a>
					</c:if>
				</td>
				<td class="text-left">${fn:replace(replyVo.content,newLine,"<br/>")}</td>
				<td>${fn:substring(replyVo.WDate,0,10)}</td>
				<td>${replyVo.hostIp}</td>
				<td>
					<a href="javascript:replyShow(${replyVo.idx})" id="replyShowBtn${replyVo.idx}" class="badge badge-secondary">답글</a>
					<a href="javascript:replyClose(${replyVo.idx})" id="replyCloseBtn${replyVo.idx}" class="badge badge-secondary replyCloseBtn">닫기</a>
				</td>
			</tr>
			<tr>
				<td colspan="5" class="m-0 p-0">
					<div id="replyDemo${replyVo.idx}" style="display:none;">
						<table class="table table-center">
							<tr>
								<td style="85%" class="text-left"> 답글 내용:
									<textarea rows="4" name="contentRe" id="contentRe${replyVo.idx}" class="form-control">@${replyVo.nickName} &nbsp;</textarea>
								</td>
								<td style="15%">
									<br/>
									<p>작성자 : ${sNickName}</p>
									<input type="button" value="답글달기" onclick="replyCheckRe(${replyVo.idx},${replyVo.re_step},${replyVo.re_order})" class="btn btn-secondary btn-sm"/>
								</td>
							</tr>
						</table>
					</div>
				</td>
			</tr>
		</c:forEach>
		<tr><td colspan="4" class="m-0 p-0"></td></tr>
	</table>
	<!-- 댓글 입력창 -->
	<form name="replyForm">
		<table class="table table-center">
			<tr>
				<td style="width:85%" class="text-left">
					댓글 입력 : 
					<textarea rows="4" name="content" id="content" class="form-control"></textarea>
				</td>
				<td style="width:15%">
					<br/>
					<p>작성자 : ${sNickName}</p>
					<p><input type="button" value="등록" onclick="replyCheck()" class="btn btn-info btn-sm"/></p>
				</td>
			</tr>
		</table>
	</form>
	<br/>
</div>
<!-- 댓글 처리 끝 -->

<!-- 신고하기 폼 modal -->
<div class="modal fade" id="myModal">
  <div class="modal-dialog modal-dialog-centered">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header">
        <h4 class="modal-title">현재 게시글 신고하기</h4>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body">
      	<b>신고사유 선택</b>
      	<hr/>
      	<form name="modalForm">
      		<div><input type="radio" name="complaint" id="complaint1" value="광고,홍보,영리목적" /> 광고,홍보,영리목적</div>
      		<div><input type="radio" name="complaint" id="complaint2" value="욕설,비방,차별,혐오" /> 욕설,비방,차별,혐오</div>
      		<div><input type="radio" name="complaint" id="complaint3" value="불법정보" /> 불법정보</div>
      		<div><input type="radio" name="complaint" id="complaint4" value="음란,청소년유해" /> 음란,청소년유해</div>
      		<div><input type="radio" name="complaint" id="complaint5" value="개인정보노출,유포,거래" /> 개인정보노출,유포,거래</div>
      		<div><input type="radio" name="complaint" id="complaint6" value="도배,스팸" /> 도배,스팸</div>
      		<div><input type="radio" name="complaint" id="complaint7" value="기타" onclick="etcShow()" /> 기타</div>
      		<div id="etc"><textarea rows="2" id="complaintTxt" class="form-control" style="display:none"></textarea> </div>
      		<hr/>
      		<input type="button" value="확인" onclick="complaintCheck()" class="btn btn-primary form-control" />
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