package aforo.productrateplanservie.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class CurrenciesResourceTest extends BaseIT {

    @Test
    @Sql("/data/currenciesData.sql")
    void getAllCurrenciess_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.currenciesDTOList.get(0).currencyId", Matchers.equalTo("a96e0a04-d20f-3096-bc64-dac2d639a577"))
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess?page=0&size=20&sort=currencyId,asc"));
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void getAllCurrenciess_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess?filter=b8bff625-bdb0-3939-92c9-d4db0c6bbe45")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.currenciesDTOList.get(0).currencyId", Matchers.equalTo("b8bff625-bdb0-3939-92c9-d4db0c6bbe45"));
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void getCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("currencyCode", Matchers.equalTo("No"))
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess/a96e0a04-d20f-3096-bc64-dac2d639a577"));
    }

    @Test
    void getCurrencies_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess/23a93ba8-9a5b-3c6c-a26e-49b88973f46e")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/currenciesDTORequest.json"))
                .when()
                    .post("/api/currenciess")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, currenciesRepository.count());
    }

    @Test
    void createCurrencies_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/currenciesDTORequest_missingField.json"))
                .when()
                    .post("/api/currenciess")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("currencyCode"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void updateCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/currenciesDTORequest.json"))
                .when()
                    .put("/api/currenciess/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess/a96e0a04-d20f-3096-bc64-dac2d639a577"));
        assertEquals("Con", currenciesRepository.findById(UUID.fromString("a96e0a04-d20f-3096-bc64-dac2d639a577")).orElseThrow().getCurrencyCode());
        assertEquals(2, currenciesRepository.count());
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void deleteCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/currenciess/a96e0a04-d20f-3096-bc64-dac2d639a577")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, currenciesRepository.count());
    }

}
