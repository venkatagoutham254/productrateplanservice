package aforo.productrateplanservie.rate_plan_usage_based;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import aforo.productrateplanservie.config.BaseIT;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;


public class RatePlanUsageBasedResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanUsageBasedData.sql"})
    void getAllRatePlanUsageBaseds_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanUsageBaseds")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanUsageBasedDTOList.get(0).ratePlanUsageRateId", Matchers.equalTo(1300))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanUsageBaseds?page=0&size=20&sort=ratePlanUsageRateId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanUsageBasedData.sql"})
    void getAllRatePlanUsageBaseds_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanUsageBaseds?filter=1301")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanUsageBasedDTOList.get(0).ratePlanUsageRateId", Matchers.equalTo(1301));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanUsageBasedData.sql"})
    void getRatePlanUsageBased_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanUsageBaseds/1300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanUsageDescription", Matchers.equalTo("Consectetuer adipiscing."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanUsageBaseds/1300"));
    }

    @Test
    void getRatePlanUsageBased_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlanUsageBaseds/1966")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void createRatePlanUsageBased_success() throws IOException {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanUsageBasedDTORequest.json"))
                .when()
                    .post("/api/ratePlanUsageBaseds")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanUsageBasedRepository.count());
    }

    @Test
    void createRatePlanUsageBased_missingField() throws IOException {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanUsageBasedDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlanUsageBaseds")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanUsageDescription"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanUsageBasedData.sql"})
    void updateRatePlanUsageBased_success() throws IOException {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanUsageBasedDTORequest.json"))
                .when()
                    .put("/api/ratePlanUsageBaseds/1300")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlanUsageBaseds/1300"));
        assertEquals("Sed diam voluptua.", ratePlanUsageBasedRepository.findById(((long)1300)).orElseThrow().getRatePlanUsageDescription());
        assertEquals(2, ratePlanUsageBasedRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql", "/data/ratePlanUsageBasedData.sql"})
    void deleteRatePlanUsageBased_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlanUsageBaseds/1300")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanUsageBasedRepository.count());
    }

}
