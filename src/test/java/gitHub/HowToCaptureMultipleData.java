package gitHub;

import static org.testng.Assert.assertEquals;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class HowToCaptureMultipleData {
	@Test
	
	public void createRepo() {
		HashMap rb=new HashMap();
		rb.put("name", "anushka1");
		rb.put("description", "actor");
		rb.put("private", true);
	
	Response createRepoResp = RestAssured.given().contentType(ContentType.JSON)
			.body(rb).log().all()
			.header("Authorizaion","Bearer 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")
			.when().post("https://api.github.com/user/repos");
	
		// storng the response we got in the variable of type Response and fetching the required with the help of it
		System.out.println(createRepoResp.getStatusCode());
		System.out.print(createRepoResp.body().jsonPath().get("name"));
		System.out.println(createRepoResp.body().jsonPath().get("description"));
		System.out.println(createRepoResp.body().jsonPath().get("private"));
		System.out.println(createRepoResp.body().jsonPath().get("owner.login"));
		
		// validation for the response we captured
		Assert.assertEquals(createRepoResp.getStatusCode(), 201);
		Assert.assertEquals(createRepoResp.body().jsonPath().get("name"), "anushka1");
		Assert.assertEquals(createRepoResp.body().jsonPath().get("description"), "actor");
		Assert.assertEquals(createRepoResp.body().jsonPath().get("private"), false);	
}

	@Test
	public void deleteRepo() {
		
		 RestAssured.given().header("Authorizaion","Bearer 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")
			.when().delete("https://api.github.com/repos/Nishadtaj/anushka1")
			.then().assertThat().statusCode(404).log().all();
			
		}
}

