<%@include file="./partials/header.jsp" %>
<%@ page import = "team6.entity.Hotel" %>
<%@ page import = "java.util.Map, java.time.LocalDate, java.time.format.DateTimeFormatter" %>

<%
	Map<Hotel, Boolean> mapHotel = (Map<Hotel, Boolean>) request.getAttribute("search-result");
	String location = (String) request.getAttribute("query-location");
	LocalDate checkInDate = (LocalDate) request.getAttribute("query-check-in");
	LocalDate checkOutDate = (LocalDate) request.getAttribute("query-check-out");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d MMM, yyyy");
%>
<%-- TODO: frontend --%>
<span>You searched for: <b><%= location %></b>: <b><%= checkInDate.format(dtf) %></b> - <b><%= checkOutDate.format(dtf) %></b></span>

<% if(mapHotel == null) { %>
	<br>
	<span style="color:red">No hotel found in this area.</span>
<% }
else { %>
	<% for(Map.Entry<Hotel, Boolean> entry: mapHotel.entrySet()) {
		request.setAttribute("hotel", entry.getKey());
		request.setAttribute("is-available", entry.getValue());
		request.setAttribute("query-check-in", checkInDate);
		request.setAttribute("query-check-out", checkOutDate);
	%>
		<jsp:include page="/WEB-INF/jsp/partials/hotel_description.jsp"/>
	<% }
} %>
<%@include file="./partials/footer.jsp" %>