package tests;

import org.testng.annotations.Test;
import pageobjectmodel.PageObjects;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ApiTest {
	
	public ApiTest() {
		baseURI="http://www.omdbapi.com";
	}
	
	@Test
	public void checkStatusCode() {
		given().
			param("apikey", PageObjects.API_KEY).
			param("i", getID()).
		when().
			get().
		then().
			statusCode(200);
	}
	
	@Test
	public void checkTitle() {
		given().
			param("apikey", PageObjects.API_KEY).
			param("i", getID()).
		when().
			get().
		then().
			body("$", hasKey("Title")).
			body("Title.size()", greaterThan(0));
	}
	
	@Test
	public void checkYear() {
		given().
			param("apikey", PageObjects.API_KEY).
			param("i", getID()).
		when().
			get().
		then().
			body("$", hasKey("Year")).
			body("Year.size()", greaterThan(0));
	}
	
	@Test
	public void checkReleased() {
		given().
			param("apikey", PageObjects.API_KEY).
			param("i", getID()).
		when().
			get().
		then().
			body("$", hasKey("Released")).
			body("Released.size()", greaterThan(0));
	}
	
	//function to get imdbID of the movie which is title "Harry Potter and the Sorcerer's Stone" (can be changed on PageObjects.MOVIE_TITLE_FOR_ID) 
	private String getID () {
		String imdbID =
				given().
					param("apikey", PageObjects.API_KEY).
					param("s", PageObjects.MOVIE_TITLE_TO_SEARCH).
				when().
					get().
				then().
					extract().response().path("Search.find{it.Title=='"+PageObjects.MOVIE_TITLE_FOR_ID+"'}.imdbID");
		return imdbID;
	}
}
