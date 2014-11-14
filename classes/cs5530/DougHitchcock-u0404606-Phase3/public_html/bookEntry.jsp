<%@ page language="java" import="cs5530.*" %>
<html>
<head>
        <title>Doug E Fresh Bookstore!</title>
<script LANGUAGE="javascript">

function check_all_fields(form_obj){
	alert(form_obj.searchAttribute.value+"='"+form_obj.attributeValue.value+"'");
	if( form_obj.attributeValue.value == ""){
		alert("Search field should be nonempty");
		return false;
	}
	return true;
}

</script>
</head>
<body>
	<%
//public String newBook(String ISBN, String format, String title, String qty, String publisher, String year, String tempCost, String authors, String subjects, String keywords) throws Exception
String isbnAttribute = request.getParameter("isbnAttribute");
String titleAttribute = request.getParameter("titleAttribute");
String formatAttribute = request.getParameter("formatAttribute");
String qtyAttribute = request.getParameter("qtyAttribute");
String publisherAttribute = request.getParameter("publisherAttribute");
String yearAttribute = request.getParameter("yearAttribute");
String costAttribute = request.getParameter("costAttribute");
String authorsAttribute = request.getParameter("authorsAttribute");
String subjectsAttribute = request.getParameter("subjectsAttribute");
String keywordsAttribute = request.getParameter("keywordsAttribute");

if( isbnAttribute == null || titleAttribute == null || formatAttribute == null || qtyAttribute == null || publisherAttribute == null || yearAttribute == null || costAttribute == null || authorsAttribute == null || subjectsAttribute == null || keywordsAttribute == null){
%>
        <form name="newBook" method=post action="bookEntry.jsp">
        ISBN:<input type=text name="isbnAttribute" length=16><BR>
        Title:<input type=text name="titleAttribute" length=20><BR>
        Author(s) (separated by /):<input type = text name="authorsAttribute" length=30><BR>
        Format (paperback/hardcover):<input type=text name="formatAttribute" length=10><BR>
        Publisher:<input type = text name ="publisherAttribute" length=30><BR>
        Year Published:<input type=text name="yearAttribute" length=4><BR>
        Quantity:<input type=text name="qtyAttribute" length = 10><BR>
        Cost:<input type=text name="costAttribute" length = 10><BR>
        Subject(s) (separated by /):<input type=text name="subjectsAttribute" length = 20><BR>
        Keyword(s) (separated by /):<input type=text name="keywordsAttribute" length = 20><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.Book book= new Book(connector, connector.stmt);
	
%>
<%=book.newBook(isbnAttribute, formatAttribute, titleAttribute, qtyAttribute, publisherAttribute, yearAttribute, costAttribute, authorsAttribute, subjectsAttribute, keywordsAttribute)%>
<%
connector.closeConnection();
}
%>

<BR><BR><BR><a href="mainUI.jsp"> Return to Main Menu</a></p>
</body>

</html>