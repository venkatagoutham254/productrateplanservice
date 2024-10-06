package aforo.productrateplanservie.rate_plan_freemium_rate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
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
                    .body("_embedded.ratePlanFreemiumRateDTOList.get(0).ratePlanFreemiumRateId", Matchers.equalTo(2100))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates?page=0&size=20&sort=ratePlanFreemiumRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void getAllRatePlanFreemiumRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates?filter=2101")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanFreemiumRateDTOList.get(0).ratePlanFreemiumRateId", Matchers.equalTo(2101));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void getRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates/2100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanFreemiumDescription", Matchers.equalTo("Eget est lorem."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates/2100"));
    }

    @Test
    void getRatePlanFreemiumRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanFreemiumRates/2766")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanFreemiumRate_success() throws IOException {
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
    void createRatePlanFreemiumRate_missingField() throws IOException {
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
    void updateRatePlanFreemiumRate_success() throws IOException {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanFreemiumRateDTORequest.json"))
                .when()
                    .put("/api/ratePlanFreemiumRates/2100")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanFreemiumRates/2100"));
        assertEquals("Consectetuer adipiscing.", ratePlanFreemiumRateRepository.findById(((long)2100)).orElseThrow().getRatePlanFreemiumDescription());
        assertEquals(2, ratePlanFreemiumRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanFreemiumRateData.sql"})
    void deleteRatePlanFreemiumRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanFreemiumRates/2100")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanFreemiumRateRepository.count());
    }

}
