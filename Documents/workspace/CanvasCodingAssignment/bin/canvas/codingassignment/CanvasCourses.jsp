<%@ page language="java" import="canvas.BackEndJava" %>
<html>
<title>Canvas Courses</title>
<head> </head>
<body>
	<h1> Canvas Courses are: </h1>
	<script>
		//alert("Hello Courses!");
	</script>


<!-- <table style ="width:50%">
<tr>
<td>Intro to Literary Theory</td>
<td>ENGL 2100</td>
</tr>
<tr>
<td>Introduccion a Espanoles</td>
<td>SPAN-1000</td>
</tr> -->

<!-- <html><head><title>Introduccion a Espanoles</title><h1>Introduccion a Espanoles</h1></head><body><BR><BR>Course Name: Introduccion a Espanoles<BR>Course Code: SPAN-1000<BR>Course Description: The path of the righteous man is beset on all sides by the iniquities of the selfish and the tyranny of evil men. Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of darkness, for he is truly his brother's keeper and the finder of lost children. And I will strike down upon thee with great vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know My name is the Lord when I lay My vengeance upon thee.<BR>Course Starts At: 2012-10-02 06:00:00 +0000<BR>Course Ends At: null<BR>Course Created At: 2012-11-14 19:25:38 +0000<BR>Course Updated At:null<BR>
 -->

 <%

    canvas.BackEndJava bej = new canvas.BackEndJava();
%>

  <p><b>Course Info: </b><BR><BR>

  <%=bej.getCourse("1")%> <BR><BR>


</body>

</html>
