<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.lang.StringBuilder"%>

<%@ attribute name="page" required="true" rtexprvalue="true"%>
<%@ attribute name="value" required="true" rtexprvalue="true"%>
<%@ attribute name="cssClass" required="false" rtexprvalue="true"%>
<%@ attribute name="search" required="false" rtexprvalue="true"%>
<%@ attribute name="max" required="false" rtexprvalue="true"%>
<%@ attribute name="column" required="false" rtexprvalue="true"%>
<%@ attribute name="order" required="false" rtexprvalue="true"%>
<%@ attribute name="button" required="false" type="java.lang.Boolean"%>

<%
    StringBuilder sb = new StringBuilder("dashboard?");

    if (search != null && !search.trim().isEmpty()) {
        sb.append("search=").append(search).append("&amp;");
    }
    
    if (max != null && !max.trim().isEmpty()) {
        sb.append("max=").append(max).append("&amp;");
    }
    
    if (column != null && !column.trim().isEmpty()) {
        sb.append("column=").append(column).append("&amp;");
        
        if (order == null || order.trim().isEmpty()) {
			order = "1";
        }
        
        sb.append("order=").append(order).append("&amp;");
    }
    
    if (cssClass == null) {
		cssClass = "";
    }

    sb.append("page=").append(page);

    String url = sb.toString();
%>

<c:choose>
	<c:when test="${button != null && button.equals(true)}">
		<a class="btn btn-default <%= cssClass %>" href="<%=url%>"><%= value %></a>
	</c:when>

	<c:otherwise>
		<a class="<%= cssClass %>" href="<%=url%>"><%= value %></a>
	</c:otherwise>
</c:choose>