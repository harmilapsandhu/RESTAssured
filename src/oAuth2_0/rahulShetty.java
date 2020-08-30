package oAuth2_0;
import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.MainPOJO;
import pojo.PojoApi;
import pojo.PojoWebAutomation;

@SuppressWarnings("unused")
public class rahulShetty {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		
		//To get the Authorization Code - We have to capture the auth code from the browser URL and RestAssured is unable to do
		//that so we will use Selenium for this purpose
		//System.setProperty("webdriver.chrome.driver", "C://Users//Harmilap//Documents//Work//chromedriver_win32//chromedriver.exe");
		//WebDriver driver = new ChromeDriver();
		//Get the URL after entering all the information in the contract
		//driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://www.googleapis.com/oauth2/v4/token&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=AubaSigns");
		//driver.findElement(By.id("identifierId")).sendKeys("paultheskitman@gmail.com");
		//driver.findElement(By.xpath("//div[@class='VfPpkd-RLmnJb']")).click();
		//Thread.sleep(2000);
		//driver.findElement(By.cssSelector("[type='password']")).sendKeys("Slayer@666");
		//driver.findElement(By.cssSelector("[type='password']")).sendKeys(Keys.ENTER);
		//Thread.sleep(2000);
		//String codeURL = driver.getCurrentUrl();  //We now have the entire URL in which authorization code is present
		
		//Because of a recent update by google, we are unable to automate the above using Selenium, so manually get the URL 
		//using the contract and pass it as a string below.
		
		String[] allCoursesWebAuto = {"Selenium Webdriver Java", "Cypress", "Protractor"};
		//Use below code to generate new Authentication code
		//https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php
		String codeURL = "https://rahulshettyacademy.com/getCourse.php?state=AubaSigns&code=4%2F3QElvl-LZ7CBwJP_FRRV-3vElAigO1tNEUq-xX3WqhM1uxEpluEsyIbJTBm9rfFXCwkp59K_w3lHMdtED-NpJkk&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=4&prompt=none#";
		String halfCode = codeURL.split("code=")[1];
		String authorizationCode = halfCode.split("&scope")[0];
		System.out.println(authorizationCode);
		
		
		//To obtain the Access Token
		//By default RestAssured encodes special characters into numerals but our authorizationCode has a special character so
		//we have to explicitly tell it to not encode this special character in this below step
		String accessTokenResponse = given().urlEncodingEnabled(false)
		.queryParams("code", authorizationCode)
		.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type", "authorization_code")
		.when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();
		//now to extract the Access Token
		JsonPath js = new JsonPath(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Token is "+accessToken);
		
		//To hit the final request using Access Token
		//String finalResponse = given().queryParam("access_token", accessToken)
		//.when().log().all().get("https://rahulshettyacademy.com/getCourse.php").asString();
		//System.out.println(finalResponse);
		
		//For De-Serialization using POJO classes of JSON into Java object
//Also we have to tell our Rest API test about what format response we expect from java class using .expect().defaultParser
		MainPOJO pojo = given().queryParam("access_token", accessToken) //Create an object of the pojo class
				.expect().defaultParser(Parser.JSON) //we have to tell our Rest API test about what format response we expect from java class using .expect().defaultParser
				.when().get("https://rahulshettyacademy.com/getCourse.php").as(MainPOJO.class); //Use .as(Class) and pass pojo class
		
		System.out.println(pojo.getInstructor());
		System.out.println(pojo.getLinkedIn());
		//Get title of Soap UI Webservices testing
		System.out.println(pojo.getCourses().getApi().get(1).getCourseTitle());
		//Get price of a title without using index or hard-coding
		List<PojoApi> apiCourse = pojo.getCourses().getApi();
		for (int i = 0; i<apiCourse.size(); i++)
		{
			if(apiCourse.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing"))
			{
				System.out.println(apiCourse.get(i).getPrice());
			}
		}
		//Get all titles under WebAutomation and compare them to a String allCoursesWebAuto listed at top.
		ArrayList<String> arrList = new ArrayList<String>();//We create an ArrayList so that its dynamic nature can automatically add all the elements
		List<PojoWebAutomation> webAutoCourses = pojo.getCourses().getWebAutomation();
		for (int i = 0; i<webAutoCourses.size(); i++)
		{
			System.out.println(webAutoCourses.get(i).getCourseTitle()); //This prints all course titles under webAutomation
			arrList.add(webAutoCourses.get(i).getCourseTitle());
		}
		//Convert the Array at top to ArrayList for JAVA to be able to match the two ArrayLists
		List<String> expectedCourses = Arrays.asList(allCoursesWebAuto);
		Assert.assertTrue(expectedCourses.equals(arrList));
		
		
	}

}

