package jira;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

public class JIRA_I {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//Handling an HTTPS certificate Validation through Automation, we should add .relaxedHTTPSValidation() after writing given().
		//This is cookie based authentication
		RestAssured.baseURI = "http://localhost:8080";
		//This session object of SessionFilter class will store the session details which can be later used in other scenarios
		//This can be used as a substitute of creating a JsonPath class which takes a String as an argument and then extract
		//the required information and store it in a String. So SessionFilter is a much easier way of doing it.
		SessionFilter session = new SessionFilter();
		
		//First to login into the session
		given().log().all().header("Content-Type", "application/json")
		.body("{ \"username\": \"harmilapsandhu\", \"password\": \"Arsenal_49\" }")
		.filter(session) //Need to pass this here for SessionFilter class to remember session details
		.when().post("rest/auth/1/session")
		.then().log().all();
		
		//Now to add comment to an already present bug in JIRA
		
		String expectedMessage = "Auba Signs";
		String addComment = given().log().all().pathParam("key", "RES-1").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"    \"body\": \""+expectedMessage+"\",\r\n" + 
				"    \"visibility\": {\r\n" + 
				"        \"type\": \"role\",\r\n" + 
				"        \"value\": \"Administrators\"\r\n" + 
				"    }\r\n" + 
				"}")
		.filter(session) //Passing the object session here to retrieve the session details and automatically authenticate
		.when().post("/rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addComment);
		String messageID = js.get("id").toString(); //Stored the ID of message here to verify it later when we use Get Issue
		
		
		//Adding an Attachment - below is the curl command for adding an attachment
//curl -D- -u admin:admin -X POST -H "X-Atlassian-Token: no-check" -F "file=@myfile.txt" http://myhost/rest/api/2/issue/TEST-123/attachments
		
		/*given().log().all().pathParam("key", "RES-1").header("X-Atlassian-Token", "no-check")
		.header("Content-Type", "multipart/form-data") //Use this header for attachments
		.filter(session)
		.multiPart("file", new File("jira.txt")) //Use multiPart to add an attachment
		.when().post("/rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);*/
		
		//Get Issue
//pathParam are used to get to your desired issue in JIRA whereas queryParam is to further limit the field in that issue		
		String issueDetails = given().log().all().filter(session).pathParam("key", "RES-1")
		.queryParam("fields", "comment") //Use queryParam to filter your output response		
		.when().get("/rest/api/2/issue/{key}")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(issueDetails); //Response will be seen in console even if we don't create string because we're logging
//We can copy the response from console and paste it in https://jsoneditoronline.org/ for better understanding
		
//We will now verify if comment we passed before is actually present in JIRA
		JsonPath js1 = new JsonPath(issueDetails);
		int commentCount = js1.getInt("fields.comment.comments.size()");
		for (int i = 0; i<commentCount; i++)
		{
			String commentID = js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentID.equalsIgnoreCase(messageID))
			{
				String message = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(message);
				Assert.assertEquals(expectedMessage, message);  //Using TestNG Assertion
			}
		}
				
	}

}
