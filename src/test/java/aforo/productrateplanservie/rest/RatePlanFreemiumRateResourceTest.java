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


public class RatePlanFreemiumRateResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void getAllRatePlanFreemiumRates_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanFreemiumRateDTOList.get(0).ratePlanFreemiumRateId", Matchers.equalTo("a9ad8fa4-7bbe-3282-badb-b8de5374b894"))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates?page=0&size=20&sort=ratePlanFreemiumRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void getAllRatePlanFreemiumRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates?filter=b8866e93-cab1-3768-90fe-343c9e7063fb")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanFreemiumRateDTOList.get(0).ratePlanFreemiumRateId", Matchers.equalTo("b8866e93-cab1-3768-90fe-343c9e7063fb"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void getRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates/a9ad8fa4-7bbe-3282-badb-b8de5374b894")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanFreemiumDescription", Matchers.equalTo("Eget est lorem."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates/a9ad8fa4-7bbe-3282-badb-b8de5374b894"));
    }

    @Test
    void getRatePlanFreemiumRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates/235cf83a-b072-3e78-acf7-2d14e44adf98")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFreemiumRateDTORequest.json"))
                .when()
                    .post("/api/ratePlanFreemiumRates")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanFreemiumRateRepository.count());
    }

    @Test
    void createRatePlanFreemiumRate_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFreemiumRateDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlanFreemiumRates")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanFreemiumDescription"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void updateRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFreemiumRateDTORequest.json"))
                .when()
                    .put("/api/ratePlanFreemiumRates/a9ad8fa4-7bbe-3282-badb-b8de5374b894")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates/a9ad8fa4-7bbe-3282-badb-b8de5374b894"));
        assertEquals("Consectetuer adipiscing.", ratePlanFreemiumRateRepository.findById(UUID.fromString("a9ad8fa4-7bbe-3282-badb-b8de5374b894")).orElseThrow().getRatePlanFreemiumDescription());
        assertEquals(2, ratePlanFreemiumRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void deleteRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanFreemiumRates/a9ad8fa4-7bbe-3282-badb-b8de5374b894")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanFreemiumRateRepository.count());
    }

}
