<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctp" value="${pageContext.request.contextPath}"/> 
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>wordcloudShow</title>
	<jsp:include page="/WEB-INF/views/include/bs4.jsp" />
</head>
<body>
<p><br/></p>
<div class="container">
	<img src="${ctp}/study/${imagePath}" width="1000px"/>
</div>
<p><br/></p>
</body>
</html>