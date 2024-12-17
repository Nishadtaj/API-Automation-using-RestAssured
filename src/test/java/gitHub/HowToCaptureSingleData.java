package gitHub;
import java.util.HashMap;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class HowToCaptureSingleData {

	@Test
	
	public void createRepo() {
		HashMap rb=new HashMap();
		rb.put("name", "anushka");
		rb.put("description", "actor");
		rb.put("private", true);
	
	String repoName = RestAssured.given().contentType(ContentType.JSON)
		.body(rb).log().all()
		.header("Authorizaion","Bearer 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")
		// we are capturing repo name (single data) here
		.when().post("https://api.github.com/user/repos").jsonPath().get("name");
		System.out.println(repoName);
		//.then().assertThat().statusCode(201).log().all()// not required
	}
	
	@Test
	public void deleteRepo() {
		
		 RestAssured.given().header("Authorizaion","Bearer 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")
			.when().delete("https://api.github.com/repos/Nishadtaj/anushka")
			.then().assertThat().statusCode(404).log().all();
			
		}
}
