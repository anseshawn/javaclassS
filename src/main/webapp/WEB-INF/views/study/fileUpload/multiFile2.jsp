<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>multiFile2.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<style>
		.imgsWrap {
			border: 2px solid #eee;
			margin: 10px;
		}
	
		.imgsWrap img {
			max-width: 300px;
			margin: 5px;
		}
		
		.myBtn {
			width: 200px;
			padding: 10px;
			background-color: #09A;
			color: #fff;
			border-radius: 5px;
		}
	</style>
	<script>
		'use strict';
		
		let imgFiles = [];
		
		$(document).ready(function(){
			$("#inputImgs").on("change", function(e){
				//imgFiles = [];
				$(".imgsWrap").empty();
				
				let files = e.target.files;
				let filesArr = Array.prototype.slice.call(files); // 배열객체에 있는 것을 잘라서 배열에 담기
				
				let idx = 0;
				filesArr.forEach(function(f){
					//mimeType에 들어있는 이미지의 모든 타입과 일치하는지 확인
					if(!f.type.match("image.*")) {
						alert("이미지 파일만 업로드 가능합니다.");
						return false;
					}
					imgFiles.push(f); // 정상적인 파일이면 imgFiles 배열에 push
					
					// 그림파일 랜더링 시켜서 브라우저에 뿌리기
					let reader = new FileReader();
					reader.onload = function(e) {
						let str = "<a href='javascript:void(0);' onclick='deleteImage("+idx+")' id='imgId"+idx+"'><img src='"+e.target.result+"' data-file='"+f.Name+"' class='' title='그림을 클릭하시면 제거됩니다.("+idx+")' /></a>"; // data-file 파일에 대한 형식
						$(".imgsWrap").append(str);
						idx++;
					}
					reader.readAsDataURL(f);
				});
				
			});
		});
		
		function deleteImage(idx) {
			imgFiles.slice(idx,1);
			
			let imgId = "#imgId"+idx;
			$(imgId).remove();
		}
		
		function imageUpload() {
			$("#inputImgs").trigger('click');
		}
		
		function fCheck() {
			if(imgFiles.length < 1) {
				alert("파일을 한개 이상 선택해 주세요.");
				return;
			}
			
			let imgNames = "";
			
			for(let i=0; i<imgFiles.length; i++) {
				imgNames += imgFiles[i].name + "/";
			}
			imgNames = imgNames.substring(0, imgNames.length-1);
			$("#imgNames").val(imgNames);
			myform.submit();
		}
	</script>
</head>
<body>
<jsp:include page="/WEB-INF/views/include/nav.jsp" />
<jsp:include page="/WEB-INF/views/include/slide2.jsp" />
<p><br/></p>
<div class="container">
	<h2>멀티(그림)파일 업로드 연습2</h2>
	<form name="myform" method="post" enctype="multipart/form-data">
		<a href="javascript:imageUpload()" class="myBtn">이미지 불러오기</a>
		<input type="file" name="fName" id="inputImgs" multiple style="display:none;" accept=".jpg,.gif,.png,.zip,.txt,.ppt,.pptx,.hwp" />
		<p style="margin-top:20px">
			<input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-primary"/>
			<input type="reset" value="다시선택" class="btn btn-warning"/>
			<input type="button" value="싱글파일업로드로 이동(파일리스트)" onclick="location.href='${ctp}/study/fileUpload/fileUpload';" class="btn btn-success"/>
			<input type="button" value="멀티파일업로드로 이동" onclick="location.href='${ctp}/study/fileUpload/multiFile';" class="btn btn-secondary"/>
		</p>
		<input type="hidden" name="imgNames" id="imgNames" />
	</form>
	<hr/>
	<div>
		<div class="imgsWrap">
			<img id="img" />
		</div>
	</div>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>