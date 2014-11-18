package canvas.codingassignment;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;


public class backEndJava 
{
	String authorizationToken = "9be624b4d5206a178fc56921d5bf2c2a";
	URL courseURL;
	try {
		courseURL = new URL("http://canvas-api.herokuapp.com/api/v1/courses");}
	catch(MalformedURLException e)
	{
		
	}
	
	Object jsonData;
}
