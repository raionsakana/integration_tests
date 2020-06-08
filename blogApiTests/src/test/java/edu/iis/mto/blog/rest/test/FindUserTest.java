package edu.iis.mto.blog.rest.test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;

import io.restassured.http.ContentType;

import java.rmi.Remote;

public class FindUserTest extends FunctionalTests {

    private static final String FIND_API = "/blog/user/find?searchString=";

    @Test
    public void findUserByName() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "John")
            .then()
            .body("[0].firstName", is("John"));
    }

    @Test
    public void findUserByLastName() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Steward")
            .then()
            .body("[0].lastName", is("Steward"));
    }

}