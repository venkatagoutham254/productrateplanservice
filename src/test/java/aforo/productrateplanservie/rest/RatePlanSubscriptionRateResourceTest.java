package aforo.productrateplanservie.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class RatePlanSubscriptionRateResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanSubscriptionRateData.sql"})
    void getAllRatePlanSubscriptionRates_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanSubscriptionRates")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanSubscriptionRateDTOList.get(0).ratePlanSubscriptionRateId", Matchers.equalTo(1900))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanSubscriptionRates?page=0&size=20&sort=ratePlanSubscriptionRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanSubscriptionRateData.sql"})
    void getAllRatePlanSubscriptionRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanSubscriptionRates?filter=1901")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanSubscriptionRateDTOList.get(0).ratePlanSubscriptionRateId", Matchers.equalTo(1901));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanSubscriptionRateData.sql"})
    void getRatePlanSubscriptionRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanSubscriptionRates/1900")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanSubscriptionDescription", Matchers.equalTo("No sea takimata."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanSubscriptionRates/1900"));
    }

    @Test
    void getRatePlanSubscriptionRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanSubscriptionRates/2566")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanSubscriptionRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanSubscriptionRateDTORequest.json"))
                .when()
                    .post("/api/ratePlanSubscriptionRates")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanSubscriptionRateRepository.count());
    }

    @Test
    void createRatePlanSubscriptionRate_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanSubscriptionRateDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlanSubscriptionRates")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanSubscriptionDescription"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanSubscriptionRateData.sql"})
    void updateRatePlanSubscriptionRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanSubscriptionRateDTORequest.json"))
                .when()
                    .put("/api/ratePlanSubscriptionRates/1900")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanSubscriptionRates/1900"));
        assertEquals("Consetetur sadipscing.", ratePlanSubscriptionRateRepository.findById(1900).orElseThrow().getRatePlanSubscriptionDescription());
        assertEquals(2, ratePlanSubscriptionRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanSubscriptionRateData.sql"})
    void deleteRatePlanSubscriptionRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanSubscriptionRates/1900")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanSubscriptionRateRepository.count());
    }

}
