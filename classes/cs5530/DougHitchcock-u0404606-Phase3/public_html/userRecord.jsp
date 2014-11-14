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
		cs5530.Connector connector = new Connector();
		cs5530.User user = (cs5530.User) session.getAttribute("user");
    cs5530.Order order = new Order(user);

%>

  <p><b>Full User Record for <%= user.getLogin() %>: </b><BR><BR>

  <%=user.getUserRecord()%> <BR><BR>
  connector.closeConnection();

<BR><a href="mainUI.jsp"> Return to Main Menu</a></p>

</body>