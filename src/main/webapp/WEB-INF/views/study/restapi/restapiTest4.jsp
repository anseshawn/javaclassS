<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>restapiTest4.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script>
		'use strict';
		
		const API_KEY ="l4WROcSH1Z9lH4gfJhC4jcJxxI45ejMCb9GOFv4bMg2A%2F%2FWgGhM3H3wf0Hv%2BwYncK9HWxk63ZAxvaJ6nnq47Mg%3D%3D";
		
		async function getCrimeDate() { // fetch 쓸 때 사용하는 방식
			let year = $("#year").val();
			let apiYear = "";
			
			if(year == 2015)	apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
			else if(year == 2016)	apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
			else if(year == 2017)	apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
			else if(year == 2018)	apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
			else if(year == 2019)	apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
			else if(year == 2020)	apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
			else if(year == 2021)	apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
			else if(year == 2022)	apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
			
			let url="https://api.odcloud.kr/api";
			url += apiYear;
			url += "?serviceKey="+API_KEY;
			url += "&page=1&perPage=200"; // page / perPage : 예약어
			
			let response = await fetch(url); // await: 데이터 읽어오는 동안 기다리기
			
			//console.log("response: ",response);
			
			let res = await response.json(); 
			//console.log("res : ", res);
			let str = res.data.map((item,i) => [ // (값, 인덱스) arrow function(map에선 각괄호)
				(i+1) + "."
				+ "발생년도 : " + item.발생년도 + "년"
				+ ", 경찰서 : " + item.경찰서 
				+ ", 강도 : " + item.강도 + "건"
				+ ", 절도 : " + item.절도 + "건"
				+ ", 살인 : " + item.살인 + "건"
				+"<br/>"
			]);
			$("#demo").html(str);
		}
		
		// 검색한 자료를 DB에 저장하기
		async function saveCrimeDate() { // DB에 저장
			let year = $("#year").val();
			let apiYear = "";
			
			if(year == 2015)	apiYear = "/15084592/v1/uddi:fbbfd36d-d528-4c8e-aa9b-d5cdbdeec669";
			else if(year == 2016)	apiYear = "/15084592/v1/uddi:21ec6fa1-a033-413b-b049-8433f5b446ff";
			else if(year == 2017)	apiYear = "/15084592/v1/uddi:67117bd9-5ee1-4e07-ae4a-bfe0861ee116";
			else if(year == 2018)	apiYear = "/15084592/v1/uddi:2d687e27-b5c3-4bdb-9b77-c644dcafcbc7";
			else if(year == 2019)	apiYear = "/15084592/v1/uddi:b6cc7731-181b-48e1-9a6c-ae81388e46b0";
			else if(year == 2020)	apiYear = "/15084592/v1/uddi:fdde1218-987c-49ba-9326-8e3ba276141e";
			else if(year == 2021)	apiYear = "/15084592/v1/uddi:943e757d-462b-4b3a-ab9f-9a8553637ca2";
			else if(year == 2022)	apiYear = "/15084592/v1/uddi:5e08264d-acb3-4842-b494-b08f318aa14c";
			
			let url="https://api.odcloud.kr/api";
			url += apiYear;
			url += "?serviceKey="+API_KEY;
			url += "&page=1&perPage=200"; // page / perPage : 예약어
			
			let response = await fetch(url); // await: 데이터 읽어오는 동안 기다리기
			
			let res = await response.json();
			
			let str = res.data.map((item,i) => [ // (값, 인덱스) arrow function(map에선 각괄호)
				(i+1) + "."
				+ "발생년도 : " + item.발생년도 + "년"
				+ ", 경찰서 : " + item.경찰서 
				+ ", 강도 : " + item.강도 + "건"
				+ ", 절도 : " + item.절도 + "건"
				+ ", 살인 : " + item.살인 + "건"
				+"<br/>"
			]);
			$("#demo").html(str);
			// map형식의 연속된 자료는 배열처럼 저장된다. 이때 기본 구분자가 ','인데, 이것을 join()함수를 이용하여 공백으로 바꿔준다.
			
			// 화면에 출력된 자료들을 모두 DB에 저장시켜준다.
			// alert(res.data[0].강도);
			let query = "";
			for(let i=0; i<res.data.length; i++) {
				if(res.data[i].경찰서 != null) {
					query = {
							year : year,
							police : res.data[i].경찰서,
							robbery : res.data[i].강도,
							theft : res.data[i].절도,
							murder : res.data[i].살인,
							violence : res.data[i].폭력
					}
				}
				$.ajax({
					url: "${ctp}/study/restapi/saveCrimeData",
					type: "post",
					data: query,
					error: function(){
						alert("전송오류");
					}
				});
			}
			alert(year + "년도 자료가 DB에 저장되었습니다.");
		}
		
		// DB 자료 삭제
		function deleteCrimeDate(){
			let year = $("#year").val();
			$.ajax({
				url: "${ctp}/study/restapi/deleteCrimeData",
				type: "post",
				data: {year : year},
				success: function(res){
					if(res != 0) alert("삭제 완료");
					else alert("삭제 실패");
				},
				error: function(){
					alert("전송오류");
				}
			});
		}
		
		// DB에 있는 강력범죄 년도별 출력
		function listCrimeDate(){
			let year = $("#year").val();
			let str = "";
			$.ajax({
				url: "${ctp}/study/restapi/listCrimeDate",
				type: "post",
				data: {year : year},
				success: function(vos){
					str += "<table class='table table-hover'>";
					str += "<tr><th>No</th><th>경찰서</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th></tr>";
					for(let i=0; i<vos.length; i++) {
						if(vos[i].police != null) {
							str += "<tr>";
							str += "<td>"+(i+1)+"</td>";
							str += "<td>"+vos[i].police+"</td>";
							str += "<td>"+vos[i].robbery+"</td>";
							str += "<td>"+vos[i].theft+"</td>";
							str += "<td>"+vos[i].murder+"</td>";
							str += "<td>"+vos[i].violence+"</td>";
							str += "</tr>";
						}
					}
					str += "<tr><td colspan='6' class='m-0 p-0'></td></tr>";
					str += "</table>";
					$("#demo").html(str);
				},
				error: function(){
					alert("전송오류");
				}
			});
		}
		
		// 지역별 출력(년도 오름차/내림차순)
		function yearPoliceCheck(){
			let yearOrder = myform.yearOrder.value;
			let police = $("#police").val();
			
			let str = "";
			$.ajax({
				url: "${ctp}/study/restapi/yearPoliceList",
				type: "post",
				data: {yearOrder : yearOrder, police:police},
				success: function(vos){
					str += "<h3>"+police+"지역의 범죄현황</h3>";
					str += "<table class='table table-hover'>";
					str += "<tr><th>No</th><th>년도</th><th>경찰서</th><th>강도</th><th>절도</th><th>살인</th><th>폭력</th></tr>";
					for(let i=0; i<vos.length; i++) {
						if(vos[i].police != null) {
							str += "<tr>";
							str += "<td>"+(i+1)+"</td>";
							str += "<td>"+vos[i].year+"</td>";
							str += "<td>"+vos[i].police+"</td>";
							str += "<td>"+vos[i].robbery+"</td>";
							str += "<td>"+vos[i].theft+"</td>";
							str += "<td>"+vos[i].murder+"</td>";
							str += "<td>"+vos[i].violence+"</td>";
							str += "</tr>";
						}
					}
					str += "<tr><td colspan='7' class='m-0 p-0'></td></tr>";
					str += "</table>";
					$("#demo").html(str);
				},
				error: function(){
					alert("전송오류");
				}
			});
		}
		
		// 년도/지역별 범죄 건수
		function getYearStatsCrime(){
			let year = statsCrime.year.value;
			let police = statsCrime.police.value;
			let str = "";
			$.ajax({
				url: "${ctp}/study/restapi/getYearStatsCrime",
				type: "post",
				data: {year : year, police:police},
				success: function(vo){
					str += "<h3>"+police+"지역의 "+year+"년도 범죄현황</h3>";
					str += "<table class='table table-hover'>";
					str += "<tr><th>총 강도 건수</th><th>총 절도 건수</th><th>총 살인 건수</th><th>총 폭력 건수</th></tr>";
					str += "<tr>";
					str += "<td>"+vo.totRobbery+"</td>";
					str += "<td>"+vo.totTheft+"</td>";
					str += "<td>"+vo.totMurder+"</td>";
					str += "<td>"+vo.totViolence+"</td>";
					str += "</tr>";
					str += "<tr><td colspan='4' class='m-0 p-0'></td></tr>";
					str += "</table>";
					$("#demo2").html(str);
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
	<h2>경찰청 강력범죄 발생 현황 자료 리스트</h2>
	<hr/>
	<form name="myform" method="post">
		<div>
			<select name="year" id="year">
				<option value="">년도</option>
				<c:forEach var="i" begin="2015" end="2022">
					<option value="${i}" ${year==i ? "selected" : ""} >${i}년</option>
				</c:forEach>
			</select>
			<input type="button" value="강력범죄현황조회" onclick="getCrimeDate()" class="btn btn-primary" />
			<input type="button" value="강력범죄DB저장" onclick="saveCrimeDate()" class="btn btn-success" />
			<input type="button" value="강력범죄DB삭제" onclick="deleteCrimeDate()" class="btn btn-danger" />
			<input type="button" value="강력범죄DB출력" onclick="listCrimeDate()" class="btn btn-info" />
		</div>
		<div class="mt-3 mb-3">
			경찰서 지역명 : 
			<select name="police" id="police">
				<option>서울</option>
				<option>부산</option>
				<option>대구</option>
				<option>인천</option>
				<option>광주</option>
				<option>대전</option>
				<option>울산</option>
				<option>경기</option>
				<option>강원</option>
				<option>충북</option>
				<option>충남</option>
				<option>전북</option>
				<option>전남</option>
				<option>경북</option>
				<option>경남</option>
				<option>제주</option>
			</select> &nbsp;
			│ 정렬순서 : 
			<input type="radio" name="yearOrder" value="a" />오름차순
			<input type="radio" name="yearOrder" value="d" />내림차순 │
			<input type="button" value="년도/경찰서별출력" onclick="yearPoliceCheck()" class="btn btn-secondary" />
		</div>
	</form>
	<hr/>
	<div id="demo"></div>
	<hr/>
	<h3>범죄 분석 통계</h3>
	<form name="statsCrime" method="post">
		<select name="year">
			<option value="">년도</option>
			<c:forEach var="i" begin="2015" end="2022">
				<option value="${i}" ${year==i ? "selected" : ""} >${i}년</option>
			</c:forEach>
		</select> │
		지역명 :
		<select name="police">
			<option>서울</option>
			<option>부산</option>
			<option>대구</option>
			<option>인천</option>
			<option>광주</option>
			<option>대전</option>
			<option>울산</option>
			<option>경기</option>
			<option>강원</option>
			<option>충북</option>
			<option>충남</option>
			<option>전북</option>
			<option>전남</option>
			<option>경북</option>
			<option>경남</option>
			<option>제주</option>
		</select> │ 
		<input type="button" value="년도별 지역 통계" onclick="getYearStatsCrime()" class="btn btn-primary" />
	</form>
	<hr/>
	<div id="demo2"></div>
	<!-- 1. 년도/강도/살인/절도/폭력 통계 -->
	<!-- 2. 경찰서별 통계 : 년도/강도/살인/절도/폭력 -->
	<!-- 3. 범죄발생건수가 가장 작은 지역? -->
</div>
<p><br/></p>
<jsp:include page="/WEB-INF/views/include/footer.jsp" />
</body>
</html>