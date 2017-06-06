<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
	<head>
		<title>Computer Database</title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<!-- Bootstrap -->
		<link href="resources/css/bootstrap.min.css" rel="stylesheet" media="screen">
		<link href="resources/css/font-awesome.css" rel="stylesheet" media="screen">
		<link href="resources/css/main.css" rel="stylesheet" media="screen">
	</head>
	<body>
	    <header class="navbar navbar-inverse navbar-fixed-top">
	        <div class="container">
				<mytag:link value="Application - Computer Database" 
							cssClass="navbar-brand"
							page="1"/>
	            <a class="navbar-right btn btn-default navbar-btn" href="logout">Logout <i class="glyphicon glyphicon-log-out"></i></a>
	            <a class="navbar-right btn btn-default navbar-btn" href="logout">Logout <i class="glyphicon glyphicon-log-out"></i></a>
	        </div>
	    </header>
	    <section id="main">
	        <div class="container">
	            <div class="row">
	                <div class="col-xs-8 col-xs-offset-2 box">
	                    <div class="label label-default pull-right">
	                        id: <c:out value="${computer.id}"></c:out>
	                    </div>
	                    <h1>Edit Computer</h1>

						<c:if test="${errors != null}">						
							<div class="alert alert-danger">
								<c:forEach items="${errors}" var="e">
									<strong>Error :</strong><c:out value="${e.defaultMessage}" />
								</c:forEach>								
							</div>
						</c:if>
	
	                    <form action="editComputer" method="POST">
	                        <input type="hidden" value="${computer.id}" id="id" name="id"/>
	                        <fieldset>
	                            <div class="form-group">
	                                <label for="computerName">Computer name</label>
	                                <input type="text" class="form-control" id="name" name="name" placeholder="Computer name" value="${computer.name}">
	                            </div>
	                            <div class="form-group">
	                                <label for="introduced">Introduced date</label>
	                                <input type="date" class="form-control" id="introduced" name="introduced" placeholder="Introduced date" value="${computer.introduced}">
	                            </div>
	                            <div class="form-group">
	                                <label for="discontinued">Discontinued date</label>
	                                <input type="date" class="form-control" id="discontinued" name="discontinued" placeholder="Discontinued date" value="${computer.discontinued}">
	                            </div>
	                            <div class="form-group">
	                                <label for="companyId">Company</label>
	                                <select class="form-control" id="companyId" name="companyId" >
                                        <c:forEach items="${companies}" var="com">
                                            <option value="${com.id}"><c:out value="${com.name}"></c:out></option>
                                        </c:forEach>
	                                </select>
	                            </div>            
	                        </fieldset>
	                        <div class="actions pull-right">
	                            <input type="submit" value="Edit" class="btn btn-primary">
	                            or
	                            <a href="dashboard" class="btn btn-default">Cancel</a>
	                            
	                            <input 	type="hidden"
										name="${_csrf.parameterName}"
										value="${_csrf.token}"/>
	                        </div>
	                    </form>
	                </div>
	            </div>
	        </div>
	    </section>
	</body>
</html>