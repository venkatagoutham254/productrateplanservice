package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductSQLResult;
import aforo.productrateplanservice.product.request.CreateProductSQLResultRequest;
import aforo.productrateplanservice.product.request.UpdateProductSQLResultRequest;

import java.util.List;

public interface ProductSQLResultService {
    ProductSQLResult create(CreateProductSQLResultRequest request);
    ProductSQLResult getById(Long id);
    List<ProductSQLResult> getAll();
ProductSQLResult update(Long id, UpdateProductSQLResultRequest request);
    void delete(Long id);
}
