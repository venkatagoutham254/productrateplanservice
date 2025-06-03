package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductSQLResult;
import aforo.productrateplanservice.product.request.CreateProductSQLResultRequest;

import java.util.List;

public interface ProductSQLResultService {
    ProductSQLResult create(CreateProductSQLResultRequest request);
    ProductSQLResult getById(Long id);
    List<ProductSQLResult> getAll();
    ProductSQLResult update(Long id, CreateProductSQLResultRequest request);
    void delete(Long id);
}
