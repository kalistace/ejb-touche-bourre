<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bons souvenirs de copains</title>
<link rel="stylesheet" type="text/css"  href="./css/styles.css"/>
</head>
<body>
	<h1>Selectionnez une soirée:</h1>
	<c:forEach var="soiree" items="${journaux}">
		<table border=1>
			<tr><td colspan="2">${soiree.nom}</td></tr>
			<tr>
				<c:choose>
					<c:when test="${empty soiree.gagnant}">
						<td class="jaune">
							Il n'y a pas de vainqueur pour cette soirée.
						</td>
					</c:when>
					<c:when test="${not empty soiree.gagnant && soiree.gagnant.fetard.pseudo != myPseudo}">
						<td class="rouge">
							Vous avez perdu cette soirée contre <c:out value="${soiree.gagnant.fetard.pseudo}"/>
						</td>
					</c:when>
					<c:when test="${empty soiree.gagnant && soiree.gagnant.fetard.pseudo == myPseudo}">
						<td class="vert">
							Vous avez gagné cette soirée contre <c:out value="${soiree.gagnant.fetard.pseudo}"/>
						</td>
					</c:when>
				</c:choose>
				<td>
					<c:url var="urlJournal" value="journal.html"/>
					<form action="${urlJournal}" method="post">
					<c:set var="soiree" value="${soiree}" scope="request"/>
					<input class = "btnSmall" type = "submit" value = "Journal"/>
					</form>
				</td>
			</tr>
		</table>
		<div>
			<c:forEach var="ligne" items="${mapTournees[soiree.id]}">
				<c:out value="${ligne}"/>
			</c:forEach>
		</div>
	</c:forEach>	
</body>
</html>