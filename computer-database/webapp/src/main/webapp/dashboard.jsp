<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="homeTitle"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta charset="utf-8">
    <!-- Bootstrap -->
    <link href="<c:url value="resources/css/bootstrap.min.css"/>" rel="stylesheet"
          media="screen">
    <link href="<c:url value="resources/css/font-awesome.css"/>" rel="stylesheet"
          media="screen">
    <link href="<c:url value="resources/css/main.css"/>" rel="stylesheet"
          media="screen">
    <link href="<c:url value="resources/css/dashboard.css"/>" rel="stylesheet"
          media="screen">
</head>
<body>

<header class="navbar navbar-inverse navbar-fixed-top">

    <div class="container">
		<mytag:link value="Application - Computer Database"
					cssClass="navbar-brand"
					page="1"
					max="${pageComputer.maxPerPage}"/>
        <a class="navbar-right btn btn-default navbar-btn" href="logout"><spring:message code="logout"/> <i
                class="glyphicon glyphicon-log-out"></i></a>

        <div class="navbar-right panel-icon">
            <mytag:link value="<img class='flag-icon' src='resources/images/UK.jpeg'/>"
                        locale="en"
                        search="${search}"
                        page="1"
                        max="${pageComputer.maxPerPage}"/>

            <mytag:link value="<img class='flag-icon' src='resources/images/france.jpg'/>"
                        locale="fr"
                        search="${search}"
                        page="1"
                        max="${pageComputer.maxPerPage}"/>
        </div>
    </div>

</header>

<section id="main">
    <div class="container">
        <h1 id="homeTitle">
            <c:out value="${pageComputer.total}"/> <spring:message code="computerFound"/>
        </h1>
        <div id="actions" class="form-horizontal">
            <div class="pull-left">
                <form id="searchForm" action="#" method="GET" class="form-inline">
                    <input type="search" id="searchbox" name="search" class="form-control" placeholder="<spring:message
							code="search" />"/>
                    <input type="submit" id="searchsubmit"
                           value="<spring:message code="filter" />" class="btn btn-primary"/>
                </form>
            </div>
            <sec:authorize access="hasRole('ADMIN')">
                <div class="pull-right">

                    <a class="btn btn-success" id="addComputer" href="addComputer"><spring:message
                            code="addComputer"/></a> <a class="btn btn-default"
                                                        id="editComputer" href="#"
                                                        onclick="$.fn.toggleEditMode('<spring:message
                                                                code="showSelect"/>','<spring:message
                                                                code="hideSelect"/>');"><spring:message
                        code="showSelect"/></a>
                </div>
            </sec:authorize>
        </div>
    </div>

    <form id="deleteForm" action="#" method="POST">
        <input type="hidden" name="selection" value="">

        <input type="hidden"
               name="${_csrf.parameterName}"
               value="${_csrf.token}"/>
    </form>

    <div class="container" style="margin-top: 10px;">
        <table class="table table-striped table-bordered">
            <thead>
            <tr>
                <!-- Variable declarations for passing labels as parameters -->
                <!-- Table header for Computer Name -->
                <th class="editMode" style="width: 60px; height: 22px;">
                    <input type="checkbox" id="selectall"/>
                    <span style="vertical-align: top;">
                                    <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
	                            </span>
                </th>
                <th><spring:message code="computerName" var="computerName"/>
                    <spring:message code="introduced" var="introduced"/>
                    <spring:message code="discontinued" var="discontinued"/>
                    <spring:message code="company" var="company"/>
                    <mytag:link value="${computerName}"
								page="${pageComputer.number}"
								search="${search}"
								max="${pageComputer.maxPerPage}"
								column="name"
								order="${1 - order}"/></th>
				<th><mytag:link value="${introduced}"
								page="${pageComputer.number}"
								search="${search}"
								max="${pageComputer.maxPerPage}"
								column="introduced"
								order="${1 - order}"/></th>
				<th><mytag:link value="${discontinued}"
								page="${pageComputer.number}"
								search="${search}"
								max="${pageComputer.maxPerPage}"
								column="discontinued"
								order="${1 - order}"/></th>
				<th><mytag:link value="${company}"
								page="${pageComputer.number}"
								search="${search}"
								max="${pageComputer.maxPerPage}"
								column="companyName"
								order="${1 - order}"/></th>
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
                        <a href="editComputer?id=${obj.id}"><c:out value="${obj.name}"/></a>
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

        <mytag:pagination pageComputer="${pageComputer}" search="${search}"/>

			<div class="btn-group btn-group-sm pull-right" role="group">
				<mytag:link page="${pageComputer.number}"
							value="10"
							search="${search}"
							max="10"
							button="true"/>

				<mytag:link page="${pageComputer.number}"
							value="50"
							search="${search}"
							max="50"
							button="true"/>

				<mytag:link page="${pageComputer.number}"
							value="100"
							search="${search}"
							max="100"
							button="true"/>
    </div>
	</div>
</footer>
<script src="<c:url value="resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="resources/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="resources/js/dashboard.js"/>"></script>

</body>
</html>