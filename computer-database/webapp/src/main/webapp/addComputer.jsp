<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="mytag" tagdir="/WEB-INF/tags" %>

<!DOCTYPE html>
<html>
<head>
    <title><spring:message code="homeTitle"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="resources/css/bootstrap.min.css" type="text/css" rel="stylesheet" media="screen">
    <link href="resources/css/font-awesome.css" type="text/css" rel="stylesheet" media="screen">
    <link href="resources/css/main.css" type="text/css" rel="stylesheet" media="screen">
    <link href="resources/css/addComputer.css" type="text/css" rel="stylesheet" media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <mytag:link value="Application - Computer Database"
                    cssClass="navbar-brand"
                    page="1"/>
        <a class="navbar-right btn btn-default navbar-btn" href="logout"><spring:message code="logout"/> <i
                class="glyphicon glyphicon-log-out"></i></a>
        <div class="navbar-right panel-icon">
            <a href="?mylocale=en"> <img class="flag-icon" src="resources/images/UK.jpeg"/> </a>
            <a href="?mylocale=fr"> <img class="flag-icon" src="resources/images/france.jpg"/> </a>
        </div>
    </div>
</header>
<sec:authorize access="hasRole('ADMIN')">
    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><spring:message code="addComputer"/></h1>

                    <c:if test="${errors != null}">
                        <div class="alert alert-danger">
                            <c:forEach items="${errors}" var="e">
                                <strong>Error :</strong><c:out value="${e.defaultMessage}"/>
                            </c:forEach>
                        </div>
                    </c:if>

                    <form action="addComputer" method="POST">
                        <fieldset>

                            <div class="form-group">
                                <label for="computerName"><spring:message code="computerName"/></label>
                                <input type="text" class="form-control" id="name" name="name"
                                       placeholder="Computer name">
                            </div>
                            <div class="form-group">
                                <label for="introduced"><spring:message code="introduced"/></label>
                                <input type="date" class="form-control" id="introduced" name="introduced"
                                       placeholder="Introduced date">
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><spring:message code="discontinued"/></label>
                                <input type="date" class="form-control" id="discontinued" name="discontinued"
                                       placeholder="Discontinued date">
                            </div>
                            <div class="form-group">
                                <label for="companyId"><spring:message code="company"/></label>
                                <select class="form-control" id="companyId" name="companyId">
                                    <c:forEach items="${companies}" var="com">
                                        <option value="${com.id}"><c:out value="${com.name}"/></option>
                                    </c:forEach>
                                </select>
                            </div>
                        </fieldset>
                        <div class="actions pull-right">
                            <input type="submit" value="<spring:message code="add"/>" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><spring:message code="cancel"/></a>

                            <input type="hidden"
                                   name="${_csrf.parameterName}"
                                   value="${_csrf.token}"/>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</sec:authorize>
</body>
</html>