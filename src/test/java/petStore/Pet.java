package petStore;

import java.util.HashMap;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class Pet {
	
	String baseURL="https://petstore.swagger.io/v2";
	long petId;
	
	@Test(priority = 1)
	public void createPet()
	{
		//when we have complex json like an object inside an object, we pass the data like this.
		//create pet using HashMap
		//this is parent object
		
		HashMap cat=new HashMap();
		cat.put("name", "dog");

		// this is the nested object in which we have to pass the parent object
		HashMap cb= new HashMap();
		cb.put("category", cat);
		cb.put("name", "scooby");
		cb.put("status", "available");
		
//		RestAssured.given().contentType(ContentType.JSON)
//			.body(cb).log().all()
//			.when().post(baseURL+"/pet")
//			.then().assertThat().statusCode(200).log().all();	
		
		// to capture and store the response by using extract() and JSONPath()` 
		String createBodyResponse=RestAssured.given().contentType(ContentType.JSON)
			.body(cb).log().all()
			.when().post(baseURL+"/pet")
			.then().assertThat().statusCode(200).log().all()

		// below method extracts the response from the response body and store
		// it as a String in the variable createBodyResponse
		    .extract().response().asString(); 
		
		//then we need to extract the data from the extracted response by JSONPath
		// That accepts String as an argument and returns an Object
		
		JsonPath js=new JsonPath(createBodyResponse);
		petId=js.get("id");
		System.out.println(petId);
		
		String catName=js.get("category.name");
		System.out.println(catName);
	}
	
	//getPet
	@Test(priority = 2)
	public void getPet() {
		RestAssured.given().log().all()
			.when().get(baseURL+"/pet/"+petId)
			.then().assertThat().statusCode(200).log().all();		
	}	
	
	@Test(priority=3)
	public void updatePet() {
		
		HashMap cat=new HashMap();
		cat.put("name","dog");
		
		HashMap uc= new HashMap();
		uc.put("category", cat);
		uc.put("name", "sheru");
		uc.put("status", "unavailable");
		uc.put("id", petId);
		
		RestAssured.given().contentType(ContentType.JSON)
		.body(uc).log().all()
			.when().put(baseURL+"/pet")
			.then().assertThat().statusCode(200).log().all();	
	}
	@Test(priority=4)
	public void deletePet( ) {
		RestAssured.given()
			.when().delete(baseURL+"/pet/"+petId)
			.then().assertThat().statusCode(200).log().all();	
	}	
	
}
