package aforo.productrateplanservice.product.resource;

import aforo.productrateplanservice.product.dto.ProductDTO;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;
import aforo.productrateplanservice.product.request.CreateProductFlatFileRequest;
import aforo.productrateplanservice.product.request.CreateProductLLMTokenRequest;
import aforo.productrateplanservice.product.request.CreateProductRequest;
import aforo.productrateplanservice.product.request.CreateProductSQLResultRequest;
import aforo.productrateplanservice.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductResource {

    private final ProductService productService;

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
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/api")
public ResponseEntity<Void> createProductAPI(@RequestBody CreateProductAPIRequest request) {
    productService.createProductAPI(request);
    return ResponseEntity.ok().build();
}

@PostMapping("/flatfile")
public ResponseEntity<Void> createProductFlatFile(@RequestBody CreateProductFlatFileRequest request) {
    productService.createProductFlatFile(request);
    return ResponseEntity.ok().build();
}

@PostMapping("/sqlresult")
public ResponseEntity<Void> createProductSQLResult(@RequestBody CreateProductSQLResultRequest request) {
    productService.createProductSQLResult(request);
    return ResponseEntity.ok().build();
}

@PostMapping("/llmtoken")
public ResponseEntity<Void> createProductLLMToken(@RequestBody CreateProductLLMTokenRequest request) {
    productService.createProductLLMToken(request);
    return ResponseEntity.ok().build();
}

}
