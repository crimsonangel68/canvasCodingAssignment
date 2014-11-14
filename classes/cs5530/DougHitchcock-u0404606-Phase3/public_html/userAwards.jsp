<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doug E Fresh Bookstore!</title>
</head>
<body>
	<%

//	public String placeOrder(String ISBN, String quantity) throws SQLException


String usefulAttribute = request.getParameter("usefulAttribute");
String trustedAttribute = request.getParameter("trustedAttribute");

if( usefulAttribute == null || trustedAttribute == null){
%>
        <form name="userAwards" method=post action="userAwards.jsp">
        Enter a number for number of Useful Users:<input type=text name="usefulAttribute" length=16><BR>
        Enter a number for number of Trusted Users: <input type=text name="trustedAttribute" length=16><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User user = (cs5530.User) session.getAttribute("user");
	cs5530.Feedback feedback = new Feedback(connector, connector.stmt, user);
	
%>
<%=user.getUsefulUsers(usefulAttribute)%>
<%=user.getTrustGivenN(trustedAttribute)%>
<%
connector.closeConnection();
}
%>

	<BR><BR><a href="mainUI.jsp"> Return to Main Menu</a>
</body>

</html>