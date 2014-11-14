package cs5530;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
public class Book 
{
	private Connector con;
	private Statement stmt;
	
	public Book(Connector conn, Statement statement)
	{
		this.con = conn;
		this.stmt = statement;
	}
	
	public String newBook(String ISBN, String format, String title, String qty, String publisher, String year, String tempCost, String authors, String subjects, String keywords) throws Exception
	{
		int yearPubInt = 0;
		int quantity = 0;
		float cost = 0.0f;
		ArrayList<String> tempSubjects = new ArrayList<String>();
		ArrayList<String> tempKeywords = new ArrayList<String>();
		ArrayList<String> tempAuthors = new ArrayList<String>();
		try
		{
			yearPubInt = Integer.parseInt(year);
		}
		catch(Exception e)
		{
			return "\nInvalid entry for year of publication. Returning to user menu";
			
		}
		
		try
		{
			quantity = Integer.parseInt(qty);
		}
		catch(Exception e)
		{
			return "\nInvalid entry for quantity. Returning to user menu";
			
		}
		
		try
		{
			cost = Float.parseFloat(tempCost);
		}
		catch(Exception e)
		{
			return "\nInvalid entry for quantity. Returning to user menu";
			
		}
		
		String[] tA = authors.split("/");
		for(int i = 0; i < tA.length; i++)
		{
			if(!tempAuthors.contains(tA[i]))
				tempAuthors.add(tA[i]);
		}
		String[] tS = subjects.split("/");
		for(int i = 0; i < tS.length; i++)
		{
			if(!tempSubjects.contains(tS[i]))
				tempSubjects.add(tS[i]);
		}
		String[] tK = subjects.split("/");
		for(int i = 0; i < tS.length; i++)
		{
			if(!tempKeywords.contains(tK[i]))
				tempKeywords.add(tK[i]);
		}
		String result = "TEST RESULT<BR><BR>";
		String bookQuery = "";
		String authorQuery = "";
		String subjectQuery = "";
		String keywordQuery = "";
		bookQuery = "INSERT INTO Book VALUES('" +ISBN + "', '"+ format+ "', '"+ title + "', '"+ quantity+ "', '"+ publisher+ "', '"+ yearPubInt+ "', '" + cost +"')";
		try
		{
			if(stmt.executeUpdate(bookQuery) == 1)
			{
				//System.out.println("Successfully added " + title + " with " + ISBN);
			}
		}
		catch(SQLException e)
		{
			//System.err.println("In NewBook: Unable to execute query:" + bookQuery + "<BR>");
			//System.err.println(e.getMessage());
			return "ERROR ADDING BOOK";
			
		}
		try
		{
			for(int i = 0; i < tempAuthors.size(); i++)
			{
				authorQuery = "INSERT INTO Author VALUES('" +ISBN + "', '" + tempAuthors.get(i).trim() + "')";

				if(stmt.executeUpdate(authorQuery) == 1)
				{
					//System.out.println("Successfully added " + title + " with " + ISBN);
				}
			}
		}
		catch(SQLException e)
		{
			//System.err.println("In NewBook: Unable to execute query:" + authorQuery + "<BR>");
			//System.err.println(e.getMessage());
			return "ERROR WITH QUERY: " + authorQuery;
		}
		try
		{
			for(int i = 0; i < tempSubjects.size(); i++)
			{
				subjectQuery = "INSERT INTO Subject VALUES('" +ISBN + "', '" + tempSubjects.get(i).trim() + "')";

				if(stmt.executeUpdate(subjectQuery) == 1)
				{
					//System.out.println("Successfully added " + title + " with " + ISBN);
				}
			}
		}
		catch(SQLException e)
		{
			//System.err.println("In NewBook: Unable to execute query:" + subjectQuery + "<BR>");
			//System.err.println(e.getMessage());
			return "ERROR WITH QUERY: " + subjectQuery;		}
		try
		{
			for(int i = 0; i < tempKeywords.size(); i++)
			{
				keywordQuery = "INSERT INTO Keywords VALUES('" +ISBN + "', '" + tempKeywords.get(i).trim() + "')";

				if(stmt.executeUpdate(keywordQuery) == 1)
				{
					//System.out.println("Successfully added " + title + " with " + ISBN);
				}
			}
		}
		catch(SQLException e)
		{
			//System.err.println("In NewBook: Unable to execute query:" + keywordQuery + "<BR>");
			//System.err.println(e.getMessage());
			return "ERROR WITH QUERY: " + keywordQuery;
		}
		return result;
	}
	
	public String updateQuantity(String ISBN, String quantity) throws SQLException
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
		qtyQuery = "UPDATE Book SET qty_in_stock = qty_in_stock + '" + qty + "' WHERE ISBN = '" + ISBN + "'";
		try
		{
			if(stmt.executeUpdate(qtyQuery) == 1)
			{
//				if(qty < 0)
					//System.out.println("Successfully removed " + Math.abs(qty) + " copies from ISBN " + ISBN);
//				else
					//System.out.println("Successfully added " + qty + " copies to " + ISBN);
			}
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute query: " + qtyQuery);
			//System.err.println(e.getMessage());
			throw(e);
		}
		return result;
	}
	
	public String browseBooks(String authorSearch, String titles, String publishers, String subjectSearch, String keywordSearch) throws SQLException
	{
		ArrayList<String> titleWords = new ArrayList<String>();
		ArrayList<String> authors = new ArrayList<String>();
		ArrayList<String> publisherWords = new ArrayList<String>();
		ArrayList<String> subjects = new ArrayList<String>();
		ArrayList<String> keywords = new ArrayList<String>();
		String[] tA = authorSearch.split("/");
		for(int i = 0; i < tA.length; i++)
		{
			if(!authors.contains(tA[i]))
				authors.add(tA[i]);
		}
		String[] tT = titles.split("/");
		for(int i = 0; i < tT.length; i++)
		{
			if(!titleWords.contains(tT[i]))
				titleWords.add(tT[i]);
		}
		String[] tP = publishers.split("/");
		for(int i = 0; i < tP.length; i++)
		{
			if(!publisherWords.contains(tP[i]))
				publisherWords.add(tP[i]);
		}
		String[] tS = subjectSearch.split("/");
		for(int i = 0; i < tS.length; i++)
		{
			if(!subjects.contains(tS[i]))
				subjects.add(tS[i]);
		}
		String[] tK = keywordSearch.split("/");
		for(int i = 0; i < tK.length; i++)
		{
			if(!keywords.contains(tK[i]))
				keywords.add(tK[i]);
		}
		String result = "";
		ResultSet results;
		String cond = " OR ";
		ArrayList<String> ISBNs = new ArrayList<String>();
		String authorQuery = "";
		String titleQuery = "";
		String publisherQuery = "";
		String subjectQuery = "";
		String keywordQuery = "";
		String browseQuery = "Select * from Book WHERE ";
		ResultSet authorSet;
		ResultSet titleSet;
		ResultSet pubSet;
		ResultSet keywordSet;
		ResultSet subjectSet;
		authorQuery = "Select a.ISBN from Author a WHERE ";
		titleQuery = "Select b.ISBN from Book b WHERE ";
		publisherQuery = "Select b.ISBN from Book b WHERE ";
		keywordQuery = "Select k.ISBN from Keywords k WHERE ";
		subjectQuery = "Select s.ISBN from Subject s WHERE ";
		if(authorSearch.length() > 0)
		{
		for(String a : authors)
			authorQuery += "a.authorName LIKE '%" + a + "%'" + cond + " ";
		}
		if(titles.length() > 0)
		{
		for(String b : titleWords)
			titleQuery += "b.title LIKE '%" + b + "%'" + cond + " ";
		}
		if(publishers.length() > 0)
		{
		for(String p : publisherWords)
			publisherQuery += "b.publisher LIKE '%" + p + "%'" + cond + " ";
		}
		if(keywordSearch.length() > 0)
		{
		for(String k : keywords)
			keywordQuery += "k.Keyword LIKE '%" + k + "%'" + cond + " ";
		}
		if(subjectSearch.length() > 0)
		{
		for(String s : subjects)
			subjectQuery += "s.Subject LIKE '%" + s + "%'" + cond + " ";
		}
//		//System.out.println("Author Query" + authorQuery);
//		//System.out.println("Title Query" + titleQuery);
//		//System.out.println("Publisher Query" + publisherQuery);
//		//System.out.println("Keyword Query" + keywordQuery);
//		//System.out.println("Subject Query" + subjectQuery);
		if(authorSearch.length() > 0)
			authorQuery = authorQuery.substring(0, authorQuery.lastIndexOf(" OR "));
		if(titles.length() > 0)
			titleQuery = titleQuery.substring(0, titleQuery.lastIndexOf(" OR "));
		if(publishers.length() > 0)
			publisherQuery = publisherQuery.substring(0, publisherQuery.lastIndexOf(" OR "));
		if(keywordSearch.length() > 0)
		keywordQuery = keywordQuery.substring(0, keywordQuery.lastIndexOf(" OR "));
		if(subjectSearch.length() > 0)
		subjectQuery = subjectQuery.substring(0, subjectQuery.lastIndexOf(" OR "));
//		//System.out.println("----------------------------------------------");
//		//System.out.println("Author Query" + authorQuery);
//		//System.out.println("Title Query" + titleQuery);
//		//System.out.println("Publisher Query" + publisherQuery);
//		//System.out.println("Keyword Query" + keywordQuery);
//		//System.out.println("Subject Query" + subjectQuery);

		try
		{
			if(authorSearch.length() > 0){
			authorSet = stmt.executeQuery(authorQuery);
			while(authorSet.next())
			{
				if(!ISBNs.contains(authorSet.getString("ISBN")))
						ISBNs.add(authorSet.getString("ISBN"));
			}
			}
			if(titles.length() > 0)
			{
			titleSet = stmt.executeQuery(titleQuery);
			while(titleSet.next())
			{
				if(!ISBNs.contains(titleSet.getString("ISBN")))
						ISBNs.add(titleSet.getString("ISBN"));
			}
			}
			if(publishers.length() > 0)
			{
			pubSet = stmt.executeQuery(publisherQuery);
			while(pubSet.next())
			{
				if(!ISBNs.contains(pubSet.getString("ISBN")))
						ISBNs.add(pubSet.getString("ISBN"));
			}
			}
			if(keywordSearch.length() > 0)
			{
			keywordSet = stmt.executeQuery(keywordQuery);
			while(keywordSet.next())
			{
				if(!ISBNs.contains(keywordSet.getString("ISBN")))
						ISBNs.add(keywordSet.getString("ISBN"));
			}
			}
			if(subjectSearch.length() > 0)
			{
			subjectSet = stmt.executeQuery(subjectQuery);
			while(subjectSet.next())
			{
				if(!ISBNs.contains(subjectSet.getString("ISBN")))
						ISBNs.add(subjectSet.getString("ISBN"));
			}
			}
			if(ISBNs.size() > 0)
			{
			for(String isbn : ISBNs)
				browseQuery += "ISBN = '" + isbn + "' OR ";
			browseQuery = browseQuery.substring(0, browseQuery.lastIndexOf(" OR "));
			browseQuery += " ORDER BY Year_of_publication DESC";
			//System.out.println("Browse Query: " + browseQuery);
			results = stmt.executeQuery(browseQuery);
			result += "Results from search are: <BR>";
			while(results.next())
				result += "Title: " + results.getString("title") + "&#9ISBN: " + results.getString("ISBN") + "&#9Year Published: " + results.getString("Year_of_publication") + "<BR>";
			}
			else
				result = "No fields were entered<BR>";
		}
		catch(SQLException e)
		{
			//System.err.println("Unable to execute a query in browseBooks");
			//System.err.println(e.getMessage());
			throw e;
		}
		return result;
	}
	
	public String twoDegrees(String authorA, String authorB) throws SQLException
	{
		String result = "Either no degree of separation, or greater than 2 degrees";
		if(authorA.equals(authorB))
			return "Zero degree of separation. It's almost like they are the same person!";
		String authorAQuery = "Select * from Author where authorName = '" + authorA + "'";
		String authorBQuery = "Select * from Author where authorName = '" + authorB + "'";
		String allAuthorsQuery = "Select * from Author";
		String currentQuery = "";
		ResultSet authorASet;
		ResultSet authorBSet;
		ResultSet allAuthorsSet;
		ArrayList<String> allAuthors = new ArrayList<String>();
		HashSet<String> sharedAAuthors = new HashSet<String>();
		HashSet<String> sharedBAuthors = new HashSet<String>();
		HashSet<String> authorAISBNs = new HashSet<String>();
		HashSet<String> authorBISBNs = new HashSet<String>();
		try
		{
			currentQuery = authorAQuery;
			authorASet = stmt.executeQuery(currentQuery);
			while(authorASet.next())
				authorAISBNs.add(authorASet.getString("ISBN"));
			currentQuery = authorBQuery;
			authorBSet = stmt.executeQuery(currentQuery);
			while(authorBSet.next())
				authorBISBNs.add(authorBSet.getString("ISBN"));
			currentQuery = allAuthorsQuery;
			allAuthorsSet = stmt.executeQuery(currentQuery);
			String tempISBN = "";
			String tempAuthor = "";
			while(allAuthorsSet.next())
			{
				if(allAuthorsSet.getString("authorName").equals(authorA) || allAuthorsSet.getString("authorName").equals(authorA))
					continue;
				tempISBN = allAuthorsSet.getString("ISBN");
				tempAuthor = allAuthorsSet.getString("authorName");
				if(authorAISBNs.contains(tempISBN) && authorBISBNs.contains(tempISBN))
					return "One degree of separation";
				
				if(authorAISBNs.contains(tempISBN))
					sharedAAuthors.add(tempAuthor);
				if(authorBISBNs.contains(tempISBN))
					sharedBAuthors.add(tempAuthor);
				if(sharedBAuthors.contains(tempAuthor) && sharedAAuthors.contains(tempAuthor))
					return "Two Degrees of Separation";
				
			}
		}
		catch(SQLException e)
		{
			//System.err.println("In Two Degrees: Unable to execute query: " + currentQuery);
			//System.err.println(e.getMessage());
			throw e;
		}
		return result;
	}
	
	
}
