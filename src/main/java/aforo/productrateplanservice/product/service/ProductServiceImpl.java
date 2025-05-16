package aforo.productrateplanservice.product.service;

import aforo.productrateplanservice.product.dto.ProductDTO;
import aforo.productrateplanservice.product.entity.*;
import aforo.productrateplanservice.product.enums.ProductType;
import aforo.productrateplanservice.product.mapper.ProductMapper;
import aforo.productrateplanservice.product.repository.*;
import aforo.productrateplanservice.product.request.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    private ProductAPIRepository productAPIRepository;

    @Autowired
    private ProductFlatFileRepository productFlatFileRepository;

    @Autowired
    private ProductSQLResultRepository productSQLResultRepository;

    @Autowired
    private ProductLLMTokenRepository productLLMTokenRepository;

    @Override
    public ProductDTO createProduct(CreateProductRequest request) {
        Product product = productMapper.toEntity(request);
        Product saved = productRepository.save(product);
        return productMapper.toDTO(saved);
    }

    @Override
    public ProductDTO getProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));
        return productMapper.toDTO(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long productId, CreateProductRequest request) {
        Product existing = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        Product updated = productMapper.toEntity(request);
        updated.setProductId(productId);
        updated.setCreatedAt(existing.getCreatedAt()); // retain original creation date

        return productMapper.toDTO(productRepository.save(updated));
    }

    @Override
    public void deleteProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new RuntimeException("Product not found with id: " + productId);
        }
        productRepository.deleteById(productId);
    }

    private void validateProductType(Product product, ProductType expectedType) {
        if (product.getProductType() != expectedType) {
            throw new RuntimeException("Invalid product type for this operation. Expected: " + expectedType + ", but found: " + product.getProductType());
        }
    }

    @Override
    public void createProductAPI(CreateProductAPIRequest request) {
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

        productAPIRepository.save(productAPI);
    }

    @Override
    public void createProductFlatFile(CreateProductFlatFileRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        validateProductType(product, ProductType.FlatFile);

        ProductFlatFile entity = ProductFlatFile.builder()
                .product(product)
                .format(request.getFormat())
                .size(request.getSize())
                .deliveryFrequency(request.getDeliveryFrequency())
                .accessMethod(request.getAccessMethod())
                .retentionPolicy(request.getRetentionPolicy())
                .fileNamingConvention(request.getFileNamingConvention())
                .compressionFormat(request.getCompressionFormat())
                .build();

        productFlatFileRepository.save(entity);
    }

    @Override
    public void createProductSQLResult(CreateProductSQLResultRequest request) {
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

        productSQLResultRepository.save(entity);
    }

    @Override
    public void createProductLLMToken(CreateProductLLMTokenRequest request) {
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

        productLLMTokenRepository.save(entity);
    }
} 
