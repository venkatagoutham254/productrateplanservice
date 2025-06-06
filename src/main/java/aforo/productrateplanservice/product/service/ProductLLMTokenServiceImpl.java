package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.entity.ProductLLMToken;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.repository.ProductLLMTokenRepository;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.product.request.CreateProductLLMTokenRequest;
import aforo.productrateplanservice.product.request.UpdateProductLLMTokenRequest;
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
public ProductLLMToken update(Long id, UpdateProductLLMTokenRequest request) {
    ProductLLMToken existing = productLLMTokenRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("LLM Token not found"));

    if (request.getTokenProvider() != null) existing.setTokenProvider(request.getTokenProvider());
    if (request.getModelName() != null) existing.setModelName(request.getModelName());
    if (request.getTokenUnitCost() != null) existing.setTokenUnitCost(request.getTokenUnitCost());
    if (request.getCalculationMethod() != null) existing.setCalculationMethod(request.getCalculationMethod());
    if (request.getQuota() != null) existing.setQuota(request.getQuota());
    if (request.getPromptTemplate() != null) existing.setPromptTemplate(request.getPromptTemplate());
    if (request.getInferencePriority() != null) existing.setInferencePriority(request.getInferencePriority());
    if (request.getComputeTier() != null) existing.setComputeTier(request.getComputeTier());

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
