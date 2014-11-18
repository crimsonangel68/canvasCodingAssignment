package canvas.codingassignment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.*;


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
		String nr = "";
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
				nr += "<td>" + job.getInt("id") + "</td>";
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
//		nr = result;
		return nr;
		
	}
	
	public static String getCourse(String idStr)
	{
		
		int id = -1;
		try
		{
			id = Integer.parseInt(idStr);
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
		if (id == -1) return "Invalid Id";
		String result = "";
		String nr = "";
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
			JSONObject job = null;
			for(int i = 0; i < arr.length();i++)
			{
				job = arr.getJSONObject(i);
				if(job.getInt("id") == id)
					break;
			}
			if(job == null)
				return "Course Not Found";
			
			nr += "<html><head><title>" + job.getString("name") +"</title><h1>" + job.getString("name") + "</h1></head><body>";
			nr += "<BR><BR>";
			nr += "Course Name: " + job.getString("name") + "<BR>";
			nr += "Course Code: " + job.getString("code") + "<BR>";
			nr += "Course Description: " + job.getString("description") + "<BR>";
			nr += "Course Starts At: " + job.get("start_at").toString() + "<BR>";
			nr += "Course Ends At: " + job.get("end_at").toString() + "<BR>";
			nr += "Course Created At: " + job.get("created_at").toString() + "<BR>";
			nr += "Course Updated At:"  + job.get("updated_at").toString() + "<BR>";
			
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
//			nr = result;
			return nr;
	}
	public static void main(String[] args)
	{
		String temp = getJSON();
		System.out.println(temp + "\n\n\n");
		
		System.out.println(getCourse("2"));
		
	}
	
	
}
