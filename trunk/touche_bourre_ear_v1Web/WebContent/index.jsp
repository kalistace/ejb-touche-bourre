<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css"  href="./css/styles.css"/>
<title>Touchée - bourré - Bienvenue !</title>
<script type="text/javascript" src="js/login.js"></script>
</head>
<body>
	<div id="main">

<div class = "login">
<p class = "edtUser">Entrez votre nom:</p>
<c:url var="urlAccueil" value="accueil.html"/>
<form action="${urlAccueil}" method="post" id="formLogin">
<p class = "edtUser"> <input class = "loginput" type = "text" name = "pseudo" id="pseudo"/> </p>
<p><input class = "btnSmall" type = "submit" value = "Entrer"/> </p>
</form>

<p><a href="mailto:${initParam.emailCoordProj}">Nous contacter</a><p>
</div>

</div>

</body>
</html>