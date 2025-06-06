package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductAPI;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;
import aforo.productrateplanservice.product.request.UpdateProductAPIRequest;

import java.util.List;

public interface ProductAPIService {
    ProductAPI createProductAPI(CreateProductAPIRequest request);
    ProductAPI getById(Long productId);
    List<ProductAPI> getAll();
ProductAPI update(Long id, UpdateProductAPIRequest request);
    void delete(Long productId);
}
