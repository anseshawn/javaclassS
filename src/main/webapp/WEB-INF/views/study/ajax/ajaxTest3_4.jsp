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
    
    function midCheck() {
    	let mid = document.getElementById("mid").value;
    	if(mid.trim() == "") {
    		alert("아이디를 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study/ajax/ajaxTest3_4",
    		type : "post",
    		data : {mid : mid},
    		success:function(res) {
    			//console.log(res);
    			$("#demo").html(res);
    		},
    		error : function() {
    			alert("전송 오류!");
    		}
    	});
    }
    
    function addressCheck() {
    	let address = document.getElementById("address").value;
    	if(address.trim() == "") {
    		alert("주소를 선택하세요");
    		return false;
    	}
    	
    	$.ajax({
    		url  : "${ctp}/study/ajax/ajaxTest3_5",
    		type : "post",
    		data : {address : address},
    		success:function(res) {
    			//console.log(res);
    			let str = '';
    			str += '<table class="table-hover"><tr style="background-color:#eee">';
    			str += '<th>아이디</th><th>성명</th><th>나이</th><th>주소</th>';
    			for(let i=0; i<res.length; i++) {
    				str += '<tr>';
    				str += '<td>'+res[i].mid+'</td>';
    				str += '<td>'+res[i].name+'</td>';
    				str += '<td>'+res[i].age+'</td>';
    				str += '<td>'+res[i].address+'</td>';
    				str += '</tr>';
    			}
    			str += '<tr><td colspan="4" class="m-0 p-0"></td></tr>';
    			str += '</table>';
    			$("#addressDemo").html(str);
    		},
    		error : function() {
    			alert("전송 오류!");
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
  <h2>ajaxTest3_4.jsp(ArrayList 처리)</h2>
  <hr/>
  <form>
    <h4>1.아이디를 선택하세요</h4>
    <table class="table">
      <tr>
        <td>
			    <select name="mid" id="mid" onchange="midCheck()">
			      <option value="">아이디선택</option>
			      <c:forEach var="mid" items="${midVos}" varStatus="st">
			        <option>${mid}</option>
			      </c:forEach>
			    </select>
        </td>
        <td>
	  			<div id="demo"></div>
	  		</td>
	    </tr>
	  </table>
    <hr/>
    <h4>2.주소를 선택하세요</h4>
    <table class="table">
      <tr>
        <td>
			    <select name="address" id="address" onchange="addressCheck()">
			      <option value="">주소선택</option>
			      <c:forEach var="address" items="${addressVos}" varStatus="st">
			        <option>${address}</option>
			      </c:forEach>
			    </select>
        </td>
        <td>
	  			<div id="addressDemo"></div>
	  		</td>
	    </tr>
	  </table>
    <hr/>
    <input type="button" value="돌아가기" onclick="location.href='ajaxForm';" class="btn btn-warning mr-3 mb-3"/>
  </form>
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>