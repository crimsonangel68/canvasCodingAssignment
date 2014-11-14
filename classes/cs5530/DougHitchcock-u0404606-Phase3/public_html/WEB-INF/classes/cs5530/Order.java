package cs5530;

import java.sql.*;
//import javax.servlet.http.*;

public class Order{
	
	protected Connector con;
	protected Statement stmt;
	protected User user;
	protected Book book;
	
	public Order(Connector conn, Statement statement)
	{
		this.con = conn;
		this.stmt = statement;
		this.user = new User(conn, statement);
		this.book = new Book(conn, statement);
	}
	public Order(User tempUser)
	{
		this.con = tempUser.con;
		this.stmt = tempUser.stmt;
		this.user = tempUser;
		this.book = new Book(con, stmt);
	}
	
	public String getUserOrderHistory() throws SQLException
	{
		String result = "";
		String orderQuery = "select title, qty, order_date from Order_history OH2 natural join Book b where OH2.login = '" + user.ulogin + "' AND OH2.ISBN = b.ISBN";
		ResultSet results;
		try
		{
			results = stmt.executeQuery(orderQuery);
			while(results.next())
				result += "Title: " + results.getString("title") + "&#9# of copies: " + results.getInt("qty") + "&#9Ordered on: " + results.getString("order_date") + "<BR>"; 
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execture orderQuery: " + orderQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		
		return result;
	}
	public String getOrders(String attrName, String attrValue, Statement stmt) throws Exception{
		String query;
		String resultstr="";
		ResultSet results; 
		query="Select * from orders where "+attrName+"='"+attrValue+"'";
		try{
			results = stmt.executeQuery(query);
        } catch(Exception e) {
			//System.err.println("Unable to execute query:"+query+"<BR>");
	                //System.err.println(e.getMessage());
			throw(e);
		}
		//System.out.println("Order:getOrders query="+query+"<BR>");
		while (results.next()){
			resultstr += "<b>"+results.getString("login")+"</b> purchased "+results.getInt("quantity") +
							" copies of &nbsp'<i>"+results.getString("title")+"'</i><BR><BR>";	
		}
		return resultstr;
	}
	
	public boolean isAvailable(String ISBN, String title, int qty) throws SQLException
	{
		boolean available = false;
		ResultSet resultSet;
		String availableQuery = "";
		if(!ISBN.equals(""))
			availableQuery = "select * from Book where ISBN = '" + ISBN + "' AND qty_in_stock >= " + qty;
		else if(!title.equals(""))
			availableQuery = "select * from Book where Title = '" + title + "' AND qty_in_stock >= " + qty;
		try
		{
			resultSet = stmt.executeQuery(availableQuery);
			if(resultSet.next())
			{
				available = true;
			}
			else
			{
				available = false;
			}
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute available query: " + availableQuery);
			//System.err.println(e.getLocalizedMessage());
			throw(e);
		}
		return available;
	}
	
	public String placeOrder(String ISBN, String quantity) throws SQLException
	{
		String result = "";
		String qtyQuery;
		int qty = 0;
		try
		{
			qty = Integer.parseInt(quantity);
		}
		catch(Exception e)
		{
			return "\nInvalid entry for quantity. Returning to user menu";
		}
		java.sql.Date orderDate = new java.sql.Date((new java.util.Date()).getTime());
		String placeOrderQuery = "INSERT INTO Order_history VALUE(orderID, '" + orderDate + "', '" + user.ulogin + "', '" + ISBN + "', '" + qty + "')";
		if(isAvailable(ISBN, "", qty))
		{
			try
			{
				if(stmt.executeUpdate(placeOrderQuery) == 1)
				{
					result = "Successfully placed order for ISBN: " + ISBN;
				}
				book.updateQuantity(ISBN, "-" + qty);
			}
			catch(SQLException e)
			{
				//System.err.println("Unable to execute placeOrder query: " + placeOrderQuery);
				//System.err.println(e.getMessage());
				throw e;
			}
			result = "Enjoy your purchase!<BR><BR>";
			result += this.bookSuggestions(ISBN);
		}
		else
		{
			int qutity = -1;
			String quantityQuery = "Select qty_in_stock from Book where ISBN = '" + ISBN + "'";
			ResultSet results;
			try
			{
				results = stmt.executeQuery(quantityQuery);
				if(results.next())
				{
					qutity = results.getInt("qty_in_stock");
					result = "There are only " + qutity + " copies of that ISBN.<BR><BR>";
					result += this.bookSuggestions(ISBN);

					
				}
				else
				{
					qutity = 0;
					result = "ISBN: " + ISBN + " is not present in the database.";
				}
			}
			catch(SQLException e)
			{
				//System.err.println("Unable to determine quantity with quantityQuery query: " + quantityQuery);
				//System.out.println(e.getMessage());
				throw e;
			}
		}
		return result;
	}
	
	public String bookSuggestions(String ISBN) throws SQLException
	{
		String result = "People who bought this also bought:<BR>";
		//"SELECT * FROM VideoData v WHERE v.isbn IN (SELECT OH1.isbn FROM OrderHistory OH1 WHERE EXISTS "
//		+ "(SELECT OH.isbn FROM OrderHistory OH WHERE OH.isbn = '%s') AND OH1.isbn <> '%s' group by OH1.isbn) LIMIT 10"
		String suggestionQuery = "SELECT * from Book b where b.ISBN IN (select OH1.ISBN from Order_history OH1 where EXISTS (SELECT OH.ISBN from Order_history OH WHERE OH.ISBN = '" 
				+ ISBN + "') AND OH1.ISBN <> '" + ISBN + "' GROUP BY OH1.ISBN) LIMIT 10";
		ResultSet results;
		try
		{
			results = stmt.executeQuery(suggestionQuery);
			while(results.next())
				result += "Title: " + results.getString("title") + "&#9ISBN: " + results.getInt("ISBN") + "<BR>";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to determine quantity with quantityQuery query: " + suggestionQuery);
			//System.out.println(e.getMessage());
			throw e;
		}
		return result;
	}
	
	
	
	
}
