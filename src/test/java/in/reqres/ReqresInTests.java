package in.reqres;

import in.reqres.data.UserData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReqresInTests {

    @BeforeAll
    static void baseUri() {
        RestAssured.baseURI = "https://reqres.in";
    }

    @Test
    void getUserSingleTest() {

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
    void getListUserWithUserDataTest() {
        List<UserData> users = given()
                .when()
                .contentType(JSON)
                .get("/api/users?page=2")
                .then()
                .log().status()
                .log().body()
                .extract().body().jsonPath().getList("data", UserData.class);

        Assertions.assertTrue(users.stream().allMatch(n -> n.getAvatar().endsWith(".jpg")));
    }

    @Test
    void postCreateUserTest() {
        Map<String, Object> jsonAsMap = new HashMap<>();
        jsonAsMap.put("name", "morpheus");
        jsonAsMap.put("job", "leader");

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(jsonAsMap)
                .when()
                .contentType(JSON)
                .post("/api/users")
                .then()
                .statusCode(201)
                .log().status()
                .log().body();
    }

    @Test
    void putNewUserTest() {
        String body = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(body)
                .when()
                .contentType(JSON)
                .put("/api/users/2")
                .then().log().body()
                .statusCode(200)
                .body("name", is("morpheus"),
                        "job", is("zion resident"),
                        "updatedAt", notNullValue());
    }

    @Test
    void deleteUserTest() {
        given()
                .log().uri()
                .log().method()
                .delete("/api/users/2")
                .then()
                .statusCode(204)
                .log().status();
    }
}
