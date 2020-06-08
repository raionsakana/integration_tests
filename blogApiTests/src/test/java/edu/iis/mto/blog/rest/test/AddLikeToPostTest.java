package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class AddLikeToPostTest extends FunctionalTests {

    private static final String CONFIRMED_API = "blog/user/4/like/1";
    private static final String OWNER_API = "blog/user/1/like/1";

    private JSONObject jsonObj = new JSONObject().put("entry", "post test AddPostTest");

    @Test
    public void addLikeToPostByConfirmedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(jsonObj.toString())
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .post(CONFIRMED_API);
    }
    
}