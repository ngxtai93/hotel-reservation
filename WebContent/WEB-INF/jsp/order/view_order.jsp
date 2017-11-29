<%@include file="../partials/header.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="team6.entity.Order" %>
<%
	List<Order> listOrder = (List<Order>) request.getAttribute("list-order");
	int orderCount = 0;
	if(listOrder != null) {
	    orderCount = listOrder.size();
	}
%>

 <br>
 <% if(orderCount == 0) { %>
     <p>You have no order available.</p>
 <% }
 else { %>
	<p>You have <%=listOrder.size()%> orders placed.</p>
	<br>
<% } %>

<%@include file="../partials/footer.jsp" %>