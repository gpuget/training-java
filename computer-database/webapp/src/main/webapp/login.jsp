<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<meta charset="utf-8">
		<!-- Bootstrap -->
		<link href="resources/css/bootstrap.min.css" type="text/css" rel="stylesheet" media="screen">
		<link href="resources/css/font-awesome.css" type="text/css" rel="stylesheet" media="screen">
		<link href="resources/css/main.css" type="text/css" rel="stylesheet" media="screen">
	</head>
	<body>
		<form name="f" action="login" method="POST">
			<c:if test="${param.error != null}">        
				<p>
					Invalid username and password.
				</p>
			</c:if>
			<c:if test="${param.logout != null}">
				<p>
					You have been logged out.
				</p>
			</c:if>
			<p>
				<label for="username">Username</label>
				<input type="text" id="username" name="username"/>
			</p>
			<p>
				<label for="password">Password</label>
				<input type="password" id="password" name="password"/>
			</p>
			<input type="hidden"      
				name="${_csrf.parameterName}"
				value="${_csrf.token}"/>
			<button type="submit" class="btn">Log in</button>
		</form>
	</body>
</html>