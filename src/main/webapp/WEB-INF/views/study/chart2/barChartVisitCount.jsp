<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>barChartVisitCount.jsp</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
  <script type="text/javascript">
    google.charts.load('current', {'packages':['bar']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart() {
      var data = google.visualization.arrayToDataTable([
        ['${xTitle}', '${legend}'],
        <c:forEach var="i" begin="0" end="6" varStatus="st">
        	['${mids[i]}',${visitCounts[i]}],
        </c:forEach>
      ]);

      var options = {
        chart: {
          title: '${title}',
          subtitle: '${subTitle}',
        }
      };

      var chart = new google.charts.Bar(document.getElementById('columnchart_material'));

      chart.draw(data, google.charts.Bar.convertOptions(options));
    }
  </script>
</head>
<body>
<p><br/></p>
<div class="container">
	<div id="columnchart_material" style="width: 800px; height: 500px;"></div>
</div>
<p><br/></p>
</body>
</html>