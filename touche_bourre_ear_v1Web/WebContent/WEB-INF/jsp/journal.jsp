<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<!-- ${joueurCourant} == pseudoPremierJoueur au début -->
	<!-- == ${joueurNext} ==pseudoDeuxiemeJoueur au début -->
	
	<c:set var="tmp" value="${joueurCourant}"
    scope="request" />
    <c:set var="joueurNext" value="${tmp}" scope="request" />
	
	<c:forEach var="tournee" items="${tournees}">
	    <tr><td colspan="2">${soiree.nom}</td></tr>
		<tr><td>//todo afficher vainqueur avec fond vert</td>
		<td><c:url var="urlJournal" value="journal.html"/>
		<form action="${urlJournal}" method="post">
		<input type="hidden" value="${soiree.id}"/>
		<input class = "btnSmall" type = "submit" value = "Journal"/>
		</form></td></tr>
	</c:forEach>
</body>
</html>