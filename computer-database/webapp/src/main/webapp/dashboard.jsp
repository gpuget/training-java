<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

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
	    <header class="navbar navbar-inverse navbar-fixed-top">
	        <div class="container">
	            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
	        </div>
	    </header>
	
	    <section id="main">
	        <div class="container">
	            <h1 id="homeTitle">
	               <c:out value="${count}"/> Computers found
	            </h1>
	            <div id="actions" class="form-horizontal">
	                <div class="pull-left">
	                    <form id="searchForm" action="#" method="GET" class="form-inline">
	                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name"/>
	                        <input type="submit" id="searchsubmit" value="Filter by name" class="btn btn-primary" />
	                    </form>
	                </div>
	                <div class="pull-right">
	                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
	                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
	                </div>
	            </div>
	        </div>
	
	        <form id="deleteForm" action="#" method="POST">
	            <input type="hidden" name="selection" value="">
	        </form>
	
	        <div class="container" style="margin-top: 10px;">
	            <table class="table table-striped table-bordered">
	                <thead>
	                    <tr>
	                        <!-- Variable declarations for passing labels as parameters -->
	                        <!-- Table header for Computer Name -->
	                        <th class="editMode" style="width: 60px; height: 22px;">
	                            <input type="checkbox" id="selectall" /> 
	                            <span style="vertical-align: top;">
                                    <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
	                            </span>
	                        </th>                 
	                        <th>Computer name</th>
	                        <th>Introduced date</th>
	                        <th>Discontinued date</th>
	                        <th>Company</th>
	                    </tr>
	                </thead>
	                <!-- Browse attribute computers -->
	                <tbody id="results">                    
	                    <c:forEach items="${pageComputer.objects}" var="obj">
		                    <tr>
		                        <td class="editMode">
		                            <input type="checkbox" name="cb" class="cb" value="${obj.id}">
		                        </td>
		                        <td>
		                            <a href="editComputer?id=${obj.id}">${obj.name}</a>
		                        </td>
		                        <td><c:out value="${obj.introduced}"/></td>
		                        <td><c:out value="${obj.discontinued}"/></td>
		                        <td><c:out value="${obj.companyName}"/></td>
		                    </tr>                            
	                    </c:forEach>              
	                </tbody>
	            </table>
	        </div>
	    </section>
	
	    <footer class="navbar-fixed-bottom">
	        <div class="container text-center">
		        
		        <mytag:pagination request="${pageContext.request}" page="${pageComputer.number}"></mytag:pagination>
		
		        <div class="btn-group btn-group-sm pull-right" role="group">
		        	<mytag:link page="${pageComputer.number}"
		        				search="${search}"
		        				max="10"
		        				value="10" 
		        				target="dashboard"/>
		        	<mytag:link page="${pageComputer.number}"
		        				search="${search}"
		        				max="50"
		        				value="50" 
		        				target="dashboard"/>
		        	<mytag:link page="${pageComputer.number}"
		        				search="${search}"
		        				max="100"
		        				value="100" 
		        				target="dashboard"/>
		        </div>
		    </div>
	    </footer>
	<script src="resources/js/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/js/dashboard.js"></script>
	
	</body>
</html>