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
String scoreAttribute = request.getParameter("scoreAttribute");
String commentAttribute = request.getParameter("commentAttribute");

if( isbnAttribute == null || scoreAttribute == null){
%>
        <form name="recordUserFeedback" method=post action="recordFeedback.jsp">
        ISBN:<input type=text name="isbnAttribute" length=16><BR>
        Score:<input type=text name="scoreAttribute" length = 10><BR>
        Comment (optional, but limited to 255 characters):<textarea name="commentAttribute" cols="40" rows="5"></textarea><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User tempUser = (cs5530.User) session.getAttribute("user");
	cs5530.Feedback feedback = new Feedback(connector, connector.stmt, (cs5530.User) session.getAttribute("user"));
	if(commentAttribute == null)
		commentAttribute = "";
	
%>
<%=feedback.recordFeedback(tempUser.getLogin(), commentAttribute, scoreAttribute, isbnAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><BR><a href="mainUI.jsp"> Return to Main Menu</a></p>
</body>

</html>