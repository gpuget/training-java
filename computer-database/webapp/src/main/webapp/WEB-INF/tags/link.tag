<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.lang.StringBuilder" %>

<%@ attribute name="target" required="true" rtexprvalue="true"%>
<%@ attribute name="value" required="true" rtexprvalue="true"%>
<%@ attribute name="page" required="true" rtexprvalue="true"%>
<%@ attribute name="search" required="false" rtexprvalue="true"%>
<%@ attribute name="max" required="false" rtexprvalue="true"%>
<%@ attribute name="button" required="false" type="java.lang.Boolean"%>

<%
	StringBuilder sb = new StringBuilder("?");
	sb.append(target);
	
	if (search != null) {
		sb.append("search&#61").append(search).append("&amp;");
	}
	
	if (max != null) {
		sb.append("max&#61").append(max).append("&amp;");
	}
	

	sb.append("page&#61").append(page);
	
	String url = sb.toString();
%>

<c:choose>
	<c:when test="${button != null && button.equals(true)}">
		<a class="btn btn-default" href="<%= url %>">${value}</a>
	</c:when>
	
	<c:otherwise>
		<a href="<%= url %>">${value}</a>
	</c:otherwise>
</c:choose>