<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.lang.StringBuilder" %>

<%@ attribute name="page" required="true" rtexprvalue="true" type="Integer"%>
<%@ attribute name="total" required="true" rtexprvalue="true" type="Integer"%>
<%@ attribute name="search" required="false" rtexprvalue="true"%>
<%@ attribute name="max" required="false" rtexprvalue="true" type="Integer"%>

<%
    int count;

    StringBuilder sb = new StringBuilder("dashboard?");

    if (search != null && !search.trim().isEmpty()) {
        sb.append("search=").append(search).append("&amp;");
    }

    if (max > 0) {
        sb.append("max=").append(max).append("&amp;");
        count = 1 + total / max;
    } else {
        count = 1 + total / 10;
    }

    sb.append("page=");

    String before = sb.toString();
%>

<ul class="pagination">
	<li><a href="<%= before %>1">&lsaquo;&lsaquo;</a></li>
	<li><a href="<%= before %><%= (page > 1 ? page - 1 : 1) %>">&lsaquo;</a></li>

	<c:forEach var="i" begin="0" end="4">
		<li><a href="<%= before %>${page + i}">${page + i}</a></li>
	</c:forEach>

	<li><a href="<%= before %><%= (page < count ? page + 1 : count) %>">&rsaquo;</a></li>
	<li><a href="<%= before %><%= count %>">&rsaquo;&rsaquo;</a></li>
</ul>