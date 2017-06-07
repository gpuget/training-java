<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
    <title>Computer Database</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet"
          media="screen">
    <link href="<c:url value="/resources/css/font-awesome.css"/>" rel="stylesheet"
          media="screen">
    <link href="<c:url value="/resources/css/main.css"/>" rel="stylesheet"
          media="screen">
</head>
<body>
<header class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <a class="navbar-brand" href="../dashboard"> Application - Computer Database </a>
    </div>
</header>

<section id="main">
    <div class="container">
        <div class="alert alert-danger">
            <spring:message code="404"/>
            <br/>
            <!-- stacktrace -->
            ${pageContext.exception.message}
        </div>
    </div>
</section>

<script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
<script src="<c:url value="/resources/js/bootstrap.min.js"/>"></script>


</body>
</html>