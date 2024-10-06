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


public class RatePlanResourceTest extends BaseIT {

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void getAllRatePlans_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlans")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.ratePlanDTOList.get(0).ratePlanId", Matchers.equalTo("a92d0103-08a6-3379-9a3d-9c728ee74244"))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlans?page=0&size=20&sort=ratePlanId,asc"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void getAllRatePlans_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlans?filter=b801e5d4-da87-3c39-9782-741cd794002d")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.ratePlanDTOList.get(0).ratePlanId", Matchers.equalTo("b801e5d4-da87-3c39-9782-741cd794002d"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void getRatePlan_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlans/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("ratePlanName", Matchers.equalTo("Sed ut perspiciatis."))
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlans/a92d0103-08a6-3379-9a3d-9c728ee74244"));
    }

    @Test
    void getRatePlan_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/ratePlans/23de10ad-baa1-32ee-93f7-7f679fa1483a")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql"})
    void createRatePlan_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanDTORequest.json"))
                .when()
                    .post("/api/ratePlans")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, ratePlanRepository.count());
    }

    @Test
    void createRatePlan_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanDTORequest_missingField.json"))
                .when()
                    .post("/api/ratePlans")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("ratePlanName"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void updateRatePlan_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/ratePlanDTORequest.json"))
                .when()
                    .put("/api/ratePlans/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/ratePlans/a92d0103-08a6-3379-9a3d-9c728ee74244"));
        assertEquals("Nulla facilisis.", ratePlanRepository.findById(UUID.fromString("a92d0103-08a6-3379-9a3d-9c728ee74244")).orElseThrow().getRatePlanName());
        assertEquals(2, ratePlanRepository.count());
    }

    @Test
    @Sql({"/data/productData.sql", "/data/currenciesData.sql", "/data/ratePlanData.sql"})
    void deleteRatePlan_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/ratePlans/a92d0103-08a6-3379-9a3d-9c728ee74244")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, ratePlanRepository.count());
    }

}
