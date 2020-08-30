package first;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class NestedJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(Payload.bookPurchase());
		//Print the number of courses
		
		int courseCount = js.getInt("courses.size()"); //Used .size as it was an Array
		System.out.println(courseCount);
		
		//Print Purchase Amount
		int courseAmount = js.getInt("dashboard.purchaseAmount"); //Use . operator to traverse from parent to child
		System.out.println(courseAmount);
		
		//Print title of First course
		String firstCourse = js.getString("courses[0].title"); //Can also use js.get()
		System.out.println(firstCourse);
		
		//Print all course titles and their respective prices
		for (int i = 0; i<courseCount; i++)
		{
			String courseTitles = js.getString("courses["+i+"].title");
			System.out.println(courseTitles);
			System.out.println(js.getInt("courses["+i+"].price"));
		}
		
		//Print number of copies sold by RPA course
		for (int i = 0; i<courseCount; i++)
		{
			String courseTitles = js.getString("courses["+i+"].title");
			if (courseTitles.equalsIgnoreCase("RPA"))
			{
				System.out.println("RPA Copies Sold: " + js.getInt("courses["+i+"].copies"));
				break;
			}	
		}	
		
		//Verify if sum of prices of all courses matches Purchase Amount
		int sum = 0;
		for (int i = 0; i<courseCount; i++)
		{
			int allPrices = js.getInt("courses["+i+"].price");
			int allCopies = js.getInt("courses["+i+"].copies");
			int allAmounts = allPrices * allCopies;
			System.out.println(allAmounts);
			sum = sum + allAmounts; //To sum up all the individual amounts use this logic
		}
		System.out.println(sum);
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(sum, purchaseAmount);
		
	} 

}
