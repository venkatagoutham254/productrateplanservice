package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.ProductFlatFile;
import aforo.productrateplanservice.product.request.CreateProductFlatFileRequest;

import java.util.List;

public interface ProductFlatFileService {
    ProductFlatFile create(CreateProductFlatFileRequest request);
    ProductFlatFile getById(Long id);
    List<ProductFlatFile> getAll();
    ProductFlatFile update(Long id, CreateProductFlatFileRequest request);
    void delete(Long id);
}
