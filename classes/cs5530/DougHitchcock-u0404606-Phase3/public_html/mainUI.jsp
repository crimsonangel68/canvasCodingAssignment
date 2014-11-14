<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<title> Doug E Fresh Main Menu </title>
</head>
<body>

<%
cs5530.User user = (cs5530.User) session.getAttribute("user");
if(user == null){
	response.sendRedirect("login.jsp");
} 
else {
%>
	<BR><a href="userRecord.jsp"> User Record</a><BR>
	<BR><a href="orderHistory.jsp"> See Order History</a><BR>
	<BR><a href="bookEntry.jsp"> Enter New Books</a><BR>
	<BR><a href="updateBookCount.jsp"> Update Book Count</a><BR>
	<BR><a href="placeOrder.jsp"> Order a book</a><BR>
	<BR><a href="getFeedback.jsp"> View Feedback for a given ISBN </a><BR>
	<BR><a href="recordFeedback.jsp"> Leave Feedback</a><BR>
	<BR><a href="usefulNFeedbacks.jsp"> Get N useful feedbacks for a book, given an ISBN </a><BR>
	<BR><a href="twoDegrees.jsp"> Two Degrees</a><BR>
	<BR><a href="browse.jsp"> Browse Books</a><BR>
	<BR><a href="trustUser.jsp"> Trust a user?</a><BR>
	<BR><a href="userAwards.jsp"> User Awards</a><BR>



<%
}  
%>



</body>