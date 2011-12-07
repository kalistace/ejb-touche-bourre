<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"  href="./css/styles.css"/>
<title>Touchée - bourré - Bienvenue !</title>
</head>
<body>
	<div id="main">

<div class = "login">

<p class = "edtUser">Bienvenue, ${pseudo}! </p>
<c:url var="urlSoiree" value="organiserSoiree.jsp"/>
<form action="${urlSoiree}" method="post">
<p><input class = "btnMedium" type = "submit" value = "Organiser une soirée"/> </p>
</form>
<c:url var="urlListerSoiree" value="listerSoirees.html"/> <!-- servlet -->
<form action="${urlListerSoiree}" method="post">
<p><input class = "btnMedium" type = "submit" value = "Rejoindre une soirée"/> </p>
</form>
<c:url var="urlListerJournal" value="listerJournaux.html"/> <!-- servlet -->
<form action="${urlListerJournal}" method="post">
<p><input class = "btnLarge" type = "submit" value = "Bons souvenirs de copains"/> </p>
</form>

</div>

</div>

</body>
</html>