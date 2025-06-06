package aforo.productrateplanservice.product.resource;

import aforo.productrateplanservice.product.dto.ProductDTO;
import aforo.productrateplanservice.product.entity.ProductAPI;
import aforo.productrateplanservice.product.entity.ProductFlatFile;
import aforo.productrateplanservice.product.entity.ProductLLMToken;
import aforo.productrateplanservice.product.entity.ProductSQLResult;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;
import aforo.productrateplanservice.product.request.CreateProductFlatFileRequest;
import aforo.productrateplanservice.product.request.CreateProductLLMTokenRequest;
import aforo.productrateplanservice.product.request.CreateProductRequest;
import aforo.productrateplanservice.product.request.CreateProductSQLResultRequest;
import aforo.productrateplanservice.product.request.UpdateProductAPIRequest;
import aforo.productrateplanservice.product.request.UpdateProductFlatFileRequest;
import aforo.productrateplanservice.product.request.UpdateProductLLMTokenRequest;
import aforo.productrateplanservice.product.request.UpdateProductRequest;
import aforo.productrateplanservice.product.request.UpdateProductSQLResultRequest;
import aforo.productrateplanservice.product.service.ProductService;
import aforo.productrateplanservice.product.service.ProductAPIService;
import aforo.productrateplanservice.product.service.ProductFlatFileService;
import aforo.productrateplanservice.product.service.ProductSQLResultService;
import aforo.productrateplanservice.product.service.ProductLLMTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;
    private final ProductAPIService productAPIService;
    private final ProductFlatFileService productFlatFileService;
    private final ProductSQLResultService productSQLResultService;
    private final ProductLLMTokenService productLLMTokenService;
    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

   @PutMapping("/{id}")
public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody UpdateProductRequest request) {
    return ResponseEntity.ok(productService.updateProduct(id, request));
}


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
   
    @PostMapping("/api")
    public ResponseEntity<ProductAPI> createProductAPI(@RequestBody CreateProductAPIRequest request) {
        ProductAPI created = productAPIService.createProductAPI(request);
        return ResponseEntity.ok(created);
    }
    
@GetMapping("/api/{id}")
public ResponseEntity<ProductAPI> getProductAPI(@PathVariable Long id) {
    return ResponseEntity.ok(productAPIService.getById(id));
}

@GetMapping("/api")
public ResponseEntity<List<ProductAPI>> getAllProductAPIs() {
    return ResponseEntity.ok(productAPIService.getAll());
}

@PutMapping("/api/{id}")
public ResponseEntity<ProductAPI> updateProductAPI(
        @PathVariable Long id,
        @RequestBody UpdateProductAPIRequest request) {
    return ResponseEntity.ok(productAPIService.update(id, request));
}


@DeleteMapping("/api/{id}")
public ResponseEntity<Void> deleteProductAPI(@PathVariable Long id) {
    productAPIService.delete(id);
    return ResponseEntity.noContent().build();
}
@PostMapping("/flatfile")
public ResponseEntity<ProductFlatFile> createProductFlatFile(@RequestBody CreateProductFlatFileRequest request) {
    ProductFlatFile created = productFlatFileService.create(request);
    return ResponseEntity.ok(created);
}


@GetMapping("/flatfile/{id}")
public ResponseEntity<ProductFlatFile> getProductFlatFile(@PathVariable Long id) {
    return ResponseEntity.ok(productFlatFileService.getById(id));
}

@GetMapping("/flatfile")
public ResponseEntity<List<ProductFlatFile>> getAllProductFlatFiles() {
    return ResponseEntity.ok(productFlatFileService.getAll());
}

@PutMapping("/flatfile/{id}")
public ResponseEntity<ProductFlatFile> updateProductFlatFile(
        @PathVariable Long id,
        @RequestBody UpdateProductFlatFileRequest request) {
    return ResponseEntity.ok(productFlatFileService.update(id, request));
}

@DeleteMapping("/flatfile/{id}")
public ResponseEntity<Void> deleteProductFlatFile(@PathVariable Long id) {
    productFlatFileService.delete(id);
    return ResponseEntity.noContent().build();
}

// Add to ProductResource.java

@PostMapping("/sql-result")
public ResponseEntity<ProductSQLResult> createProductSQLResult(@RequestBody CreateProductSQLResultRequest request) {
    ProductSQLResult created = productSQLResultService.create(request);
    return ResponseEntity.ok(created);
}


@GetMapping("/sql-result/{id}")
public ResponseEntity<ProductSQLResult> getProductSQLResult(@PathVariable Long id) {
    return ResponseEntity.ok(productSQLResultService.getById(id));
}

@GetMapping("/sql-result")
public ResponseEntity<List<ProductSQLResult>> getAllProductSQLResults() {
    return ResponseEntity.ok(productSQLResultService.getAll());
}

@PutMapping("/sql-result/{id}")
public ResponseEntity<ProductSQLResult> updateProductSQLResult(
        @PathVariable Long id,
        @RequestBody UpdateProductSQLResultRequest request) {
    return ResponseEntity.ok(productSQLResultService.update(id, request));
}


@DeleteMapping("/sql-result/{id}")
public ResponseEntity<Void> deleteProductSQLResult(@PathVariable Long id) {
    productSQLResultService.delete(id);
    return ResponseEntity.noContent().build();
}
@PostMapping("/llm-token")
public ResponseEntity<ProductLLMToken> createProductLLMToken(@RequestBody CreateProductLLMTokenRequest request) {
    ProductLLMToken created = productLLMTokenService.create(request);
    return ResponseEntity.ok(created);
}


@GetMapping("/llm-token/{id}")
public ResponseEntity<ProductLLMToken> getProductLLMToken(@PathVariable Long id) {
    return ResponseEntity.ok(productLLMTokenService.getById(id));
}

@GetMapping("/llm-token")
public ResponseEntity<List<ProductLLMToken>> getAllProductLLMTokens() {
    return ResponseEntity.ok(productLLMTokenService.getAll());
}

@PutMapping("/llm-token/{id}")
public ResponseEntity<ProductLLMToken> updateProductLLMToken(
        @PathVariable Long id,
        @RequestBody UpdateProductLLMTokenRequest request) {
    return ResponseEntity.ok(productLLMTokenService.update(id, request));
}


@DeleteMapping("/llm-token/{id}")
public ResponseEntity<Void> deleteProductLLMToken(@PathVariable Long id) {
    productLLMTokenService.delete(id);
    return ResponseEntity.noContent().build();
}

}
