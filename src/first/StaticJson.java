package first;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class StaticJson {

	//Use this strategy only if the PayLoad is static
	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String addB = given().log().all().header("Content-Type", "application/json")
				.body(GenerateStringFromResource("C:\\Users\\Harmilap\\Documents\\Work\\REST Assured\\Library.json"))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(addB);
		String id = js.get("ID");
		System.out.println(id);
	}
	
	//Define this method to get file from external resource for data
	public static String GenerateStringFromResource(String path) throws IOException {
		
		return new String(Files.readAllBytes(Paths.get(path))); //This converts the bytes in a file to String
	}
	
	
}
