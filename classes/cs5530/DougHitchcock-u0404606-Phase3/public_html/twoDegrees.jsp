<%@ page language="java" import="cs5530.*, java.util.*" %>
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
String searchAttribute = request.getParameter("searchAttribute");
if( searchAttribute == null ){
%>

		Two Degrees of Separation:<br>
		Enter two authors below, and see their degrees of separation
		<form name="twoDegrees" method=get onsubmit="return check_all_fields(this)" action="twoDegrees.jsp"><br>
				Author 1: <input type=text name="authorOne" ><br>
				Author 2: <input type=text name="authorTwo" ><br>
				<input type=hidden name="searchAttribute" value="twoDegrees">
				<input type=submit>
        </form>

<%

} else {

		String authorOne = request.getParameter("authorOne");
		String authorTwo = request.getParameter("authorTwo");
		String output = "I think you forgot to enter an author!";

		cs5530.Connector connector = new Connector();
		cs5530.Book book = new Book(connector, connector.stmt);

		if(authorOne.length() > 0 && authorTwo.length() > 0){
			output = book.twoDegrees(authorOne, authorTwo); 
		}

%>
	The results are:
  <BR><BR><%=output%> <BR><BR>

  <%
 connector.closeConnection();
}  // We are ending the braces for else here
%>

	<BR><a href="twoDegrees.jsp"> Try a new 2 degree search</a></p>
	<BR><a href="mainUI.jsp"> Return to Main Menu</a></p>

</body>