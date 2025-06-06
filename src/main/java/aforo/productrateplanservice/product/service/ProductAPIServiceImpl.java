package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductAPI;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductAPIRepository;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;
import aforo.productrateplanservice.product.request.UpdateProductAPIRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAPIServiceImpl implements ProductAPIService {

    private final ProductAPIRepository productAPIRepository;
    private final ProductRepository productRepository;

    private void validateProductType(Product product, ProductType expected) {
        if (product.getProductType() != expected) {
            throw new RuntimeException("Invalid product type. Expected: " + expected + ", but got: " + product.getProductType());
        }
    }

    @Override
    public ProductAPI createProductAPI(CreateProductAPIRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.API);

        ProductAPI productAPI = ProductAPI.builder()
                .product(product)
                .endpointUrl(request.getEndpointUrl())
                .authType(request.getAuthType())
                .payloadSizeMetric(request.getPayloadSizeMetric())
                .rateLimitPolicy(request.getRateLimitPolicy())
                .meteringGranularity(request.getMeteringGranularity())
                .grouping(request.getGrouping())
                .cachingFlag(request.isCachingFlag())
                .latencyClass(request.getLatencyClass())
                .build();

        return productAPIRepository.save(productAPI);
    }

    @Override
    public ProductAPI getById(Long productId) {
        return productAPIRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product API not found with ID: " + productId));
    }

    @Override
    public List<ProductAPI> getAll() {
        return productAPIRepository.findAll();
    }

   @Override
public ProductAPI update(Long id, UpdateProductAPIRequest request) {
    ProductAPI existing = productAPIRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product API not found"));

    Product product = productRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Product not found"));
    validateProductType(product, ProductType.API);

    if (request.getEndpointUrl() != null) existing.setEndpointUrl(request.getEndpointUrl());
    if (request.getAuthType() != null) existing.setAuthType(request.getAuthType());
    if (request.getPayloadSizeMetric() != null) existing.setPayloadSizeMetric(request.getPayloadSizeMetric());
    if (request.getRateLimitPolicy() != null) existing.setRateLimitPolicy(request.getRateLimitPolicy());
    if (request.getMeteringGranularity() != null) existing.setMeteringGranularity(request.getMeteringGranularity());
    if (request.getGrouping() != null) existing.setGrouping(request.getGrouping());
    if (request.getLatencyClass() != null) existing.setLatencyClass(request.getLatencyClass());
    if (request.getCachingFlag() != null) existing.setCachingFlag(request.getCachingFlag());

    return productAPIRepository.save(existing);
}


    @Override
    public void delete(Long productId) {
        if (!productAPIRepository.existsById(productId)) {
            throw new RuntimeException("Product API not found with ID: " + productId);
        }
        productAPIRepository.deleteById(productId);
    }
}
