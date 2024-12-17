package reqres;


import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.CreateUser;


public class Users {
	
	@Test (priority = 0)
	public void createUser()
	{
		//given()-> Inputs
		//when()-> HTTP method along with the URL
		//then()-> Validation
		//body() -> must be string always
		
		RestAssured.given().body("{\r\n" + 
				"    \"name\": \"morpheus\",\r\n" + 
				"    \"job\": \"leader\"\r\n" + 
				"}").contentType(ContentType.JSON)
		
		//or we can pass the content type like below line in the form of key value pair
		//.header("Content-Type","application/json")
		
			.when().post("https://reqres.in/api/users")
			
		//	.then().assertThat().statusCode(201); -> but this doesn't log response in the console
		//  alternative
			
		//	.then().log().all().assertThat().statusCode(201);
			.then().assertThat().statusCode(201).log().all();
	}
	
	@Test (priority = 1)
	public void getSingleUser() {
		
		RestAssured.given().log().all() // to view user/client input data
			.when().get("https://reqres.in/api/users/2")
			.then().log().all().assertThat().statusCode(200);	
	}
	
	@Test (priority = 2)
	public void listUsers() {
		
		RestAssured.given().log().all()
			.when().get("https://reqres.in/api/users?page=2")
			.then().assertThat().statusCode(200).log().all();
	}
	
	@Test (priority = 3)
	public void noUserFound() {
		
		RestAssured.given().log().all()
			.when().get("https://reqres.in/api/users/23")
			.then().assertThat().statusCode(404).log().all();
	}
	
	@Test (priority = 4)
	public void updateUser() {
		
		CreateUser cb = new CreateUser();
		cb.setName("Nishu");
		cb.setJob("AE");
		
		RestAssured.given().contentType(ContentType.JSON).body(cb)
			.when().put("https://reqres.in/api/users/2")
			.then().assertThat().statusCode(200).log().all();
	}
	
	@Test (priority = 5)
	public void updateUserPatch() {
			
		CreateUser cb = new CreateUser();
		cb.setName("Nishuuuuu");
			
		RestAssured.given().contentType(ContentType.JSON).body(cb)
				.when().patch("https://reqres.in/api/users/2")
				.then().assertThat().statusCode(200).log().all();
		}
	
	@Test (priority = 6)
	public void deleteUser() {
			
		RestAssured.when().delete("https://reqres.in/api/users/2")
				.then().assertThat().statusCode(204).log().all();
		}
}
