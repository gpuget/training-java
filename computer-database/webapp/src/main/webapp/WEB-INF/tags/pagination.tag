<%@ taglib tagdir="/WEB-INF/tags" prefix="mytag"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ tag import="java.lang.StringBuilder"%>

<%@ attribute name="pageComputer" required="true" rtexprvalue="true"
	type="com.excilys.cdb.model.Page"%>
<%@ attribute name="search" required="false" rtexprvalue="true"%>
<%@ attribute name="column" required="false" rtexprvalue="true"%>
<%@ attribute name="order" required="false" rtexprvalue="true"%>

<%
    int number = pageComputer.getNumber();
    int maxPerPage = pageComputer.getMaxPerPage();
    int last = pageComputer.getLastNumber();

    StringBuilder sb = new StringBuilder("dashboard?");

    if (search != null && !search.trim().isEmpty()) {
        sb.append("search=").append(search).append("&amp;");
    }
    
    if (column != null && !column.trim().isEmpty()) {
        sb.append("column=").append(column).append("&amp;");
        
        if (order == null || order.trim().isEmpty()) {
			order = "1";
        }
        
        sb.append("order=").append(order).append("&amp;");
    }

    sb.append("max=").append(maxPerPage).append("&amp;");
    sb.append("page=");

    String before = sb.toString();
%>

<ul class="pagination">
	<c:if test="${pageComputer.number >1}">
	<li><a href="<%=before%>1">&lsaquo;&lsaquo;</a></li>
	<li><a href="<%=before%><%=number - 1%>">&lsaquo;</a></li>
	</c:if>

	<c:forEach var="i" begin="0" end="3">
		<c:if test="${pageComputer.number + i <= pageComputer.lastNumber}">
			<li><a href="<%= before %>${pageComputer.number + i}">${pageComputer.number + i}</a></li>
		</c:if>
	</c:forEach>

	<c:if test="${pageComputer.number < pageComputer.lastNumber}">
	<li><a
		href="<%=before%><%=(number < last ? number + 1 : last)%>">&rsaquo;</a></li>
	<li><a href="<%=before%><%=last%>">&rsaquo;&rsaquo;</a></li>
	</c:if>
</ul>