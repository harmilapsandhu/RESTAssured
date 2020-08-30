package first;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.util.HashMap;

import org.testng.annotations.Test;

public class LibraryHashMap {

	@Test
	public void addBook()
	{
		//This will make a JSON format in key/value pair with String being key and object being value by inserting data with HashMap
		HashMap<String, Object> jsonAsMap = new HashMap<>();
		jsonAsMap.put("name", "Richard Dawkins");
		jsonAsMap.put("isbn", "fuhs");
		jsonAsMap.put("aisle", "315");
		jsonAsMap.put("author", "The God Delusion");
		/* For Nested JSON, we will create another HashMap and pass its object as a value)
		 * HashMap<String, Object> jsonAsMap2 = new HashMap<>();
		 * jsonAsMap2.put("lat", "65");
		 * jsonAsMap2.put("lng", "34");
		 * jsonAsMap.put("location", jsonAsMap2); 
		 */
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String addB = given().log().all().header("Content-Type", "application/json")
				.body(jsonAsMap)
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(addB);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	/*@Test
	public void deleteBook()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String delB = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" +
				"\"ID\":\"skid4212\",\r\n" +
				"\"ID\":\"skju2251\"\r\n" + 
				"}")
		.when().delete("/Library/DeleteBook.php")
		.then().log().all().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(delB);
		String mess = js.get("msg");
		System.out.println(mess);
		
	} */
	
	
}
