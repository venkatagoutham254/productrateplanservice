package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductAPI;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductAPIRepository;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.request.CreateProductAPIRequest;
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
    public ProductAPI update(Long productId, CreateProductAPIRequest request) {
        ProductAPI existing = productAPIRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product API not found with ID: " + productId));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.API);

        existing.setEndpointUrl(request.getEndpointUrl());
        existing.setAuthType(request.getAuthType());
        existing.setPayloadSizeMetric(request.getPayloadSizeMetric());
        existing.setRateLimitPolicy(request.getRateLimitPolicy());
        existing.setMeteringGranularity(request.getMeteringGranularity());
        existing.setGrouping(request.getGrouping());
        existing.setCachingFlag(request.isCachingFlag());
        existing.setLatencyClass(request.getLatencyClass());

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
