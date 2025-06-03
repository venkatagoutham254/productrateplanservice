package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductSQLResult;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.repository.ProductSQLResultRepository;
import aforo.productrateplanservice.product.request.CreateProductSQLResultRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductSQLResultServiceImpl implements ProductSQLResultService {

    private final ProductSQLResultRepository productSQLResultRepository;
    private final ProductRepository productRepository;

    private void validateProductType(Product product, ProductType expected) {
        if (product.getProductType() != expected) {
            throw new RuntimeException("Invalid product type. Expected: " + expected + ", but got: " + product.getProductType());
        }
    }

    @Override
    public ProductSQLResult create(CreateProductSQLResultRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.SQLResult);

        ProductSQLResult entity = ProductSQLResult.builder()
                .product(product)
                .queryTemplate(request.getQueryTemplate())
                .dbType(request.getDbType())
                .resultSize(request.getResultSize())
                .freshness(request.getFreshness())
                .executionFrequency(request.getExecutionFrequency())
                .expectedRowRange(request.getExpectedRowRange())
                .isCached(request.isCached())
                .joinComplexity(request.getJoinComplexity())
                .build();

        return productSQLResultRepository.save(entity);
    }

    @Override
    public ProductSQLResult getById(Long id) {
        return productSQLResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SQL Result Product not found with ID: " + id));
    }

    @Override
    public List<ProductSQLResult> getAll() {
        return productSQLResultRepository.findAll();
    }

    @Override
    public ProductSQLResult update(Long id, CreateProductSQLResultRequest request) {
        ProductSQLResult existing = productSQLResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("SQL Result Product not found with ID: " + id));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.SQLResult);

        existing.setQueryTemplate(request.getQueryTemplate());
        existing.setDbType(request.getDbType());
        existing.setResultSize(request.getResultSize());
        existing.setFreshness(request.getFreshness());
        existing.setExecutionFrequency(request.getExecutionFrequency());
        existing.setExpectedRowRange(request.getExpectedRowRange());
        existing.setCached(request.isCached());
        existing.setJoinComplexity(request.getJoinComplexity());

        return productSQLResultRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!productSQLResultRepository.existsById(id)) {
            throw new RuntimeException("SQL Result Product not found with ID: " + id);
        }
        productSQLResultRepository.deleteById(id);
    }
}
