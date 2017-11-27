<%@include file="../partials/header.jsp" %>
	<%-- TODO: frontend --%>
        <br>
        <p><a href="<%= rootPath %>/account/createAccount">Create account</a></p>
        <br>
        <h2>Hotel management</h2>
        <br>
        <p><a href="<%= rootPath %>/hotel/add"> Add hotel </a></p>
        <p><a href="<%= rootPath %>/hotel/update"> Update hotel </a></p>
        <p><a href="<%= rootPath %>/hotel/delete"> Delete hotel </a></p>
        <br>
        <h2>Room management</h2>
        <p><a href="<%= rootPath %>/room/add"> Add room type</a></p>
        <p><a href="<%= rootPath %>/room/update"> Update room type</a></p>
        <p><a href="<%= rootPath %>/room/delete"> Delete room type</a></p>

<%@include file="../partials/footer.jsp" %>