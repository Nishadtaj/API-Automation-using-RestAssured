package gitHub;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import static org.hamcrest.Matchers.*;

public class Repos {
	
	// Validation approach 1(RestAssured validation)
	//@Test
	public void createRepo() {
		HashMap rb=new HashMap();
		rb.put("name", "virat3");
		rb.put("description", "cricketer");
		rb.put("private", false);
		
		RestAssured.given().contentType(ContentType.JSON)
			.body(rb).log().all()
			.header("Authorization","Bearer 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") // when authorization is required
			.when().post("https://api.github.com/user/repos")
			.then().log().all().assertThat().statusCode(201)
		//validation
			.body("name", equalTo("virat3")).body("description", equalTo("cricketer"))
			.body("private", equalTo(false))
			.header("Server", "github.com"); // no idea
	}
	
	// Validation approach 2(TestNG validation)
	@Test
	public void createRepo1() {
	
		HashMap rb=new HashMap();
		rb.put("name", "virat5");
		rb.put("description", "cricketer");
		rb.put("private", false);
		
		String createResponseBody=RestAssured.given().contentType(ContentType.JSON)
				.body(rb).log().all()
				.header("Authorization","Bearer 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")
				.when().post("https://api.github.com/user/repos")
				.then().assertThat().statusCode(201).log().all().extract().response().asString();
		
			JsonPath js=new JsonPath(createResponseBody);
			String repoName=js.get("name");
			String desc=js.get("description");
			Boolean private1=js.get("private");
			
			// TestNG validation using asserts		
			Assert.assertEquals(repoName, "virat5");
			Assert.assertEquals(desc, "cricketer");
			Assert.assertEquals(private1, false);
	}
	
	//Validation approach 3
}


