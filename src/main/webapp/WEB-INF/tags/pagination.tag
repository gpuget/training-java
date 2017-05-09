<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.util.Map" %>
<%@ tag import="javax.servlet.http.HttpServletRequest" %>

<%@ attribute name="page" required="true" rtexprvalue="true"%>
<%@ attribute name="request" required="true" rtexprvalue="true" type="javax.servlet.http.HttpServletRequest"%>

<%
	Map<String, String[]> param = request.getParameterMap();
	String before ="?";
	if (param != null && !param.isEmpty()) {
	    for(Map.Entry<String, String[]> p : param.entrySet()) {
	        if (!p.getKey().equals("page")) {
	        	before = before + p.getKey() + "&#61;" + p.getValue()[0]+ "&amp;";
	        }
	    }
	}
%>

<ul class="pagination">
	<li><a href="<%= before %>page&#61;${page > 1 ? page - 1 : 1}">&lsaquo;</a></li>
	
	<c:forEach var="i" begin="0" end="4">
		<li><a href="<%= before %>page&#61;${page + i}">${page + i}</a></li>
	</c:forEach>
	
	<li><a href="<%= before %>page&#61;${page + 1}">&rsaquo;</a></li>
</ul>