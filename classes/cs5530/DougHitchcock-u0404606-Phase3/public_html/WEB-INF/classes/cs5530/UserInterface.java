package cs5530;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;

public class UserInterface {

	
	public static void loginMenu()
	{
		System.out.println("Welcome to the book database.\nPlease choose from the following options:");
		System.out.println("1. Register a new user");
		System.out.println("2. Login");
		System.out.println("3. Logout and Exit");
	}
	
	public static void displayUserMenu()
	{
		System.out.println("1.  Add new book");
		System.out.println("2.  Add more of a book, given a quantity and ISBN");
		System.out.println("3.  Leave feedback for a book, given an ISBN");
		System.out.println("4.  Get feedback for a particular ISBN");
		System.out.println("5.  Rate feedback, given a feedback ID number");
		System.out.println("6.  Trust a user? Why not say so?");
		System.out.println("7.  Place an order for a book given an ISBN.");
		System.out.println("8.  Get User Record.");
		System.out.println("9.  Get useful feedbacks given 'n'");
		System.out.println("10. Browse Collection. Results sorted by year (other sorting not implemented yet...)");
		System.out.println("11. Two Degrees");
		System.out.println("12. User Awards");
		System.out.println("13. Statistics (Not implemented)");
		System.out.println("0. Exit");
	}
	
	public static void userMenu(Connector conn, Statement stmt, User tempUser)
	{
		String choice;
		String ISBN;
		String title;
		String pub;
		String yearPub;
		String qty;
		String format;
		Book tempBook = new Book(conn, stmt);
		Feedback feedback = new Feedback(conn, stmt, tempUser);
		Order tempOrder = new Order(tempUser);
		float cost = 0.0f;
		int quantity = 0;
		int yearPubInt = 0;
		int possibleChoices = 12;
		ArrayList<String> tempAuthors = new ArrayList<String>();
		int c = -1;
		try
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			while(true)
			{
				
				displayUserMenu();
				while((choice = in.readLine()) == null && choice.length() == 0);
				try
				{
					c = Integer.parseInt(choice);
				}
				catch(Exception e)
				{
					continue;
				}
				if(c < 0 || c > possibleChoices)
				{
					continue;
				}
				if(c == 0)
				{
					break;
				}
				if(c == 1)
				{
					ArrayList<String> tempSubjects = new ArrayList<String>();
					ArrayList<String> tempKeywords = new ArrayList<String>();
					String authors;
					String subjects;
					String keywords;
					//newBook(String ISBN, String format, String title, int qty, String publisher, int year, ArrayList<String> authors) throws Exception
					String tempCost;
					System.out.print("Enter the ISBN: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.print("\nEnter title: ");
					while((title = in.readLine()) == null && title.length() == 0);
					System.out.print("\nEnter format (paperback/hardcover): ");
					while((format = in.readLine()) == null && format.length() == 0);
					System.out.print("\nEnter publisher: ");
					while((pub = in.readLine()) == null && pub.length() == 0);
					System.out.print("\nEnter year of publication: ");
					while((yearPub = in.readLine()) == null && yearPub.length() == 0);
//					try
//					{
//						yearPubInt = Integer.parseInt(yearPub);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for year of publication. Returning to user menu");
//						continue;
//					}
					System.out.print("\nEnter quantity: ");
					while((qty = in.readLine()) == null && qty.length() == 0);
//					try
//					{
//						quantity = Integer.parseInt(qty);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for quantity. Returning to user menu");
//						continue;
//					}
					System.out.print("\nEnter the cost: ");
					while((tempCost = in.readLine()) == null && tempCost.length() == 0);
//					try
//					{
//						cost = Float.parseFloat(tempCost);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for quantity. Returning to user menu");
//						continue;
//					}
					System.out.print("\nEnter the authors, separated by /\n");
					while((authors = in.readLine()) == null && authors.length() == 0);
					System.out.print("\nEnter the subjects (Genre), separated by /\n");
					while((subjects = in.readLine()) == null && subjects.length() == 0);
					System.out.print("\nEnter the keywords, separated by /\n");
					while((keywords = in.readLine()) == null && keywords.length() == 0);
//					String[] tA = authors.split("/");
//					for(int i = 0; i < tA.length; i++)
//					{
//						if(!tempAuthors.contains(tA[i]))
//							tempAuthors.add(tA[i]);
//					}
//					String[] tS = subjects.split("/");
//					for(int i = 0; i < tS.length; i++)
//					{
//						if(!tempSubjects.contains(tS[i]))
//							tempSubjects.add(tS[i]);
//					}
//					String[] tK = subjects.split("/");
//					for(int i = 0; i < tS.length; i++)
//					{
//						if(!tempKeywords.contains(tK[i]))
//							tempKeywords.add(tK[i]);
//					}
					System.out.println(tempBook.newBook(ISBN, format, title, qty, pub, yearPub, tempCost, authors, subjects, keywords));
				}
				if(c == 2)
				{
					System.out.print("Enter the ISBN: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.print("\nEnter quantity: ");
					while((qty = in.readLine()) == null && qty.length() == 0);
//					try
//					{
//						quantity = Integer.parseInt(qty);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for quantity. Returning to user menu");
//						continue;
//					}
					// updateQuantity(String ISBN, int qty) throws SQLException
					System.out.println(tempBook.updateQuantity(ISBN, qty));
					
				}
				if(c == 3)
				{
					String comment = "";
					String tempScore;
					System.out.print("Enter ISBN for the book you are leaving feedback for: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.print("Enter your feedback score: ");
					while((tempScore = in.readLine()) == null && tempScore.length() == 0);
					System.out.print("Enter your feedback comment: ");
					while((comment = in.readLine()) == null);
//					recordFeedback(String login, String comment, String score, String ISBN)
					System.out.println(feedback.recordFeedback(tempUser.ulogin, comment, tempScore, ISBN));
				}
				if(c == 4)
				{
//					getFeedback(String ISBN)
					System.out.print("Enter an ISBN: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.println(feedback.getFeedback(ISBN));
				}
				if(c == 5)
				{
					String tempScore;
//					rateUsefulFeedback(int fID, int score)
					String fIDstr;
					int fID = 0;
					int score = 0;
					System.out.print("Enter the feedback ID for your feedback: ");
					while((fIDstr = in.readLine()) == null && fIDstr.length() == 0);
					try
					{
						fID = Integer.parseInt(fIDstr);
					}
					catch(Exception e)
					{
						System.out.println("\nInvalid entry for feedback ID. Returning to user menu");
						continue;
					}
					System.out.print("How useful was this feedback? (0 for Useless, 1 for Useful, 2 for Very Useful: ");
					while((tempScore = in.readLine()) == null && tempScore.length() == 0);
					try
					{
						score = Integer.parseInt(tempScore);
					}
					catch(Exception e)
					{
						System.out.println("\nInvalid entry for usefulness. Returning to user menu");
						continue;
					}
					
					System.out.println(feedback.rateUsefulFeedback(fIDstr, tempScore));
				}
				if(c == 6)
				{
					String login;
					String tempIsTrusted;
					int isTrusted = -1;
					System.out.print("Enter the user you wish to evaluate their trust level: ");
					while((login = in.readLine()) == null && login.length() == 0);
					System.out.print("Do you trust this person? Enter 1 for trusted, enter 0 for not trusted");
					while((tempIsTrusted = in.readLine()) == null && tempIsTrusted.length() == 0);
//					try
//					{
//						isTrusted = Integer.parseInt(tempIsTrusted);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for trust category. Returning to main menu");
//						continue;
//					}
					System.out.println(tempUser.trustUser(login, tempIsTrusted));
					System.out.println(tempUser.getTrusters());
				}
				if(c == 7)
				{
					System.out.print("Enter the ISBN of the book you would like to purchase: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.print("Enter the quantity you wish to buy: ");
					while((qty = in.readLine()) == null && qty.length() == 0);
//					try
//					{
//						quantity = Integer.parseInt(qty);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for quantity. Returning to user menu");
//						continue;
//					}
					System.out.println(tempOrder.placeOrder(ISBN, qty));
				}
				if(c == 8)
				{
					System.out.println("User record: ");
					System.out.println("------------------------------------------------------------");
					System.out.println(tempUser.getUserRecord());
					
				}
				if(c == 9)
				{
					int n;
					String tempN;
					System.out.print("Enter the ISBN: ");
					while((ISBN = in.readLine()) == null && ISBN.length() == 0);
					System.out.print("Enter 'n': ");
					while((tempN = in.readLine()) == null && tempN.length() == 0);
//					try
//					{
//						n = Integer.parseInt(tempN);
//					}
//					catch(Exception e)
//					{
//						System.out.println("\nInvalid entry for 'n'. Returning to user menu");
//						continue;
//					}
					System.out.println(feedback.getUsefulFeedbacksForN(ISBN, tempN));
				}
				if(c == 10)
				{
					System.out.println("Only the ability to sort by year is implemented");
					String authorSearch = "";
					String titles = "";
					String publishers = "";
					String subjectSearch = "";
					String keywordSearch = "";
					
					System.out.println("Welcome to the browse collection: ");
					System.out.println("Enter title search words separated by /");
					while((titles = in.readLine()) == null && titles.length() == 0);
					System.out.println("Enter author search words separated by /");
					while((authorSearch = in.readLine()) == null && authorSearch.length() == 0);
					System.out.println("Enter publisher search words separated by /");
					while((publishers = in.readLine()) == null && publishers.length() == 0);
					System.out.println("Enter subject search words separated by /");
					while((subjectSearch = in.readLine()) == null && subjectSearch.length() == 0);
					System.out.println("Enter keyword search words separated by /");
					while((keywordSearch = in.readLine()) == null && keywordSearch.length() == 0);
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
					
					System.out.println(tempBook.browseBooks(authorSearch, titles, publishers, subjectSearch, keywordSearch));

				}
				if(c == 11)
				{
					System.out.println("In Two Degrees: ");
					String authorA = "";
					String authorB = "";
					System.out.println("Enter first author!");
					while((authorA = in.readLine()) == null && authorA.length() == 0);
					System.out.println("Enter second author!");
					while((authorB = in.readLine()) == null && authorB.length() == 0);
					System.out.println(tempBook.twoDegrees(authorA, authorB));
				}
				if(c == 12)
				{
					String usefulN = "";
					String trustN = "";
					int n1 = 0;
					int n2 = 0;
					System.out.println("Enter N for Useful user award: ");
					while((usefulN = in.readLine()) == null && usefulN.length() == 0);
					try
					{
						n1 = Integer.parseInt(usefulN);
					}
					catch(Exception e)
					{
						System.out.println("\nInvalid entry for 'n'. Returning to user menu");
						continue;
					}
					System.out.println("Enter N for Trusted user award: ");
					while((trustN = in.readLine()) == null && trustN.length() == 0);
					try
					{
						n2 = Integer.parseInt(trustN);
					}
					catch(Exception e)
					{
						System.out.println("\nInvalid entry for 'n'. Returning to user menu");
						continue;
					}
					System.out.println(tempUser.getUsefulUsers(usefulN)+ "\n");
					System.out.println(tempUser.getTrustGivenN(trustN));
					
					
					
				}
				if(c == 13)
				{
					System.out.println("Statistics not implemented. Check back later.");
				}
			}
		}
		catch(Exception e)
		
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) 
	{

				//System.out.println("Example for cs5530");
				Connector con=null;
				String choice;
				int loginAttempts = 0;
		        String fullName, login, password, address, phone, majorCC;
		        //String sql=null;
		        int c=0;
		         try
				 {
					//remember to replace the password
					 	 con= new Connector();
			             System.out.println ("Database connection established");
			         
			             BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			             
			             while(true)
			             {
			            	 loginMenu();
			            	 User tempUser = new User(con, con.stmt);
			            	 while ((choice = in.readLine()) == null && choice.length() == 0);
			            	 try{
			            		 c = Integer.parseInt(choice);
			            	 }catch (Exception e)
			            	 {
			            		 
			            		 continue;
			            	 }
			            	 if (c<1 | c>3)
			            		 continue;
			            	 if (c==1)
			            	 {
			            		 System.out.println("Register new user interface:");
			            		 System.out.println("Please enter your desired login: ");
			            		 while ((login = in.readLine()) == null && login.length() == 0);
			            		 System.out.println("Please enter your password: ");
			            		 while ((password = in.readLine()) == null && password.length() == 0);
			            		 System.out.println("Please enter your full name: ");
			            		 while((fullName = in.readLine()) == null && fullName.length() == 0);
			            		 System.out.println("Please enter your address: ");
			            		 while((address = in.readLine()) == null && address.length() == 0);
			            		 System.out.println("Please enter your phone number: ");
			            		 while((phone = in.readLine()) == null && phone.length() == 0);
			            		 System.out.println("Please enter a Major Credit Card number: ");
			            		 while((majorCC = in.readLine()) == null && majorCC.length() == 0);
//			            		 registerNewUser(String login, String password, String fullName, String address, String phone, String CCN)
			            		 System.out.println(tempUser.registerNewUser(login, password, fullName, address, phone, majorCC) +"\n\n\n");
			            		 if(tempUser.loginUser(login, password))
			            		 {
			            			 System.out.println("Successfully logged in as: " + login);
		            				 userMenu(con, con.stmt, tempUser);
		            				 continue;
			            		 }
			            		 
			            	 }
			            	 else if (c==2)
			            	 {	 
			            		 System.out.println("To login, please provide the following:\n");
			            		 
			            		 
			            		 while(loginAttempts < 3)
			            		 {
			            			 System.out.println("Login: ");
				            		 while ((login = in.readLine()) == null && login.length() == 0);
				            		 System.out.println("Password: ");
				            		 while ((password = in.readLine()) == null && password.length() == 0);
			            			 if(tempUser.loginUser(login, password))
			            			 {
			            				 System.out.println("Successfully logged in as: " + login);
			            				 userMenu(con, con.stmt, tempUser);
			            				 break;
			            			 }
			            			 else
			            			 {
			            				 loginAttempts++;
			            			 }
			            		 }
			            		 
			            	 }
			            	 else
			            	 {   
			            		 System.out.println("Remember to pay us!");
			            		 con.stmt.close(); 
			        
			            		 break;
			            	 }
			             }
				 }
		         catch (Exception e)
		         {
		        	 e.printStackTrace();
		        	 System.err.println ("Cannot connect to database server");
		         }
		         finally
		         {
		        	 if (con != null)
		        	 {
		        		 try
		        		 {
		        			 con.closeConnection();
		        			 System.out.println ("Database connection terminated");
		        		 }
		        	 
		        		 catch (Exception e) { /* ignore close errors */ }
		        	 }	 
		         }
			

	}
	

	
	
	

}
