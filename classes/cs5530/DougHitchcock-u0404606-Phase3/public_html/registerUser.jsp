<%@ page language="java" import="cs5530.*" %>
<html>
<head>
	<title>User Registration for Doug E Fresh Bookstore!</title>
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

String loginAttribute = request.getParameter("loginAttribute");
String passwordAttribute = request.getParameter("passwordAttribute");
String fullNameAttribute = request.getParameter("fullNameAttribute");
String addressAttribute = request.getParameter("addressAttribute");
String phoneAttribute = request.getParameter("phoneAttribute");
String ccNAttribute = request.getParameter("ccNAttribute");

if( loginAttribute == null || passwordAttribute == null || fullNameAttribute == null || addressAttribute == null || phoneAttribute == null || ccNAttribute == null || loginAttribute.length() == 0 || passwordAttribute.length() == 0){
%>
        <form name="login" method=post action="registerUser.jsp">
        Login:<input type=text name="loginAttribute" length=10><BR>
        Password:<input type=password name="passwordAttribute" length=10><BR>
        Full Name:<input type = text name="fullNameAttribute" length=30><BR>
        Address:<input type = text name ="addressAttribute" length=30><BR>
        Phone:<input type=text name="phoneAttribute" length=30><BR>
        Major Credit Card Number:<input type=text name="ccNAttribute" length = 20><BR>
        <input type=submit>
        </form>
<%

} 
else {

	cs5530.Connector connector = new Connector();
	cs5530.User user = new User(connector, connector.stmt);
	
%>
<%=user.registerNewUser(loginAttribute, passwordAttribute, fullNameAttribute, addressAttribute, phoneAttribute, ccNAttribute)%>
<%
	

		response.sendRedirect("index.html");
	
	%>
<%

connector.closeConnection();
}
%>
</body>

</html>