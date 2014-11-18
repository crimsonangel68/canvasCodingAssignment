package canvas.codingassignment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class backEndJava 
{
	String authorizationToken = "9be624b4d5206a178fc56921d5bf2c2a";
	String courseAPIURL = "http://canvas-api.herokuapp.com/api/v1/courses";
	
	public backEndJava()
	{
		
	}
	
	public static String getJSON()
	{
		
		String result = "";
		try {
			URL courseURL = new URL("http://canvas-api.herokuapp.com/api/v1/courses?access_token=9be624b4d5206a178fc56921d5bf2c2a");
			HttpURLConnection conn = (HttpURLConnection) courseURL.openConnection();
//			if (conn.getResponseCode() != 200)
//			{
//				System.err.println("ERROR");
//				throw new IOException(conn.getResponseMessage());
//			}
			
			BufferedReader rd = new BufferedReader( new InputStreamReader(conn.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String line;
			while((line = rd.readLine()) != null)
			{
				sb.append(line);
			}
			rd.close();
			conn.disconnect();
			result = sb.toString();
			JSONArray arr = new JSONArray(result);
			nr = "<table style =\"width:100%\">\n";
			for(int i = 0; i < arr.length(); i++)
			{
				nr += "<tr>\n";
				JSONObject job = arr.getJSONObject(i);
				nr += "<td>" + job.getString("name") + "</td>\n";
				nr += "<td>" + job.getString("code") + "</td>\n";
				nr += "</tr>\n";
			}
			nr += "</table>";
			}
		catch(JSONException e)
		{
			System.err.println(e.toString());
		}
		catch(MalformedURLException e)
		{
			System.err.println(e.toString());
		}
		catch(IOException e)
		{
			System.err.println(e.toString());
		}
		return result;
		
	}
	
	public static void main(String[] args)
	{
		String temp = getJSON();
		System.out.println(temp + "\n\n\n");
	}
}
