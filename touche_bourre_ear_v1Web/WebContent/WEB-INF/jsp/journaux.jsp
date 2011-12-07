<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bons souvenirs de copains</title>
</head>
<body>
	<h1>Selectionnez une soirée:</h1>
	<table border=1>
		<c:forEach var="soiree" items="${journaux}">
			<tr><td colspan="2">${soiree.nom}</td></tr>
			<tr><td>//todo afficher vainqueur avec fond vert</td>
			<td><c:url var="urlJournal" value="journal.html"/>
			<form action="${urlJournal}" method="post">
			<input class = "btnSmall" type = "submit" value = "Journal"/>
			</form></td></tr>
		</c:forEach>
	</table>
</body>
</html>