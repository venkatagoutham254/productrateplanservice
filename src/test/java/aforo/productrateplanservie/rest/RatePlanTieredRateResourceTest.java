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
                    .body("_embedded.ratePlanTieredRateDTOList.get(0).ratePlanTieredRateId", Matchers.equalTo("a9a53013-58b9-3cbe-baa4-5b1ceea088c6"))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates?page=0&size=20&sort=ratePlanTieredRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void getAllRatePlanTieredRates_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates?filter=b8bdfd0d-fa22-33fc-a726-6376887f549b")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanTieredRateDTOList.get(0).ratePlanTieredRateId", Matchers.equalTo("b8bdfd0d-fa22-33fc-a726-6376887f549b"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void getRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates/a9a53013-58b9-3cbe-baa4-5b1ceea088c6")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanTieredDescription", Matchers.equalTo("No sea takimata."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates/a9a53013-58b9-3cbe-baa4-5b1ceea088c6"));
    }

    @Test
    void getRatePlanTieredRate_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanTieredRates/23e05616-c8ed-3594-a3f9-af00b142dd6f")
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
                    .put("/api/ratePlanTieredRates/a9a53013-58b9-3cbe-baa4-5b1ceea088c6")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanTieredRates/a9a53013-58b9-3cbe-baa4-5b1ceea088c6"));
        assertEquals("Consetetur sadipscing.", ratePlanTieredRateRepository.findById(UUID.fromString("a9a53013-58b9-3cbe-baa4-5b1ceea088c6")).orElseThrow().getRatePlanTieredDescription());
        assertEquals(2, ratePlanTieredRateRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanTieredRateData.sql"})
    void deleteRatePlanTieredRate_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanTieredRates/a9a53013-58b9-3cbe-baa4-5b1ceea088c6")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanTieredRateRepository.count());
    }

}
