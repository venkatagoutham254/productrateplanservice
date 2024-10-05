package aforo.productrateplanservie.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                    .body("_embedded.currenciesDTOList.get(0).currencyId", Matchers.equalTo(1100))
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess?page=0&size=20&sort=currencyId,asc"));
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void getAllCurrenciess_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess?filter=1101")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.currenciesDTOList.get(0).currencyId", Matchers.equalTo(1101));
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void getCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("currencyCode", Matchers.equalTo("No"))
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess/1100"));
    }

    @Test
    void getCurrencies_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/currenciess/1766")
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
                    .put("/api/currenciess/1100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/currenciess/1100"));
        assertEquals("Con", currenciesRepository.findById(1100).orElseThrow().getCurrencyCode());
        assertEquals(2, currenciesRepository.count());
    }

    @Test
    @Sql("/data/currenciesData.sql")
    void deleteCurrencies_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/currenciess/1100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, currenciesRepository.count());
    }

}
