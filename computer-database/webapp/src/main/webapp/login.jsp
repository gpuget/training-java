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
		<div class = "container">
			<c:if test="${param.logout != null}">
				<div class="alert alert-success">
				  <strong>Success :</strong> ${logout}
				</div>
			</c:if>			
			
			<c:if test="${param.error != null}">
				<div class="alert alert-danger">
				  <strong>Error :</strong> ${error}
				</div>
			</c:if>
		
			<form action="login" method="post">
				<h2 class="form-signin-heading">Computer database</h2>
				<br>
				<input type="text" class="form-control" id="username" name="username" placeholder="Username" />
				<input type="password" class="form-control" id="password" name="password" placeholder="Password" />
					
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<button class="btn btn-lg btn-primary btn-block" name="Submit" value="Login" type="Submit">Login</button>
			</form>
		</div>
	</body>
</html>