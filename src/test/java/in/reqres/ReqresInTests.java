package in.reqres;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class ReqresInTests {

    @Test
    public void getUserSingleTest() {
        String uriLink = "https://reqres.in/api/users/2";

        given()
                .log().uri()
                .log().method()
                .when()
                .get(uriLink)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.email", equalTo("janet.weaver@reqres.in"));
    }

    @Test
    public void getListUserTest() {
        String uriLink = "https://reqres.in/api/users?page=2";

        given()
                .log().uri()
                .log().method()
                .when()
                .get(uriLink)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .assertThat()
                .body("data.id", hasItems(7, 8, 9));
    }


}
