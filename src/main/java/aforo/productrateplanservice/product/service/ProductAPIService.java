package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductAPI;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;

import java.util.List;

public interface ProductAPIService {
    ProductAPI createProductAPI(CreateProductAPIRequest request);
    ProductAPI getById(Long productId);
    List<ProductAPI> getAll();
    ProductAPI update(Long productId, CreateProductAPIRequest request);
    void delete(Long productId);
}
