package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

public class AddPostTest extends FunctionalTests {

    private static final String CONFIRMED_API = "blog/user/1/post";
    private static final String NEW_API = "blog/user/2/post";
    private static final String REMOVED_API = "blog/user/2/post";
    private static final String NOT_EXISTING_API = "blog/user/145/post";

    private JSONObject jsonObj = new JSONObject().put("entry", "post test AddPostTest");

    @Test
    public void addPostByConfirmedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(jsonObj.toString())
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_CREATED)
            .when()
            .post(CONFIRMED_API);
    }

    @Test
    public void addPostByNewUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(jsonObj.toString())
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(NEW_API);
    }

    @Test
    public void addPostByRemovedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(jsonObj.toString())
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(REMOVED_API);
    }

    @Test
    public void addPostByNotExistingUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .body(jsonObj.toString())
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(NOT_EXISTING_API);
    }

}
