<%@ page language="java" import="cs5530.*" %>
<html>
<head>

</head>
<body>

<%
	String loginAttribute = request.getParameter( "loginAttribute" );
	String passwordAttribute = request.getParameter( "passwordAttribute" );
	cs5530.Connector connector = new Connector();
	cs5530.User user = new User(connector, connector.stmt);

	if(user.loginUser(loginAttribute, passwordAttribute))
	{
		session.setAttribute("user", user);
		session.setAttribute("loggedIn", "true");
		response.sendRedirect("mainUI.jsp");
	} 
	else
	{
		response.sendRedirect("login.jsp");
		connector.closeConnection();
	}  
%>
</body>