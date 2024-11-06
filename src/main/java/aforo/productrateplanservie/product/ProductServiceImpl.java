package aforo.productrateplanservie.product;
import aforo.productrateplanservie.exception.ValidationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.exception.ReferencedWarning;

import java.util.Objects;

@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final RatePlanRepository ratePlanRepository;
	private final ProducerClientServiceImpl producerClientServiceImpl;
	public ProductServiceImpl(final ProductRepository productRepository,
                              final ProductMapper productMapper, final RatePlanRepository ratePlanRepository, ProducerClientServiceImpl producerClientServiceImpl) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.ratePlanRepository = ratePlanRepository;
        this.producerClientServiceImpl = producerClientServiceImpl;
    }

	@Override
	public Page<ProductDTO> findAll(final String filter, final Long producerId,
			final Long organizationId, final Long divisionId,
			final Pageable pageable) {
		Page<Product> page;
		if (producerId != null && organizationId != null && divisionId != null) {
			page = productRepository.findAllByProducerIdAndOrganizationIdAndDivisionId(
					producerId, organizationId, divisionId, pageable);
		} else if (producerId != null) {
			page = productRepository.findAllByProducerId(producerId, pageable);
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
				page = productRepository.findAll(pageable);  // No filters, get all products
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
	public Long create(final CreateProductRequest createProductRequest) {
		validateCreateRequest(createProductRequest);
		validateProducerDetails(createProductRequest);

		final Product product = new Product();
		ProductDTO productDTO = productMapper.createProductRequestToProductDTO(createProductRequest);
		productMapper.updateProduct(productDTO, product);

		return productRepository.save(product).getProductId();
	}

	private void validateCreateRequest(CreateProductRequest createProductRequest) {
		if (createProductRequest.getProductName() == null) {
			throw new ValidationException("ProducerName is required");
		}

		if (createProductRequest.getProducerId() == null) {
			throw new ValidationException("Producer ID is required");
		}

		if (createProductRequest.getOrganizationId() == null && createProductRequest.getDivisionId() == null) {
			throw new ValidationException("Either organizationId or divisionId required");
		}
	}

	@Override
	public void update(final Long productId, final CreateProductRequest createProductRequest) {

		final Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
		validateProducerDetails(createProductRequest);
		ProductDTO productDTO = productMapper.updateProductDTO(product, new ProductDTO());
		boolean isModified = false;

		if (createProductRequest.getProductName() != null &&
				!Objects.equals(product.getProductName(), createProductRequest.getProductName())) {
			productDTO.setProductName(createProductRequest.getProductName());
			isModified = true;
		}

		if (createProductRequest.getProductDescription() != null &&
		 !Objects.equals(product.getProductDescription(), createProductRequest.getProductDescription())) {
			productDTO.setProductDescription(createProductRequest.getProductDescription());
			isModified = true;
		}

		if (createProductRequest.getStatus() != null &&
				!Objects.equals(product.getStatus(), createProductRequest.getStatus())) {
			productDTO.setStatus(createProductRequest.getStatus());
			isModified = true;
		}

		if (createProductRequest.getProducerId() != null &&
				!Objects.equals(product.getProducerId(), createProductRequest.getProducerId())) {
			productDTO.setProducerId(createProductRequest.getProducerId());
			isModified = true;
		}

		if (createProductRequest.getOrganizationId() != null &&
				!Objects.equals(product.getOrganizationId(), createProductRequest.getOrganizationId())) {
			productDTO.setOrganizationId(createProductRequest.getOrganizationId());
			isModified = true;
		}

		if (createProductRequest.getDivisionId() != null &&
				!Objects.equals(product.getDivisionId(), createProductRequest.getDivisionId())) {
			productDTO.setDivisionId(createProductRequest.getDivisionId());
			isModified = true;
		}

		if (isModified) {
			productMapper.updateProduct(productDTO, product);
			productRepository.save(product);
		}
	}
	/**
	 * This method is a placeholder for fetching the Producer ID.
	 * 
	 * Currently, it returns a dummy Producer ID (123L). Once the Producer 
	 * microservice is developed and available, this method should be 
	 * updated to make an API call to that microservice and dynamically fetch 
	 * the Producer ID.
	 * 
	 * Future Enhancement:
	 * - Implement logic to call the Producer microservice and retrieve the
	 *   actual Producer ID based on business logic or requirements.
	 * - Handle any exceptions or errors that may occur during the API call.
	 * 
	 * @return Long - the dummy Producer ID (for now)
	 */
	private Long fetchProducerIdFromMicroservice() {
		return 123L; // Dummy Producer ID; update this to dynamically fetch as needed
	}
	/**
	 * This method is a placeholder for fetching the Producer ID.
	 * 
	 * Currently, it returns a dummy Producer ID (123L). Once the Producer 
	 * microservice is developed and available, this method should be 
	 * updated to make an API call to that microservice and dynamically fetch 
	 * the Producer ID.
	 * 
	 * Future Enhancement:
	 * - Implement logic to call the Producer microservice and retrieve the
	 *   actual Organization ID based on business logic or requirements.
	 * - Handle any exceptions or errors that may occur during the API call.
	 * 
	 * @return Long - the dummy Producer ID (for now)
	 */
	private Long fetchOrganizationIdFromMicroservice() {
		return 456L; // Dummy Organization ID; update this to dynamically fetch as needed
	}
	/**
	 * This method is a placeholder for fetching the Producer ID.
	 * 
	 * Currently, it returns a dummy Producer ID (123L). Once the Producer 
	 * microservice is developed and available, this method should be 
	 * updated to make an API call to that microservice and dynamically fetch 
	 * the Producer ID.
	 * 
	 * Future Enhancement:
	 * - Implement logic to call the Producer microservice and retrieve the
	 *   actual Division ID based on business logic or requirements.
	 * - Handle any exceptions or errors that may occur during the API call.
	 * 
	 * @return Long - the dummy Producer ID (for now)
	 */
	private Long fetchDivisionIdFromMicroservice() {
		return 101L; // Dummy Division ID; update this to dynamically fetch as needed
	}
	@Override
	public void delete(final Long productId) {
		// Ensure the product exists before attempting to delete it
		if (!productRepository.existsById(productId)) {
			throw new NotFoundException("Product not found with ID: " + productId);
		}

		// Perform deletion logic here (clean up related entities if necessary)
		productRepository.deleteById(productId);
	}
	@Override
	public ReferencedWarning getReferencedWarning(final Long productId) {
		final ReferencedWarning referencedWarning = new ReferencedWarning();
		final Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
		final RatePlan productRatePlan = ratePlanRepository.findFirstByProduct(product);
		if (productRatePlan != null) {
			referencedWarning.setKey("product.ratePlan.product.referenced");
			referencedWarning.addParam(productRatePlan.getRatePlanId());
			return referencedWarning;
		}
		return null;
	}

	private void validateProducerDetails(CreateProductRequest createProductRequest) {
		if (createProductRequest.getProducerId() != null) {
			producerClientServiceImpl.validateProducerId(createProductRequest.getProducerId());

			if (createProductRequest.getOrganizationId() != null) {
				producerClientServiceImpl.validateOrganizationId(createProductRequest.getOrganizationId());
			}

			if (createProductRequest.getDivisionId() != null) {
				producerClientServiceImpl.validateDivisionId(createProductRequest.getDivisionId());
			}
		}
	}

}
