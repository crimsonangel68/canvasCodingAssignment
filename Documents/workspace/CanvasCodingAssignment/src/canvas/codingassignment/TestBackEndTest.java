package canvas.codingassignment;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestBackEndTest {

	@Test
	public void test() {
		
		String expected =  "<html><head><title>Introduccion a Espanoles</title><h1>Introduccion a Espanoles</h1></head><body><BR><BR>Course "
				+ "Name: Introduccion a Espanoles<BR>Course Code: SPAN-1000<BR>Course Description: The path of the "
				+ "righteous man is beset on all sides by the iniquities of the selfish and the tyranny of evil men. "
				+ "Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of darkness, "
				+ "for he is truly his brother's keeper and the finder of lost children. And I will strike down upon thee with "
				+ "great vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know "
				+ "My name is the Lord when I lay My vengeance upon thee.<BR>Course Starts At: 2012-10-02 06:00:00 +0000<BR>Course "
				+ "Ends At: null<BR>Course Created At: 2012-11-14 19:25:38 +0000<BR>Course Updated At:null<BR>";
		
		BackEndJava test = new BackEndJava();
		
		String temp = test.getCourse("2");
		assertEquals(expected, temp);
		temp = test.getCourse("1");
		assertNotEquals(expected, temp);
		
	}
	@Test
	public void test1() {
	
		String expectedJSONResult = "[{\"json_class\":\"Course\",\"id\":1,\"name\":\"Intro to Literary Theory\",\"code\":\"ENGL 2100\",\"description\":\"The lysine contingency - it's intended to prevent the spread of the animals is case they ever got off the island. Dr. Wu inserted a gene that makes a single faulty enzyme in protein metabolism. The animals can't manufacture the amino acid lysine. Unless they're continually supplied with lysine by us, they'll slip into a coma and die.\",\"start_at\":\"2012-09-28 15:58:58 +0000\",\"end_at\":null,\"created_at\":\"2012-11-14 19:25:38 +0000\",\"updated_at\":null},{\"json_class\":\"Course\",\"id\":2,\"name\":\"Introduccion a Espanoles\",\"code\":\"SPAN-1000\",\"description\":\"The path of the righteous man is beset on all sides by the iniquities of the selfish and the tyranny of evil men. Blessed is he who, in the name of charity and good will, shepherds the weak through the valley of darkness, for he is truly his brother's keeper and the finder of lost children. And I will strike down upon thee with great vengeance and furious anger those who would attempt to poison and destroy My brothers. And you will know My name is the Lord when I lay My vengeance upon thee.\",\"start_at\":\"2012-10-02 06:00:00 +0000\",\"end_at\":null,\"created_at\":\"2012-11-14 19:25:38 +0000\",\"updated_at\":null}]";
		
		BackEndJava test = new BackEndJava();
		
		String temp = test.getResultFromAPI();
		assertEquals(expectedJSONResult, temp);
		
	}
	
	@Test
	public void test2()
	{
		BackEndJava test = new BackEndJava();
		boolean formedURL = false;
		String URL = "This is not a valid URL";
		System.out.println(test.checkURL(URL));
		assertEquals(formedURL, test.checkURL(URL));
		URL = "http://canvas-api.herokuapp.com/api/v1/courses";
		formedURL = true;
		assertEquals(formedURL, test.checkURL(URL));
	}
	
	@Test
	public void test3()
	{
		BackEndJava test = new BackEndJava();
		boolean hasToken = false;
		String URL = "http://canvas-api.herokuapp.com/api/v1/courses";
		assertEquals(hasToken, test.checkForToken(URL));
		URL += "?access_token=9be624b4d5206a178fc56921d5bf2c2a";
		hasToken = true;
		assertEquals(hasToken, test.checkForToken(URL));

		
	}
	
	@Test
	public void test4()
	{
		BackEndJava test = new BackEndJava();
		String URL = "http://canvas-api.herokuapp.com/api/v1/courses";
		String token = "?access_token=9be624b421d5bf2c2a";
		String expectedResult = "ERROR";
		assertEquals(expectedResult, test.getJSON(URL, token));
	
	}

}
