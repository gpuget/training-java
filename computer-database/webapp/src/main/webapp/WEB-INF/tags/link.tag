<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.lang.StringBuilder" %>

<%@ attribute name="page" required="true" rtexprvalue="true"%>
<%@ attribute name="search" required="false" rtexprvalue="true"%>
<%@ attribute name="button" required="false" type="java.lang.Boolean"%>

<%
	StringBuilder sb = new StringBuilder("dashboard?");
	
	if (search != null && !search.trim().isEmpty()) {
		sb.append("search=").append(search).append("&amp;");
	}	

	sb.append("page=").append(page).append("&amp;");
	
	String url = sb.toString();
%>

<c:choose>
	<c:when test="${button != null && button.equals(true)}">
		<a class="btn btn-default" href="<%= url %>max=10">10</a>		
		<a class="btn btn-default" href="<%= url %>max=50">50</a>
		<a class="btn btn-default" href="<%= url %>max=100">100</a>
	</c:when>
	
	<c:otherwise>
		<a href="<%= url %>max=10">10</a>		
		<a href="<%= url %>max=50">50</a>
		<a href="<%= url %>max=100">100</a>
	</c:otherwise>
</c:choose>