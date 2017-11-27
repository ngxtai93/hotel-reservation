<%@ page import = "team6.entity.Hotel" %>

<%
	String rootPath = request.getContextPath();
	Hotel hotel = (Hotel) request.getAttribute("hotel");
	Boolean isAvail = (Boolean) request.getAttribute("is-available");
	
%>

<div class="hotel-description">
  <div class="col-1">
    <img class="hotel-images" src="<%= rootPath %>/resources/images/upload/hotel/<%= hotel.getSeqNo() %>/<%= hotel.getListImage().get(0) %>">
  </div>
   <div class="col-2">
    <span>
    	<a href="<%= rootPath %>/hotel/<%= hotel.getSeqNo() %>">
    		<%= hotel.getName() %>
   		</a>
 	</span>
    <p><%= hotel.getDescription() %></p>
  </div>
  <div class="col-3">
  	<% if(isAvail) { %>
  	<form method="get" action="<%= rootPath %>/reserve">
  		<input type="hidden" name="action" value="chooseRoom">
  		<input type="hidden" name="hotel" value="<%= hotel.getSeqNo() %>">
  		<button type="submit">Choose</button>
  	</form>
	<% }
  	else { %>
  		<span style="color:red">Sold out for these dates</span>
	<% } %>
  </div>
</div>