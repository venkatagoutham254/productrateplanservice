package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductLLMToken;
import aforo.productrateplanservice.product.request.CreateProductLLMTokenRequest;

import java.util.List;

public interface ProductLLMTokenService {
    ProductLLMToken create(CreateProductLLMTokenRequest request);
    ProductLLMToken getById(Long id);
    List<ProductLLMToken> getAll();
    ProductLLMToken update(Long id, CreateProductLLMTokenRequest request);
    void delete(Long id);
}
