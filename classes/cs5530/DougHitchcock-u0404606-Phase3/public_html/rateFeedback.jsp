<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doug E Fresh Bookstore!</title>
</head>
<body>
	<%

//	public String placeOrder(String ISBN, String quantity) throws SQLException


String feedbackIDAttribute = request.getParameter("feedbackIDAttribute");
String usefulAttribute = request.getParameter("usefulAttribute");

if( feedbackIDAttribute == null || usefulAttribute == null){
%>
        <form name="rateFeedback" method=post action="rateFeedback.jsp">
        Feedback ID:<input type=text name="feedbackIDAttribute" length=16><BR>
        Usefulness (0 for useless, 1 for useful, and 2 for very useful): <input type=text name="usefulAttribute" length=16><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User user = (cs5530.User) session.getAttribute("user");
	cs5530.Feedback feedback = new Feedback(connector, connector.stmt, user);
	
%>
<%=feedback.rateUsefulFeedback(feedbackIDAttribute, usefulAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><a href="getFeedback.jsp">View feedback for another book?</a><BR>
	<a href="mainUI.jsp"> Return to Main Menu</a>
</body>

</html>