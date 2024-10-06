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


public class RatePlanFlatRateResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void getAllRatePlanFlatRates_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanFlatRateDTOList.get(0).ratePlanFlatRateId", Matchers.equalTo("a9e00f2f-4bfc-3b75-85cb-641066f2859b"))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates?page=0&size=20&sort=ratePlanFlatRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void getAllRatePlanFlatRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates?filter=b8231a7c-e4ba-389d-93b7-22cc5c955834")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanFlatRateDTOList.get(0).ratePlanFlatRateId", Matchers.equalTo("b8231a7c-e4ba-389d-93b7-22cc5c955834"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void getRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates/a9e00f2f-4bfc-3b75-85cb-641066f2859b")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanFlatDescription", Matchers.equalTo("Commodo consequat."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates/a9e00f2f-4bfc-3b75-85cb-641066f2859b"));
    }

    @Test
    void getRatePlanFlatRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates/23b1fb02-964a-364e-a57f-9f26a31f72cf")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFlatRateDTORequest.json"))
                .when()
                    .post("/api/ratePlanFlatRates")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanFlatRateRepository.count());
    }

    @Test
    void createRatePlanFlatRate_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFlatRateDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlanFlatRates")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanFlatDescription"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void updateRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFlatRateDTORequest.json"))
                .when()
                    .put("/api/ratePlanFlatRates/a9e00f2f-4bfc-3b75-85cb-641066f2859b")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates/a9e00f2f-4bfc-3b75-85cb-641066f2859b"));
        assertEquals("Stet clita kasd.", ratePlanFlatRateRepository.findById(UUID.fromString("a9e00f2f-4bfc-3b75-85cb-641066f2859b")).orElseThrow().getRatePlanFlatDescription());
        assertEquals(2, ratePlanFlatRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void deleteRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanFlatRates/a9e00f2f-4bfc-3b75-85cb-641066f2859b")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanFlatRateRepository.count());
    }

}
