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
String nAttribute = request.getParameter("nAttribute");

if( isbnAttribute == null || nAttribute == null){
%>
        <form name="usefulNFeedbacks" method=post action="usefulNFeedbacks.jsp">
        ISBN:<input type=text name="isbnAttribute" length=16><BR>
        Enter a number to retrieve N useful feedbacks: <input type=text name="nAttribute" length=16><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User user = (cs5530.User) session.getAttribute("user");
	cs5530.Feedback feedback = new Feedback(connector, connector.stmt, user);
	
%>
<%=feedback.getUsefulFeedbacksForN(isbnAttribute, nAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><a href="getFeedback.jsp">View feedback for another book?</a><BR>
	<a href="mainUI.jsp"> Return to Main Menu</a>
</body>

</html>