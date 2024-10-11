package aforo.productrateplanservie.product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;
import aforo.productrateplanservie.util.ReferencedWarning;
@Service
public class ProductServiceImpl implements ProductService {
	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final RatePlanRepository ratePlanRepository;
	private final RestTemplate restTemplate;
	public ProductServiceImpl(final ProductRepository productRepository,
			final ProductMapper productMapper, final RatePlanRepository ratePlanRepository, final RestTemplate restTemplate) {
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.ratePlanRepository = ratePlanRepository;
		this.restTemplate = restTemplate;
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
	public Long create(final ProductDTO productDTO) {
		Long producerId = fetchProducerIdFromMicroservice();
		Long organizationId = fetchOrganizationIdFromMicroservice();
		Long divisionId = fetchDivisionIdFromMicroservice();
		validateId(producerId, "producer");
		validateId(organizationId, "organization");
		validateId(divisionId, "division");
		final Product product = new Product();
		productMapper.updateProduct(productDTO, product);
		return productRepository.save(product).getProductId();
	}
	private void validateId(Long id, String entityType) {
		if (id == null) {
			throw new IllegalArgumentException(entityType + " ID cannot be null");
		}
		String url = String.format("http://localhost:8080/api/validate/%s/%d", entityType, id);
		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || !"ACTIVE".equals(response.getBody())) {
			throw new NotFoundException(entityType + " ID is inactive or not found");
		}
	}
	@Override
	public void update(final Long productId, final ProductDTO productDTO) {
		Long producerId = fetchProducerIdFromMicroservice();
		Long organizationId = fetchOrganizationIdFromMicroservice();
		Long divisionId = fetchDivisionIdFromMicroservice();
		validateId(producerId, "producer");
		validateId(organizationId, "organization");
		validateId(divisionId, "division");
		final Product product = productRepository.findById(productId)
				.orElseThrow(() -> new NotFoundException("Product not found with ID: " + productId));
		productMapper.updateProduct(productDTO, product);
		productRepository.save(product);
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
}
