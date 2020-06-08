package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.rmi.Remote;

public class FindPostTest extends FunctionalTests {

    private static final String CONFIRMED_API = "blog/user/1/post";

    @Test
    public void findPostByConfirmedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(CONFIRMED_API)
            .then()
            .body("size()", is(3));
    }


}