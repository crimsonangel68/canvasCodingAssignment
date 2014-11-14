<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doug E Fresh Bookstore!</title>
</head>
<body>
	<%

//	public String placeOrder(String ISBN, String quantity) throws SQLException


String isbnAttribute = request.getParameter("isbnAttribute");

if( isbnAttribute == null){
%>
        <form name="getFeedback" method=post action="getFeedback.jsp">
        ISBN:<input type=text name="isbnAttribute" length=16><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.Feedback feedback = new Feedback(connector, connector.stmt);
	
%>
<%=feedback.getFeedback(isbnAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><a href="getFeedback.jsp">View feedback for another book?</a><BR>
	<a href="rateFeedback.jsp">Rate usefulness of a feedback, given the feedback ID</a><BR>
	<a href="mainUI.jsp"> Return to Main Menu</a>
</body>

</html>