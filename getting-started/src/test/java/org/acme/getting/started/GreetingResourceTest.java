package org.acme.getting.started;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import org.hamcrest.CoreMatchers;

//buscar mas info de esta libreria para realizar test de servicio rest
import static io.restassured.RestAssured.*;

@QuarkusTest
@QuarkusTestResource(HelloQuarkusTestResourceLifeCycleManager.class)
public class GreetingResourceTest {

    @Test
    public void should_return_hello(){
        given()
        .when()
        .get("/hello")
        .then()
        .statusCode(200)
        .body(CoreMatchers.is("aloha test"));
    }

    @Test
    public void should_return_20_in_calculate(){
        given()
        .when()
        .get("/calculate")
        .then()
        .statusCode(200)
        .body(CoreMatchers.is("20"));
    }
}