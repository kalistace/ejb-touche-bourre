<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Rejoindre une partie</title>
<link rel="stylesheet" type="text/css"  href="./css/styles.css"/>
</head>
<body>
<div class = "login">

<p class = "edtUser">Selectionnez une soirée à rejoindre:</p>
<table border=1>
		<c:forEach var="soiree" items="${soireesEnCours}">
			<tr>
				<td colspan="2">${soiree.nom}</td>
			</tr>
			<tr>
						<td class="biggerTD">
							<c:url var="urlSoiree" value="rejoindre.html"/>
							<form action="${urlSoiree}" method="post">
								<input type="text" name="nomSoiree" value="${soiree.nom}"/>
								<input class = "btnSmall" type = "submit" value = "Rejoindre"/>
							</form>
						</td>
			      		<td class="joinable">${soiree.nbrFetardConnecte}/2</td>
			</tr>
		</c:forEach>
</table>
</div>

</body>
</html>