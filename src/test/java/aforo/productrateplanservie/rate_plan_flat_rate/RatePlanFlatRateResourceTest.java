package aforo.productrateplanservie.rate_plan_flat_rate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                    .body("_embedded.ratePlanFlatRateDTOList.get(0).ratePlanFlatRateId", Matchers.equalTo(1700))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates?page=0&size=20&sort=ratePlanFlatRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void getAllRatePlanFlatRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates?filter=1701")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanFlatRateDTOList.get(0).ratePlanFlatRateId", Matchers.equalTo(1701));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void getRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates/1700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanFlatDescription", Matchers.equalTo("Commodo consequat."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates/1700"));
    }

    @Test
    void getRatePlanFlatRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFlatRates/2366")
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
                    .put("/api/ratePlanFlatRates/1700")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFlatRates/1700"));
        assertEquals("Stet clita kasd.", ratePlanFlatRateRepository.findById(((long)1700)).orElseThrow().getRatePlanFlatDescription());
        assertEquals(2, ratePlanFlatRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFlatRateData.sql"})
    void deleteRatePlanFlatRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanFlatRates/1700")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanFlatRateRepository.count());
    }

}
