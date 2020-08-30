package first;


import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LibraryAPI {

	@Test(dataProvider="booksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String addB = given().log().all().header("Content-Type", "application/json")
				.body(Payload.addBookPayLoad(isbn, aisle))
		.when().post("/Library/Addbook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = ReusableMethods.rawToJson(addB);
		String id = js.get("ID");
		System.out.println(id);
	}
//Book will be added after addBook test but this will fail if we try to run it again as that book had already been added before,
//So we either change the data in PayLoad or add another method to delete the book	
	
	@Test
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
		
	} 
	
	
	@DataProvider(name="booksData")
	public Object[][] getData()
	{
		return new Object[][] {{"dhgsc", "5487"}, {"skdid", "4212"}, {"skaju", "2251"}};
	}
	
}
