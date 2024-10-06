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


public class ProductResourceTest extends BaseIT {

    @Test
    @Sql("/data/productData.sql")
    void getAllProducts_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/products")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(2))
                    .body("_embedded.productDTOList.get(0).productId", Matchers.equalTo("a9b7ba70-783b-317e-9998-dc4dd82eb3c5"))
                    .body("_links.self.href", Matchers.endsWith("/api/products?page=0&size=20&sort=productId,asc"));
    }

    @Test
    @Sql("/data/productData.sql")
    void getAllProducts_filtered() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/products?filter=b8c37e33-defd-351c-b91e-1e03e51657da")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("page.totalElements", Matchers.equalTo(1))
                    .body("_embedded.productDTOList.get(0).productId", Matchers.equalTo("b8c37e33-defd-351c-b91e-1e03e51657da"));
    }

    @Test
    @Sql("/data/productData.sql")
    void getProduct_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("productName", Matchers.equalTo("Sed diam nonumy."))
                    .body("_links.self.href", Matchers.endsWith("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
    }

    @Test
    void getProduct_notFound() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get("/api/products/23d7c8a0-8b4a-3a1b-87c5-99473f5dddda")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .body("code", Matchers.equalTo("NOT_FOUND"));
    }

    @Test
    void createProduct_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/productDTORequest.json"))
                .when()
                    .post("/api/products")
                .then()
                    .statusCode(HttpStatus.CREATED.value());
        assertEquals(1, productRepository.count());
    }

    @Test
    void createProduct_missingField() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/productDTORequest_missingField.json"))
                .when()
                    .post("/api/products")
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("code", Matchers.equalTo("VALIDATION_FAILED"))
                    .body("fieldErrors.get(0).property", Matchers.equalTo("productName"))
                    .body("fieldErrors.get(0).code", Matchers.equalTo("REQUIRED_NOT_NULL"));
    }

    @Test
    @Sql("/data/productData.sql")
    void updateProduct_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .contentType(ContentType.JSON)
                    .body(readResource("/requests/productDTORequest.json"))
                .when()
                    .put("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("_links.self.href", Matchers.endsWith("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5"));
        assertEquals("Lorem ipsum dolor.", productRepository.findById(UUID.fromString("a9b7ba70-783b-317e-9998-dc4dd82eb3c5")).orElseThrow().getProductName());
        assertEquals(2, productRepository.count());
    }

    @Test
    @Sql("/data/productData.sql")
    void deleteProduct_success() {
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .delete("/api/products/a9b7ba70-783b-317e-9998-dc4dd82eb3c5")
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
        assertEquals(1, productRepository.count());
    }

}
