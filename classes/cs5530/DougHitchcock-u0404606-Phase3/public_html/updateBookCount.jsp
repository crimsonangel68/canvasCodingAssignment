<%@ page language="java" import="cs5530.*" %>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Doug E Fresh Bookstore!</title>
</head>
<body>
	<%

//	public String updateQuantity(String ISBN, String quantity) throws SQLException


String isbnAttribute = request.getParameter("isbnAttribute");
String qtyAttribute = request.getParameter("qtyAttribute");

if( isbnAttribute == null || qtyAttribute == null){
%>
        <form name="updateCount" method=post action="updateBookCount.jsp">
        ISBN:<input type=text name="isbnAttribute" length=16><BR>
        Quantity:<input type=text name="qtyAttribute" length = 10><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.Book book= new Book(connector, connector.stmt);
	
%>
<%=book.updateQuantity(isbnAttribute, qtyAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><BR><a href="mainUI.jsp"> Return to Main Menu</a></p>
</body>

</html>