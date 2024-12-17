
package gitHub;

import java.util.HashMap;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class RepoChaining {
	
String token = "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";
String baseURL = "https://api.github.com";

	@Test (priority = 1)
	public void createRepo() {
		
		HashMap ub = new HashMap();
		ub.put("name", "amirrr");
		ub.put("description", "actor");
		ub.put("private", true);
		
		RestAssured.given().contentType(ContentType.JSON)
			.body(ub)
			.header("Authorization", "Bearer "+token)
			.when().post(baseURL+"/user/repos")
				// restassured validation using hamcrest	
			.then().assertThat().statusCode(201).body("name", equalTo("amirrr"))
			.body("description", equalTo("actor")).body("private",equalTo(true));
	}
	
	@Test (priority = 2)
	public void getRepo() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
			.pathParam("owner", "Nishadtaj")
			.pathParam("repo", "amirrr")
			.when().get(baseURL+"/repos/{owner/{repo}")
				// restassured validation using hamcrest	
			.then().assertThat().statusCode(200).body("name", equalTo("amirrr"))
			.body("description", equalTo("actor")).body("private",equalTo(true)).log().all()
			.header("content-type", "application/json; charset=utf=8")
			.header("Server", "gitHub.com");
	}
	
	@Test (priority = 3)
	public void updateRepo() {
		HashMap up = new HashMap();
		up.put("description", "producer");
		up.put("private", false);
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
				.pathParam("owner", "Nishadtaj")
				.pathParam("repo", "amirrr")
				.when().patch(baseURL+"/repos/{owner/{repo}")
					// restassured validation using hamcrest	
				.then().assertThat().statusCode(200).body("name", equalTo("amirrr"))
				.body("description", equalTo("producer")).body("private",equalTo(false)).log().all();
	}
	
	@Test (priority = 4)
	public void deleteRepo() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("owner", "Nishadtaj")
		.pathParam("repo", "amirrr")
		.when().delete(baseURL+"/repos/{owner/{repo}")
			// restassured validation using hamcrest	
		.then().assertThat().statusCode(204).body("name", equalTo("amirrr"))
		.body("description", equalTo("actor")).body("private",equalTo(true)).log().all()
		.header("content-type", "application/json; charset=utf=8")
		.header("Server", "gitHub.com");
	}
}
