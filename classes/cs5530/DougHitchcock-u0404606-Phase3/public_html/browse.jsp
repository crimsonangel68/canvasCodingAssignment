<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<title>Doug E Fresh Bookstore!</title>
<script LANGUAGE="javascript">


</script>
</head>
<body>
	<%
//public String newBook(String ISBN, String format, String title, String qty, String publisher, String year, String tempCost, String authors, String subjects, String keywords) throws Exception
String titleAttribute = request.getParameter("titleAttribute");
String publisherAttribute = request.getParameter("publisherAttribute");
String authorsAttribute = request.getParameter("authorsAttribute");
String subjectsAttribute = request.getParameter("subjectsAttribute");
String keywordsAttribute = request.getParameter("keywordsAttribute");

if( titleAttribute == null && publisherAttribute == null && authorsAttribute == null && subjectsAttribute == null && keywordsAttribute == null){
%>
        <form name="browseBook" method=post action="browse.jsp">
        Title(s) (separated by /):<input type=text name="titleAttribute" length=20><BR>
        Author(s) (separated by /):<input type = text name="authorsAttribute" length=30><BR>
        Publisher:<input type = text name ="publisherAttribute" length=30><BR>
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
<%=book.browseBooks(authorsAttribute, titleAttribute, publisherAttribute, subjectsAttribute, keywordsAttribute)%>

<%
connector.closeConnection();
}
%>
<BR><a href="browse.jsp"> Try another search?</a></p>
<BR><BR><BR><a href="mainUI.jsp"> Return to Main Menu</a></p>
</body>

</html>