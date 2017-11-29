<%@include file="../partials/header.jsp" %>
<%@ page import="java.time.LocalTime" %>
<%
	LocalTime checkInTime = (LocalTime) request.getAttribute("check-in-time");
	LocalTime checkOutTime = (LocalTime) request.getAttribute("check-out-time");
%>
	<p>Your order has been placed successfully. Order receipt has been sent to your email address.</p>
	<br>
	<p>NOTE: The check-in time is at <%= checkInTime.toString() %>, and the check-out time is at <%= checkOutTime.toString() %>. </p>

<%@include file="../partials/footer.jsp" %>