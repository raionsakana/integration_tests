package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.rmi.Remote;

public class AddLikeToPostTest extends FunctionalTests {

    private static final String CONFIRMED_API = "blog/user/4/like/1";
    private static final String OWNER_API = "blog/user/1/like/1";
    private static final String NEW_API = "blog/user/2/like/1";
    private static final String REMOVED_API = "blog/user/2/like/1";
    private static final String BLOG_POST_API = "blog/post/1";

    private JSONObject jsonObj = new JSONObject().put("entry", "post test AddPostTest");

    @Test
    public void addLikeToPostByConfirmedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .post(CONFIRMED_API);
    }

    @Test
    public void addLikeToPostByOwner() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(OWNER_API);
    }

    @Test
    public void addLikeToPostByNewUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(NEW_API);
    }

    @Test
    public void addLikeToPostByRemovedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_BAD_REQUEST)
            .when()
            .post(REMOVED_API);
    }

    @Test
    public void addLikeTwiceDoesNotChangeAnythingByTheSameUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .post(CONFIRMED_API);

        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .post(CONFIRMED_API);

        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(BLOG_POST_API)
            .then()
            .body("likesCount", is(equalTo(1)));
    }

}