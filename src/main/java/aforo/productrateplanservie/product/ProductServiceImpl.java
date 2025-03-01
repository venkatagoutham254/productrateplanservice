package aforo.productrateplanservie.product;

import aforo.productrateplanservie.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.exception.NotFoundException;

import java.io.IOException;
import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final RatePlanRepository ratePlanRepository;
    private final CustomerClientServiceImpl customerClientServiceImpl;
    private final S3Service s3Service;

    public ProductServiceImpl(final ProductRepository productRepository,
                              final ProductMapper productMapper, final RatePlanRepository ratePlanRepository,
                              CustomerClientServiceImpl customerClientServiceImpl, S3Service s3Service) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.ratePlanRepository = ratePlanRepository;
        this.customerClientServiceImpl = customerClientServiceImpl;
        this.s3Service = s3Service;
    }

    @Override
    public Page<ProductDTO> findAll(final String filter, final Long customerId,
                                    final Long organizationId, final Long divisionId,
                                    final Pageable pageable) {
        Page<Product> page;
        if (customerId != null && organizationId != null && divisionId != null) {
            page = productRepository.findAllByCustomerIdAndOrganizationIdAndDivisionId(
                    customerId, organizationId, divisionId, pageable);
        } else if (customerId != null) {
            page = productRepository.findAllByCustomerId(customerId, pageable);
        } else if (organizationId != null) {
            page = productRepository.findAllByOrganizationId(organizationId, pageable);
        } else if (divisionId != null) {
            page = productRepository.findAllByDivisionId(divisionId, pageable);
        } else {
            Long longFilter = null;
            try {
                if (filter != null) {
                    longFilter = Long.parseLong(filter);
                }
            } catch (NumberFormatException e) {
                // Ignore invalid filter input
            }

            if (longFilter != null) {
                page = productRepository.findAllByProductId(longFilter, pageable);
            } else {
                page = productRepository.findAll(pageable); // No filters, get all products
            }
        }
        return new PageImpl<>(
                page.getContent()
                        .stream()
                        .map(product -> productMapper.updateProductDTO(product, new ProductDTO()))
                        .toList(),
                pageable, page.getTotalElements()
        );
    }

    @Override
    public ProductDTO get(final Long productId) {
        return productRepository.findById(productId)
                .map(product -> productMapper.updateProductDTO(product, new ProductDTO()))
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
    }

    @Override
    public Long create(final CreateProductRequest createProductRequest, MultipartFile file, MultipartFile documentFile) {
        validateCreateRequest(createProductRequest);
        validateCustomerDetails(createProductRequest);
        final String customerName = createProductRequest.getCustomerName();
        String fileLocation = null;
        String documentFileLocation = null;

        try {
            if (customerName != null) {
                if (file != null) {
                    fileLocation = s3Service.uploadFile(file, customerName);
                }
                if (documentFile != null) {
                    documentFileLocation = s3Service.uploadFile(documentFile, customerName);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (fileLocation != null && !fileLocation.isEmpty()) {
            createProductRequest.setProductFileLocation(fileLocation);
        }

        if (documentFileLocation != null && !documentFileLocation.isEmpty()) {
            createProductRequest.setDocumentationFileLocation(documentFileLocation);
        }

        final Product product = new Product();
        ProductDTO productDTO = productMapper.createProductRequestToProductDTO(createProductRequest);
        productMapper.updateProduct(productDTO, product);

        return productRepository.save(product).getProductId();
    }

    private void validateCreateRequest(CreateProductRequest createProductRequest) {
        if (createProductRequest.getProductName().trim().isEmpty()) {
            throw new ValidationException("CustomerName is required");
        }
    }

    @Override
    public void update(final Long productId, final CreateProductRequest createProductRequest) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));

        ProductDTO productDTO = productMapper.updateProductDTO(product, new ProductDTO());
        boolean isModified = false;

        if (createProductRequest.getProductName() != null && !createProductRequest.getProductName().trim().isEmpty() &&
                !Objects.equals(product.getProductName(), createProductRequest.getProductName())) {
            productDTO.setProductName(createProductRequest.getProductName());
            isModified = true;
        }

        if (createProductRequest.getProductDescription() != null && !createProductRequest.getProductDescription().trim().isEmpty() &&
                !Objects.equals(product.getProductDescription(), createProductRequest.getProductDescription())) {
            productDTO.setProductDescription(createProductRequest.getProductDescription());
            isModified = true;
        }

        if (isModified) {
            productMapper.updateProduct(productDTO, product);
            productRepository.save(product);
        }
    }

    @Override
    public void delete(final Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product not found with ID: " + productId);
        }
        productRepository.deleteById(productId);
    }

    private void validateCustomerDetails(CreateProductRequest createProductRequest) {
        if (createProductRequest.getCustomerId() == null) {
            throw new ValidationException("Customer ID is required");
        }

        if (!customerClientServiceImpl.validateCustomerId(createProductRequest.getCustomerId())) {
            throw new ValidationException("Customer ID not found");
        }
    }

    @Override
    public long getProductCount() {
        return productRepository.count();
    }
}
