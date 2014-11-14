package cs5530;

import java.sql.*;
import java.util.*;
public class Feedback 
{
	// Feedback table format(fID, ISBN, score, date_written, text, login)
	protected Connector con;
	protected Statement stmt;
	protected User user;
	public Feedback(Connector conn, Statement statement)
	{
		this.user = new User(conn, statement);
		this.con = conn;
		this.stmt = statement;
	}
	public Feedback(Connector conn, Statement statement, User tempUser)
	{
		this.con = conn;
		this.stmt = statement;
		this.user = tempUser;
	}
	
	// Feedback table format(fID, ISBN, score, date_written, text, login)
	public String recordFeedback(String login, String comment, String score, String ISBN) throws SQLException
	{
		String result = "";
		String feedbackQuery = "";
		String leftFeedbackAlready = "SELECT * from Feedback where ISBN = '" + ISBN + "' AND login = '" + login + "'";
		ResultSet testFeedback;
		try
		{
			testFeedback = stmt.executeQuery(leftFeedbackAlready);
			if(testFeedback.next())
			{
				result = "You have already entered feedback for this book. Thank you!";
				return result;
			}
		}
		catch(SQLException e)
		{
			//System.err.println("Error checking presence of feedback with query: " + leftFeedbackAlready);
			//System.err.println(e.getMessage());
			throw e;
		}
		java.sql.Date feedbackDate = new java.sql.Date((new java.util.Date()).getTime());
		int feedbackScore = -1;
		try{
			feedbackScore = Integer.parseInt(score);
			if(feedbackScore < 0)
				feedbackScore = 0;
			else if(feedbackScore > 10)
				feedbackScore = 10;
		}
		catch(Exception e)
		{
			result = "Unable to parse " + score;
			//System.err.println(result);
			return result;			
		}
		feedbackQuery = "INSERT INTO Feedback VALUES (fID, '" + ISBN + "', '"  + feedbackScore + "', '"  + feedbackDate + "', '"  + comment + "', '"  + login + "')";  
		try
		{
			if(stmt.executeUpdate(feedbackQuery) == 1)
			{
				result = "Successfully inserted feedback.";
			}
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to enter feedback with query: " + feedbackQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		String usefulStatQuery = "UPDATE UsefulStats SET totalFeedback = totalFeedback + '1' WHERE login = '" + login + "'";
		try
		{
			if(stmt.executeUpdate(usefulStatQuery) == 1)
			{
				//System.out.println("Successfully updated stats");
			}
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to enter usefulFeedbackQuery with query: " + usefulStatQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	
	public String getFeedback(String ISBN) throws SQLException
	{
		String result = "Results for ISBN: " + ISBN + "<BR>";
		String feedbackQuery = "select * from Feedback where ISBN = '" + ISBN + "'";
		ResultSet results;
		try
		{
			results = stmt.executeQuery(feedbackQuery);
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to getFeedback for query: " + feedbackQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		while(results.next())
		{
			result += "Feedback ID: " + results.getString("fID") + "&#9" + results.getString("login") + " gave score of: " + results.getInt("score") + 
					" and left comment: '" + results.getString("text") + "' on: " + results.getString("date_written") + "<BR>";
		}
		return result;
	}
	
	public String getFeedbackForUser(String login) throws SQLException
	{
		String result = "Results for Username: " + login + "<BR>";
		String feedbackQuery = "select * from Feedback where login = '" + login + "'";
		ResultSet results;
		try
		{
			results = stmt.executeQuery(feedbackQuery);
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to getFeedback for query: " + feedbackQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		while(results.next())
		{
//			resultstr += "<b>"+results.getString("login")+"</b> purchased "+results.getInt("quantity") +
//					" copies of &nbsp'<i>"+results.getString("title")+"'</i><BR><BR>";
			result += "Feedback ID: " + results.getString("fID") + "&#9" + results.getString("login") + " gave score of: " + results.getInt("score") + 
					" and left comment: '" + results.getString("text") + "' on: " + results.getString("date_written") + "<BR>";
		}
		return result;
	}
	
	public String rateUsefulFeedback(String fIDstr, String tempScore) throws SQLException
	{
		int fID = 0;
		int score = 0;
		try
		{
			fID = Integer.parseInt(fIDstr);
		}
		catch(Exception e)
		{
			//System.out.println("\nInvalid entry for feedback ID. Returning to user menu");
			return "Please enter a number for the feedback ID<BR>";
		}
		try
		{
			score = Integer.parseInt(tempScore);
		}
		catch(Exception e)
		{
			return "Please enter a number for usefulness (0 for useless, 1 for useful, and 2 for very useful)<BR>";
		}
		String result = "";
		if (score > 2) 
			score = 2;
		
		if (score < 0) 
			score = 0;
		String testIfSameUser = "Select * from Feedback where fID = '" + fID + "'";
		ResultSet results;
		String feedbackLogin;
		int ISBN = 0;
		try
		{
			results = stmt.executeQuery(testIfSameUser);
			if(results.next())
			{
				feedbackLogin = results.getString("login");
				ISBN = results.getInt("ISBN");
				if(feedbackLogin.equals(user.ulogin))
				{
					result = "You cannot give usefulness ratings to yourself.";
	
					return result;
				}
			}
			else
				return "Feedback not found";
			
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute testIfSameUser query: " + testIfSameUser);
			//System.err.println(e.getMessage());
			throw e;
		}
		String rateFeedbackQuery = "INSERT INTO Usefulness VALUES('" + user.ulogin + "', '" + fID + "', '" + score + "')";
		try
		{
			if(stmt.executeUpdate(rateFeedbackQuery) == 1)
				result = "Successfully inserted feedback rating";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute rateFeedbackQuery: " + rateFeedbackQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		//"UPDATE Book SET qty_in_stock = qty_in_stock + '" + qty + "' WHERE ISBN = '" + ISBN + "'";
		String checkForUsefulRankingQuery = "Select * from UsefulRankings WHERE fID = '" + fID + "'";
		String usefulStatQuery = "UPDATE UsefulStats SET feedbackUsefulScore = feedbackUsefulScore + '" + score + "', averageFeedback = feedbackUsefulScore / totalFeedback WHERE login = '" + feedbackLogin + "'";
		
		String updateUsefulRankingsQuery = "UPDATE UsefulRankings SET usefulCount = usefulCount + '1', usefulScore = usefulScore + '" 
				+ score + "', averageScore = usefulScore / usefulCount WHERE fID = '"+ fID + "'";
		String insertUsefulRankingQuery = "INSERT INTO UsefulRankings VALUES('" + fID + "', '" + ISBN + "', '1', '" + score + "', '" + score + "')";
		try
		{
			results = stmt.executeQuery(checkForUsefulRankingQuery);
			if(results.next())
				stmt.executeUpdate(updateUsefulRankingsQuery);
			else
				stmt.executeUpdate(insertUsefulRankingQuery);
			stmt.executeUpdate(usefulStatQuery);
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute usefulRankingsQuery: " + updateUsefulRankingsQuery);
			//System.err.println("Or... Unable to execute Query: " + updateUsefulRankingsQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	
	public String getUsefulFeedbacksForN(String ISBN, String tempN) throws SQLException
	{
		int n = 0;
		try
		{
			n = Integer.parseInt(tempN);
		}
		catch(Exception e)
		{
			return "Invalid entry for 'n'. Returning to user menu<BR>";
		}
		String result = "";
		String forNquery = "Select f.fID, f.login, f.score, f.text, ur.averageScore from Feedback f join UsefulRankings ur where ur.ISBN = '" + ISBN + "' AND f.fID = ur.fID ORDER BY ur.averageScore DESC LIMIT " + n;
		ResultSet results;
		try
		{
			results = stmt.executeQuery(forNquery);
			while(results.next())
				result += "Feedback ID: " + results.getInt("fID") + "&#9Login: " + results.getString("login") + "&#9Score Given: " 
						+ results.getInt("score") + "&#9Comment Provided: " + results.getString("text") + "&#9Average Usefulness Score: " + results.getFloat("averageScore")  + "<BR>";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute forNQuery: " + forNquery);
			//System.err.println(e.getMessage());
			throw e;
		}
		return result;
	}

}
