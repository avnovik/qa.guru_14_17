package in.reqres;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReqresInTests {

    @Test
    void getUserSingleTest() {
        RestAssured.baseURI = "https://reqres.in";

        given()
                .log().uri()
                .log().method()
                .when()
                .get("/api/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    void getListUserTest() {
        RestAssured.baseURI = "https://reqres.in";

        given()
                .log().uri()
                .log().method()
                .when()
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.id", hasItems(7, 8, 9));
    }

    @Test
    void postCreateUserTest() {
        RestAssured.baseURI = "https://reqres.in";

        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "morpheus");
        jsonAsMap.put("job", "leader");

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType("application/json").
                body(jsonAsMap).
                when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().status()
                .log().body();
    }

    @Test
    void postLoginUserTest() {
        RestAssured.baseURI = "https://reqres.in";

//        given()
//                .auth().preemptive().basic("eve.holt@reqres.in", "cityslicka")
//                .when().post("/api/login")
//                .then().statusCode(200);
    }
}
