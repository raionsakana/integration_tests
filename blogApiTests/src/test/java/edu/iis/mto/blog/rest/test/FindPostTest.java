package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.rmi.Remote;

public class FindPostTest extends FunctionalTests {

    private static final String CONFIRMED_API = "blog/user/1/post";
    private static final String CONFIRMED_API_WITHOUT_POSTS = "blog/user/4/post";
    private static final String REMOVED_API = "blog/user/3/post";
    private static final String NOT_EXISTING_API = "blog/user/145/post";

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

    @Test
    public void findPostByConfirmedUserWithoutPosts() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(CONFIRMED_API_WITHOUT_POSTS)
            .then()
            .body("size()", is(0));
    }

    @Test
    public void findPostByRemovedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .get(REMOVED_API);
    }

    @Test
    public void findPostByNotExistingUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .get(NOT_EXISTING_API);
    }
}