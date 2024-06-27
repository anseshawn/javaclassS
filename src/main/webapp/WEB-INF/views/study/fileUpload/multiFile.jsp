<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>multiFile.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		function fCheck() {
			let fName = document.getElementById("fName").value;
			let ext = fName.substring(fName.lastIndexOf(".")+1).toLowerCase();
			let maxSize = 1024 * 1024 * 20; //한번에 업로드 가능한 최대용량은 20MByte
			
			if(fName.trim()=="") {
				alert("업로드 할 파일을 선택하세요");
				return false;
			}
			
			let fileSize = document.getElementById("fName").files[0].size;
			if(fileSize > maxSize) {
				alert("업로드 할 파일의 최대용량은 20MByte 입니다.");
			}
			else if(ext != "jpg" && ext != "png" && ext != "gif" && ext != "zip" && ext != "txt" && ext != "ppt" && ext != "pptx" && ext != "hwp") {
				alert("업로드 가능한 파일은 'jpg, gif, png, zip, txt, ppt, pptx, hwp'파일 입니다.");
			}
			else {
				myform.submit();
			}
		}
		
		// 개별 파일 삭제 처리
		function fileDelete(file) {
			let ans = confirm("선택한 파일을 삭제하시겠습니까?");
			if(!ans) return false;
			
			$.ajax({
				url: "${ctp}/study/fileUpload/fileDelete",
				type: "post",
				data: {file : file},
				success: function(res){
					if(res != 0) {
						alert("파일이 삭제 되었습니다.");
						location.reload();
					}
					else alert("파일 삭제 실패");
				},
				error: function(){
					alert("전송 오류");
				}
			});
		}
		
		// 전체 파일 삭제 처리
		function fileDeleteAll() {
			let ans = confirm("모든 파일을 삭제하시겠습니까?");
			if(!ans) return false;
			
			$.ajax({
				url: "${ctp}/study/fileUpload/fileDeleteAll",
				type: "post",
				success: function(res){
					if(res != 0) {
						alert("파일이 삭제 되었습니다.");
						location.reload();
					}
					else alert("파일 삭제 실패");
				},
				error: function(){
					alert("전송 오류");
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
	<h2>멀티파일 업로드 연습</h2>
	<form name="myform" method="post" enctype="multipart/form-data">
		<p>올린이 : 
			<input type="text" name="mid" value="${sMid}" class="form-control" />
		</p>
		<p>파일명 : 
			<input type="file" name="fName" id="fName" multiple class="form-control-file border" accept=".jpg,.gif,.png,.zip,.txt,.ppt,.pptx,.hwp" />
		</p>
		<p>
			<input type="button" value="파일업로드" onclick="fCheck()" class="btn btn-primary"/>
			<input type="reset" value="다시선택" class="btn btn-warning"/>
			<input type="button" value="싱글파일업로드로 이동(파일리스트)" onclick="location.href='${ctp}/study/fileUpload/fileUpload';" class="btn btn-success"/>
			<input type="button" value="그림파일업로드로 이동" onclick="location.href='${ctp}/study/fileUpload/multiFile2';" class="btn btn-secondary"/>
		</p>
	</form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>