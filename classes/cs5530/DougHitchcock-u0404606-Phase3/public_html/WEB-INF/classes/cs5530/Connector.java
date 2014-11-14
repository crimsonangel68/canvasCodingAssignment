package cs5530;

import java.sql.*;

public class Connector {
	public Connection con;
	public Statement stmt;
	public Connector() throws Exception {
		try{
			// database for my project: cs5530db42
			// username for my database: cs5530u42
			// password: 1tcac89d
			/*
			 * database: cs5530db42
			 * username: cs5530u42
			 * password: 1tcac89d
			 */
			
		 	String userName = "cs5530u42"; 
	   		String password = "1tcac89d";
	        	String url = "jdbc:mysql://georgia.eng.utah.edu/cs5530db42";
		        Class.forName ("com.mysql.jdbc.Driver").newInstance ();
        		con = DriverManager.getConnection (url, userName, password);

			//DriverManager.registerDriver (new oracle.jdbc.driver.OracleDriver());
        	//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt = con.createStatement();
			//stmt=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
        } catch(Exception e) {
			System.err.println("Unable to open mysql jdbc connection. The error is as follows,\n");
            		System.err.println(e.getMessage());
			throw(e);
		}
	}
	public void closeStatement() throws Exception{
		stmt.close();
	}
	public void closeConnection() throws Exception{
		con.close();
	}
}
