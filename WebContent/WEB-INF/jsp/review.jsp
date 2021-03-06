<%@include file="./partials/header.jsp" %>
	<%-- TODO: frontend --%>
<%@ page import = "java.util.*, java.time.format.DateTimeFormatter, java.time.LocalDate, java.text.NumberFormat" %>
<%@ page import = "team6.entity.Order" %>
<%
	List<Order> listOrder = (List<Order>) request.getAttribute("list-order");
	int orderCount = 0;
	if(listOrder != null) {
	    orderCount = listOrder.size();
	}
	DateTimeFormatter df = DateTimeFormatter.ofPattern("MMMM dd, uuuu");
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMMM dd, uuuu hh:mm a");
	NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
%>
<br>
 <% if(orderCount == 0) { %>
     <p>You have no order for review.</p>
 <% }
 else { %>
	<script src="<%= rootPath %>/resources/js/review/review.js" type="text/javascript"></script>
	<form id="choose-order">
		<table>
			<tr>
				<td></td>
				<td>Order no.</td>
				<td>Date ordered</td>
				<td>Room</td>
				<td>Hotel</td>
				<td>Reservation Period</td>
				<td>Price</td>
			</tr>
			<% int orderNo = 0;
			for(Order o: listOrder) {
				orderNo++; %>
				<tr>
					<td><input type="radio" required name="orderId" value="<%= o.getSeqNo() %>"></td>
					<td><p><%= orderNo %>.</p></td>
					<td><p><%= o.getOrderDate().format(df) %></p></td>
					<td><p><%= o.getRoomType().getName() %></p></td>
					<td><p><%= o.getHotel().getName() %></p></td>
					<td><p><%= o.getCheckInDateTime().toLocalDate().format(df) %> - <%= o.getCheckOutDateTime().toLocalDate().format(df) %></p></td>
					<td><p><%= currencyFormatter.format(o.getPrice()) %></p></td>
				</tr>	
			<% } %>
		</table>
	</form>
	<div id="review-div"></div>
	<div id="review-error"></div>
<% } %>	
<%@include file="./partials/footer.jsp" %>