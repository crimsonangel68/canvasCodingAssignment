package cs5530;

import java.sql.*;
//import java.util.Date;
public class User 
{
	protected Connector con;
	protected Statement stmt;
	private boolean loggedIn = false;
	
	protected String fname = "";
	protected String ulogin = "";
	protected String password = "";
	protected String uAddress = "";
	protected String majorCC = "";
	protected String uPhone = "";
	protected Feedback feedback;
	protected Order order;
	protected Book book;
	
	public User(Connector connection, Statement statement)
	{
		this.con = connection;
		this.stmt = statement;
		this.loggedIn = false;
		book = new Book(this.con, this.stmt);
		feedback = new Feedback(this.con, this.stmt, this);
		order = new Order(this);
	}
	
	public String getLogin() {return this.ulogin; }
	
	public String registerNewUser(String login, String password, String fullName, String address, String phone, String CCN) throws Exception
	{
		String testQuery = "";
		String trustStatQuery = "";
		String usefulStatQuery = "";
		String result = "Failed to register user.";
		String testLoginQuery = "Select * from User WHERE login = '" + login + "'";
		ResultSet test;
		testQuery = "INSERT INTO User VALUES ('" + login + "', '" + password + "', '" + fullName + "', '" + address + "', '" + phone + "', '"  + CCN + "')";
		trustStatQuery = "INSERT INTO TrustStats VALUES ('" + login + "', '0', '0', '0')";
		usefulStatQuery = "INSERT INTO UsefulStats VALUES ('" + login + "', '0', '0', '0')";
		try
		{
			test = stmt.executeQuery(testLoginQuery);
			if(test.next())
				return "Login is already taken. Returning to main menu.";
			if(stmt.executeUpdate(testQuery) == 1)
			{
				result = "Successfully registered user. Huzzah!";
			}
			stmt.executeUpdate(trustStatQuery);
			stmt.executeUpdate(usefulStatQuery);
		}
		catch(SQLException e)
		{
			//System.err.println("Query failed: " + testQuery);
			//System.err.println(e.getMessage());
			throw(e);
		}
//		//System.out.println(result);
		return result;
	}
	
	public boolean loginUser(String login, String password)
	{
		boolean loginResult = false;
		ResultSet tempResults;
		String testQuery = "SELECT * FROM User WHERE login = '" + login + "' AND password = '" + password + "'";
		try
		{
			tempResults = stmt.executeQuery(testQuery);
			while(tempResults.next())
			{
				this.fname = tempResults.getString("full_name");
				this.uAddress = tempResults.getString("address");
				this.ulogin = tempResults.getString("login");
				this.password = tempResults.getString("password");
				this.uPhone = tempResults.getString("phone");
				this.majorCC = tempResults.getString("major_cc_number");
				this.loggedIn = true;
				//System.out.println("Full Name is: " + fname);
				//System.out.println("Address is: " + uAddress);
				//System.out.println("Login is: " + ulogin);
				//System.out.println("Password: " + this.password);
				//System.out.println("Phone is: " + this.uPhone);
				//System.out.println("Major CC is: " + this.majorCC);
				loginResult = true;
								
			}
			
			
		}
		catch (SQLException e)
		{
			//System.err.println("Query Failed: " + testQuery);
		}
		return loginResult;
	}
	
	public String trustUser(String login, String trusted) throws SQLException
	{
		int tempIsTrusted = 0;
		try
		{
			tempIsTrusted = Integer.parseInt(trusted);
		}
		catch(Exception e)
		{
			return "Invalid entry for trust category. Returning to main menu<BR>";
		}
		String result = "";
		int isTrusted = (tempIsTrusted > 0) ? 1 : 0;
		java.sql.Date trustDate = new java.sql.Date((new java.util.Date()).getTime());

		if(login.equals(ulogin))
		{
			result = "You cannot trust yourself.";
			return result;
		}
		String trustQuery = "INSERT INTO Trusts VALUES ('" + ulogin + "', '" + login + "', '" + isTrusted + "', '" + trustDate + "')";
//		UPDATE UsefulRankings SET usefulCount = usefulCount + '1', usefulScore = usefulScore + '" 
//				+ score + "', averageScore = usefulScore / usefulCount WHERE fID = '"+ fID + "'"
		String trustStatQuery = "";
		if(tempIsTrusted == 1)
			trustStatQuery = "UPDATE TrustStats SET trustCount = trustCount + '1', notTrustCount = notTrustCount + '0', trustScore = trustCount - notTrustCount WHERE login = '" + login + "'";
		else
			trustStatQuery = "UPDATE TrustStats SET trustCount = trustCount + '0', notTrustCount = notTrustCount + '1', trustScore = trustCount - notTrustCount WHERE login = '" + login + "'";
		try
		{
			if(stmt.executeUpdate(trustQuery) == 1)
			{
				if(isTrusted == 1)
					result = login + " does seem like a trustworthy fellow!";
				else
					result = login + " always seemed like a shady person. You might be wise to not trust them";
			}
			stmt.executeUpdate(trustStatQuery);
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute trustQuery: " + trustQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	
	public String getTrusters() throws SQLException
	{
		String result = "";
		String trusterQuery = "Select * from Trusts where Trustee = '" + this.ulogin + "'";
		ResultSet results;
		try
		{
			results = stmt.executeQuery(trusterQuery);
			while(results.next())
				result += results.getString("Truster") + " evaluated " + results.getString("Trustee") + " on " + results.getString("date_trusted") + 
				" and they " + (results.getInt("isTrusted") == 1 ? "trusted them<BR>" : "did not trust them.<BR>");
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute trusterQuery: " + trusterQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	
	public String getTrustGivenN(String trustN) throws SQLException
	{
		int n = 0;
		try
		{
			n = Integer.parseInt(trustN);
		}
		catch(Exception e)
		{
			return "Invalid entry for 'n'. Returning to user menu<BR>";
		}

		String result = "<BR>Most Trusted Users are:<BR><BR>--------------------------------------------------------<BR>";
		String trustNQuery = "Select * from TrustStats ORDER BY trustScore DESC LIMIT " + n;
		ResultSet results;
		try
		{
			results = stmt.executeQuery(trustNQuery);
			while(results.next())
				result += results.getString("login") + " with a trust score of " + results.getString("trustScore") +"<BR>";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute trustNQuery: " + trustNQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		return result;
	}
	
	public String getUsefulUsers(String usefulN) throws SQLException
	{
		int n = 0;
		try
		{
			n = Integer.parseInt(usefulN);
		}
		catch(Exception e)
		{
			return "Invalid entry for 'n'. Returning to user menu";
		}
		String result = "<BR>Most Useful Users are:<BR>--------------------------------------------------------<BR>";
		String usefulNQuery = "Select * from UsefulStats ORDER BY averageFeedback DESC LIMIT " + n;
		ResultSet results;
		try
		{
			results = stmt.executeQuery(usefulNQuery);
			while(results.next())
				result += results.getString("login") + " with an average useful feedback score of " + results.getString("averageFeedback") + "<BR>";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute trustNQuery: " + usefulNQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		return result;
	}
	
	public String getUsefulFeedbacks() throws SQLException
	{
		String result = "";
		ResultSet results;
//		Select * from Usefulness u join Feedback f where f.login = u.login AND f.login = 'IronMan';
//		String usefulQuery = "Select * from Feedback f natural join Usefulness u where f.login = u.login AND u.login = '" + this.ulogin + "'";
		String usefulQuery = "Select * from Usefulness u join Feedback f where f.login = u.login AND f.login = '" + this.ulogin + "'";
		try
		{
			results = stmt.executeQuery(usefulQuery);
		}
		catch (SQLException e)
		{
			//System.err.println("Unable to execute usefulQuery: " + usefulQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		while(results.next())
		{
			result += "You rated Feedback: " + results.getString("fID") + "&#9with feedback comment: " + results.getString("text") 
			+ "&#9for book: " + results.getString("ISBN") + " and said it was ";
			if(results.getInt("type") == 0)
				result += "Useless<BR>";
			else if(results.getInt("type") == 1)
				result += "Useful<BR>";
			else
				result += "Very Useful<BR>";
			
		}
		return result;
	}
	
	public String getUserRecord() throws SQLException
	{
		String result = "";
		String nl = "<BR>";
		result += "Username: " + this.ulogin + nl;
		result += "Password: " + this.password + nl;
		result += "Full Name: " + this.fname + nl;
		result += "Address: " + this.uAddress + nl;
		result += "Phone: " + this.uPhone + nl;
		result += "Major CC: " + this.majorCC + nl+nl;
		result += "Order History: <BR>";
		result += "------------------------------------------------------------" + nl;
		result += order.getUserOrderHistory() + nl;
		result += "Feedback: <BR>";
		result += "------------------------------------------------------------" + nl;
		result += feedback.getFeedbackForUser(this.ulogin) + nl + nl;
		result += "Feedbacks rated as useful: " + nl;
		result += "------------------------------------------------------------" + nl;
		result += this.getUsefulFeedbacks() + nl + nl;
		result += "Users are trusted or not trusted: " + nl;
		result += "------------------------------------------------------------" + nl;
		result += this.getTrusters();
		
		return result;
	}
}
