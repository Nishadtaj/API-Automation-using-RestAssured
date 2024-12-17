package differentWaysToPassBody;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import pojo.CreateUser;

public class UsingPOJO {

	@Test
	
	public void createUser()
	
	{
		
		CreateUser cb=new CreateUser();
		cb.setName("Nishad Taj");
		cb.setJob("Sotware Engineer");
		
		RestAssured.given().contentType(ContentType.JSON)
			.body(cb)
			.when().post("https://reqres.in/api/users")
			.then().assertThat().statusCode(201).log().all();
		
	}
}
