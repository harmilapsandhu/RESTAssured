package serialization;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SpecBuilder {
	//In Serialization, we convert the Java object into JSON to submit to the API (We will use setters here as opposed to getters used in De-Ser)
	public static void main(String[] args) {
		// TODO Auto-generated method stub

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
		
		//We will create a RequestSpecification & ResponseSpecification class to optimize the code
		RequestSpecification requestSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").log(LogDetail.ALL).addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).log(LogDetail.ALL).build();
		
		RequestSpecification res = given().spec(requestSpec).body(pojo); //broke the given statement separately and used 'res' object with when to continue the statement
		
		String response = res.when().post("/maps/api/place/add/json")
		.then().spec(responseSpec).extract().response().asString();
		
		System.out.println(response);
	}

}
