package files;

import io.restassured.path.json.JsonPath;

public class ReusableMethods {
	
	public static JsonPath rawToJson(String respon)
	{
		JsonPath jsOne = new JsonPath(respon);
		return jsOne;
	}

}
