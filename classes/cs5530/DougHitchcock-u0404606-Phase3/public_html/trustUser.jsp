<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doug E Fresh Bookstore!</title>
</head>
<body>
	<%

//	public String placeOrder(String ISBN, String quantity) throws SQLException


String trustedLoginAttribute = request.getParameter("trustedLoginAttribute");
String trustedOrNotAttribute = request.getParameter("trustedOrNotAttribute");

if( trustedLoginAttribute == null || trustedOrNotAttribute == null){
%>
        <form name="trustUser" method=post action="trustUser.jsp">
        Login of user to trust or not trust:<input type=text name="trustedLoginAttribute" length=16><BR>
        Trusted or Not Trusted(1 for trusted, 0 for not trusted): <input type=text name="trustedOrNotAttribute" length=16><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User user = (cs5530.User) session.getAttribute("user");
	
%>
<%=user.trustUser(trustedLoginAttribute, trustedOrNotAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><a href="trustUser.jsp">Trust another user?</a><BR>
	<a href="mainUI.jsp"> Return to Main Menu</a>
</body>

</html>