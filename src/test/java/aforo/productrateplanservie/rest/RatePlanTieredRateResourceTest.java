package aforo.productrateplanservie.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class RatePlanTieredRateResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void getAllRatePlanTieredRates_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanTieredRateDTOList.get(0).ratePlanTieredRateId", Matchers.equalTo(1500))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates?page=0&size=20&sort=ratePlanTieredRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void getAllRatePlanTieredRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates?filter=1501")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanTieredRateDTOList.get(0).ratePlanTieredRateId", Matchers.equalTo(1501));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void getRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates/1500")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanTieredDescription", Matchers.equalTo("No sea takimata."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates/1500"));
    }

    @Test
    void getRatePlanTieredRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates/2166")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanTieredRateDTORequest.json"))
                .when()
                    .post("/api/ratePlanTieredRates")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanTieredRateRepository.count());
    }

    @Test
    void createRatePlanTieredRate_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanTieredRateDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlanTieredRates")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanTieredDescription"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void updateRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanTieredRateDTORequest.json"))
                .when()
                    .put("/api/ratePlanTieredRates/1500")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates/1500"));
        assertEquals("Consetetur sadipscing.", ratePlanTieredRateRepository.findById(1500).orElseThrow().getRatePlanTieredDescription());
        assertEquals(2, ratePlanTieredRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void deleteRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanTieredRates/1500")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanTieredRateRepository.count());
    }

}
