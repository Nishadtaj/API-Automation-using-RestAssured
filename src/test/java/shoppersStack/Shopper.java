package shoppersStack;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.AddAddress;
import pojo.AddProductToCart;
import pojo.AddProductToWishlist;
import pojo.AddReview;
import pojo.OrderAdd;
import pojo.PlaceOrderData;
import pojo.ShopperLogin;
import pojo.UpdateProductInCart;


public class Shopper {
	
	String baseURL="https://www.shoppersstack.com/shopping";
	int shopperId;
	String token;
	int productId;
	int itemId;
	int addressId;
	int orderId;
	int reviewId;
	
	//login module capturing shopperId and token. Storing them as global variables
	@Test(priority=1)
	public void shopperLogin() {
		
		pojo.ShopperLogin s1=new pojo.ShopperLogin();
		s1.setEmail("nishadtaj84@gmail.com");
		s1.setPassword("Nishadtaj@3");
		s1.setRole("SHOPPER");
		
		//getting the response body and capturing the data from it
		Response loginResp=RestAssured.given().body(s1).contentType(ContentType.JSON)
		.when().post(baseURL+"/users/login");
		shopperId=loginResp.jsonPath().get("data.userId");  //json path from json path finder
		token=loginResp.jsonPath().get("data.jwtToken"); //json path from json path finder
		
		System.out.println(shopperId);
		System.out.println(token);
		
		//validation
		
		Assert.assertEquals(loginResp.getStatusCode(), 200);
		
		String email=loginResp.jsonPath().get("data.email");
		Assert.assertEquals(email, "nishadtaj84@gmail.com");
	}
	
	//view proooducts module capturing productId
	@Test(priority=2)
	public void viewProducts() {
		
		productId=RestAssured.given()
		.when().get(baseURL+"/products/alpha").jsonPath().get("data[0].productId");
		System.out.println(productId);
	}
	
	//wishlist module capturing productId and storing it as a global variable
	
	//@Test(priority=3)
	public void addProductToWishlist()
	
	{
		pojo.AddProductToWishlist s1 = new pojo.AddProductToWishlist();
		s1.setProductId(productId);
		s1.setQuantity(0);
		
		RestAssured.given().log().all().body(s1).contentType(ContentType.JSON)
		.header("Authorization", "Bearer "+token).pathParam("shopperId", shopperId)
		.when().post(baseURL+"/shoppers/{shopperId}/wishlist")
		.then().assertThat().statusCode(201).log().all();
	}
	
	//@Test(priority=4)
	public void getAllProductsFromWishlist()
	
	{
		RestAssured.given().header("Authorization", "Bearer "+token).pathParam("shopperId", shopperId)
		.when().get(baseURL+"/shoppers/{shopperId}/wishlist")
		.then().assertThat().statusCode(200).log().all();
	}
	
	//@Test(priority=5)
	public void deleteProductFromWishlist()
	{
		RestAssured.given().header("Authorization", "Bearer "+token).pathParam("shopperId", shopperId)
		.pathParam("productId", productId)
		.when().delete(baseURL+"/shoppers/{shopperId}/wishlist/{productId}")
		.then().assertThat().statusCode(204).log().all();
	}
	
	
	//cart module capturing itemId and storing it as a global variable
	@Test(priority=3)
	public void addProductToCart() {
		
		pojo.AddProductToCart pc=new pojo.AddProductToCart();
		pc.setProductId(productId);
		pc.setQuantity(1);
		itemId=RestAssured.given().body(pc).contentType(ContentType.JSON)
		.pathParam("shopperId", shopperId).header("Authorization","Bearer " +token)
		.when().post(baseURL+"/shoppers/{shopperId}/carts").jsonPath().get("data.itemId");
		System.out.println(itemId);
	}
	
	@Test(priority=4)
	public void getAllProductsFromCart() {
		RestAssured.given().log().all().header("Authorization", "Bearer "+token).pathParam("shopperId", shopperId)
			.when().get(baseURL+"/shoppers/{shopperId}/carts")
			.then().assertThat().statusCode(200).log().all();
		}
	@Test(priority=5)
	public void updateProductInCart() {
		UpdateProductInCart up = new UpdateProductInCart();
		up.setProductId(productId);
		up.setQuantity(25);
		
		RestAssured.given().body(up).log().all().contentType(ContentType.JSON)
		.header("Authorization", "Bearer "+token).pathParam("itemId", itemId)
		.pathParam("shopperId", shopperId)
		.when().put(baseURL+"/shoppers/{shopperId}/carts/{itemId}")
		.then().assertThat().statusCode(200).log().all();
	}
	
	//@Test(priority=6)
	public void deleteCartFromProduct() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("productId", productId).pathParam("shopperId", shopperId)
		.when().delete(baseURL+"/shoppers/{shopperId}/carts/{productId}")
		.then().assertThat().statusCode(200).log().all();
	}
	
	//address module 
	@Test(priority=7)
	public void addAddress()
	{
		AddAddress ad = new AddAddress();
		ad.setBuildingInfo("Third Floor");
		ad.setCity("Bangalore");
		ad.setCountry("India");
		ad.setLandmark("mosque");
		ad.setName("Nishad Mansion");
		ad.setPhone("+91 9742112026");
		ad.setPincode("560010");
		ad.setState("Karnataka");
		ad.setStreetInfo("3rd cross, JHBCS Layout");
		ad.setType("Home");
		
		addressId=RestAssured.given().body(ad).contentType(ContentType.JSON)
			.header("Authorization", "Bearer "+token).pathParam("shopperId", shopperId)
			.when().post(baseURL+"/shoppers/{shopperId}/address").jsonPath().get("data.addressId");
			System.out.println(addressId);
	}
	
	//place order
	@Test(priority=7)
	public void placeOrder() {
		
		//internel object members
		OrderAdd add = new OrderAdd();
		add.setAddressId(addressId);
		//external object members
		PlaceOrderData po = new PlaceOrderData();
		po.setAddress(add);
		po.setPaymentMode("COD");
		
		orderId=RestAssured.given().body(po).log().all().contentType(ContentType.JSON)
		 .header("Authorization", "Bearer "+token).accept(ContentType.JSON)
		 .pathParam("shopperId", shopperId)
		 .when().post(baseURL+"/shoppers/{shopperId}/orders").jsonPath().get("data.orderId");
		  System.out.println(orderId);	
	}
	
	//@Test(priority=8)
	public void displayOrderHistory() {
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("shopperId", shopperId)
		.when().get(baseURL+"/shoppers/{shopperId}/orders")
		.then().assertThat().statusCode(200).log().all();	
	}
	
	//@Test(priority=9)
	public void generateInvoiceCopy() {
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("shopperId", shopperId).pathParam("orderId", orderId)
		.when().get(baseURL+"/shoppers/{shopperId}/orders/{orderId}/invoice")
		.then().assertThat().statusCode(200).log().all();	
	}
	
	//@Test(priority=10)
	public void updateOrderStatus() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("shopperId", shopperId).pathParam("orderId", orderId)
		//query parameter
		.queryParam("status", "DELIVERED")
		.when().patch(baseURL+"/shoppers/{shopperId}/orders/{orderId}")
		.then().assertThat().statusCode(200).log().all();	
	}
	
	//add & delete review
	@Test(priority=8)
	public void addReview( ) {
		
		AddReview ar = new AddReview();
		ar.setDescription("Good Cream");
		ar.setHeading("Amazing Product");
		ar.setRating(0);
		ar.setShopperId(shopperId);
		ar.setShopperName("SHOPPER");
		
		reviewId=RestAssured.given().body(ar).log().all().contentType(ContentType.JSON)
		.header("Authorization", "Bearer "+token)
		.queryParam("productId", productId)
		.when().post(baseURL+"/reviews").jsonPath().get("data.reviewId");
		 System.out.println(reviewId);	
	}
	
	@Test(priority=9)
	public void deleteReview() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
					.queryParam("productId", productId)
					.pathParam("reviewId", reviewId)
					.when().delete(baseURL+"/reviews/{reviewId}")
					.then().assertThat().statusCode(200);
			}
	
	//deletedAddress to avoid load on database and dummy data
	@Test(priority=10)
	public void deleteAddress() {
		
		RestAssured.given().log().all().header("Authorization", "Bearer "+token)
		.pathParam("shopperId", shopperId)
		.pathParam("addressId", addressId)
		.when().delete(baseURL+"/shoppers/{shopperId}/address/{addressId}")
		.then().assertThat().statusCode(204);
	}
}

