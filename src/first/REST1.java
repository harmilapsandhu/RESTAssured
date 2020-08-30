package first;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusableMethods;

public class REST1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//For POST there should definitely be a body
		//PUT is used to update something - Also requires a body
		//GET won't have a header or a body
		
		//Validate if Add Place API is working as expected
		
		//Given - give all input details
		//When - submit the response - Resource, HTTP
		//Then - validate the response
		
		//We will Add place->Update place with new address->Use Get Place to validate new address
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String respo = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
			.body(Payload.payBody()) //driven from PayLoad class
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
			.header("Server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		System.out.println(respo); //The 'respo' variable contains all the information about id, scope etc.
		
		JsonPath js = new JsonPath(respo); //This JsonPath class takes string as an input and converts it into Json
		//This will be used to parse Json
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		//Now Update Place
		String newAddress = "60 Flippers End";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" +
				"\"place_id\":\""+placeID+"\",\r\n" + 
				"\"address\":\""+newAddress+"\",\r\n" + 
				"\"key\":\"qaclick123\"\r\n" + 
				"}")
		.when().put("/maps/api/place/update/json") //Use update in a put request
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Now use Get Place API to verify the address
		String getPlace = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		//Here we stored the above response in a string and parsed that string in Json below to get desired value
		
		JsonPath jsOne = ReusableMethods.rawToJson(getPlace); //Used ReusableMethods class to derive info from
		String updatedAddress = jsOne.getString("address");
		System.out.println(updatedAddress);
		//To verify that the address has changed
		Assert.assertEquals(updatedAddress, newAddress); //After importing TestNG jar
	}

}
//For equalTo to not throw an error use, import static org.hamcrest.Matchers.*;
//In Then part we are adding assertions and validating our test
//The Header method we used in given was for input and the one used in then is for output validation
