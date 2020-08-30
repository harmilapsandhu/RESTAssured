package serialization;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerialTest {
	//In Serialization, we convert the Java object into JSON to submit to the API (We will use setters here as opposed to getters used in De-Ser)
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		//We will provide the body through POJO classes using Serialization
		
		GooglePOJO pojo = new GooglePOJO();
		
		PojoLocation loc = new PojoLocation();
		loc.setLat(-38.383494);
		loc.setLng(33.427362);
		pojo.setLocation(loc);
		
		pojo.setAccuracy(50);
		pojo.setName("Frontline house");
		pojo.setPhone_number("(+91) 983 893 3937");
		pojo.setAddress("29, side layout, cohen 09");
		
		List<String> arrList = new ArrayList<String>();
		arrList.add("shoe park");
		arrList.add("shop");
		pojo.setTypes(arrList);
		
		pojo.setWebsite("http://google.com");
		pojo.setLanguage("Punjabi");
		
		
		String response = given().log().all().queryParam("key", "qaclick123")
		.body(pojo)
		.when().post("/maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		System.out.println(response);
	}

}
