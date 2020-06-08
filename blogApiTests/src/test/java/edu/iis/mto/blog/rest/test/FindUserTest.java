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

    @Test
    public void findUserByMail() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "john@domain.com")
            .then()
            .body("[0].email", is("john@domain.com"));
    }

    @Test
    public void findUserByNameNotExistingUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Joanne")
            .then()
            .body("size()", is(0));
    }

    @Test
    public void findUserByLastNameNotExistingUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Trattoria")
            .then()
            .body("size()", is(0));
    }

    @Test
    public void findUserByMailNotExistingUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "trattoriajoanne@domain.com")
            .then()
            .body("size()", is(0));
    }

    @Test
    public void findUserByNamePattern() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Jo")
            .then()
            .body("[0].firstName", is("John"));
    }


    @Test
    public void findUserByLastNamePattern() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Stewa")
            .then()
            .body("[0].lastName", is("Steward"));
    }

    @Test
    public void findUserByMailPattern() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "john@d")
            .then()
            .body("[0].email", is("john@domain.com"));
    }

    @Test
    public void findUserByNameRemovedUser() {
        given().accept(ContentType.JSON)
            .header("Content-Type", "application/json;charset=UTF-8")
            .expect()
            .log()
            .all()
            .statusCode(HttpStatus.SC_OK)
            .when()
            .get(FIND_API + "Adam")
            .then()
            .body("size()", is(0));
    }

}