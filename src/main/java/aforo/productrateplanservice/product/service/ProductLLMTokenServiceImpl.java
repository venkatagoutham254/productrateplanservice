package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductLLMToken;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductLLMTokenRepository;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.request.CreateProductLLMTokenRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductLLMTokenServiceImpl implements ProductLLMTokenService {

    private final ProductLLMTokenRepository productLLMTokenRepository;
    private final ProductRepository productRepository;

    private void validateProductType(Product product, ProductType expected) {
        if (product.getProductType() != expected) {
            throw new RuntimeException("Invalid product type. Expected: " + expected + ", but got: " + product.getProductType());
        }
    }

    @Override
    public ProductLLMToken create(CreateProductLLMTokenRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.LLMToken);

        ProductLLMToken entity = ProductLLMToken.builder()
                .product(product)
                .tokenProvider(request.getTokenProvider())
                .modelName(request.getModelName())
                .tokenUnitCost(request.getTokenUnitCost())
                .calculationMethod(request.getCalculationMethod())
                .quota(request.getQuota())
                .promptTemplate(request.getPromptTemplate())
                .inferencePriority(request.getInferencePriority())
                .computeTier(request.getComputeTier())
                .build();

        return productLLMTokenRepository.save(entity);
    }

    @Override
    public ProductLLMToken getById(Long id) {
        return productLLMTokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LLM Token Product not found with ID: " + id));
    }

    @Override
    public List<ProductLLMToken> getAll() {
        return productLLMTokenRepository.findAll();
    }

    @Override
    public ProductLLMToken update(Long id, CreateProductLLMTokenRequest request) {
        ProductLLMToken existing = productLLMTokenRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("LLM Token Product not found with ID: " + id));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        validateProductType(product, ProductType.LLMToken);

        existing.setTokenProvider(request.getTokenProvider());
        existing.setModelName(request.getModelName());
        existing.setTokenUnitCost(request.getTokenUnitCost());
        existing.setCalculationMethod(request.getCalculationMethod());
        existing.setQuota(request.getQuota());
        existing.setPromptTemplate(request.getPromptTemplate());
        existing.setInferencePriority(request.getInferencePriority());
        existing.setComputeTier(request.getComputeTier());

        return productLLMTokenRepository.save(existing);
    }

    @Override
    public void delete(Long id) {
        if (!productLLMTokenRepository.existsById(id)) {
            throw new RuntimeException("LLM Token Product not found with ID: " + id);
        }
        productLLMTokenRepository.deleteById(id);
    }
}
