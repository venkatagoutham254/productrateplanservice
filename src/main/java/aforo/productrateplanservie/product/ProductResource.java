package aforo.productrateplanservie.product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.util.ReferencedException;
import aforo.productrateplanservie.util.ReferencedWarning;
@RestController
@RequestMapping(value = "/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductResource {
	private final ProductService productService;
	private final ProductAssembler productAssembler;
	private final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler;
	public ProductResource(final ProductService productService,
			final ProductAssembler productAssembler,
			final PagedResourcesAssembler<ProductDTO> pagedResourcesAssembler) {
		this.productService = productService;
		this.productAssembler = productAssembler;
		this.pagedResourcesAssembler = pagedResourcesAssembler;
	}
	@Operation(
			parameters = {
					@Parameter(
							name = "producerId",
							in = ParameterIn.QUERY,
							schema = @Schema(implementation = Long.class)
							),
					@Parameter(
							name = "organizationId",
							in = ParameterIn.QUERY,
							schema = @Schema(implementation = Long.class)
							),
					@Parameter(
							name = "divisionId",
							in = ParameterIn.QUERY,
							schema = @Schema(implementation = Long.class)
							),
					@Parameter(
							name = "filter",
							in = ParameterIn.QUERY,
							schema = @Schema(implementation = String.class)
							)
			}
			)
	@GetMapping
	public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> getAllProducts(
			@RequestParam(name = "filter", required = false) final String filter,
			@RequestParam(name = "producerId", required = false) final Long producerId,
			@RequestParam(name = "organizationId", required = false) final Long organizationId,
			@RequestParam(name = "divisionId", required = false) final Long divisionId,
			@Parameter(hidden = true) @SortDefault(sort = "productId") @PageableDefault(size = 20) final Pageable pageable) {

		// Fetch paginated products
		final Page<ProductDTO> productDTOs = productService.findAll(filter, producerId, organizationId, divisionId, pageable);

		// Convert to PagedModel using PagedResourcesAssembler
		PagedModel<EntityModel<ProductDTO>> pagedModel = pagedResourcesAssembler.toModel(productDTOs, productAssembler);

		return ResponseEntity.ok(pagedModel);
	}
	@GetMapping("/{productId}")
	public ResponseEntity<EntityModel<ProductDTO>> getProduct(
			@PathVariable(name = "productId") final Long productId) {
		final ProductDTO productDTO = productService.get(productId);
		return ResponseEntity.ok(productAssembler.toModel(productDTO));
	}
	@PostMapping
	@ApiResponse(responseCode = "201")
	public ResponseEntity<EntityModel<SimpleValue<Long>>> createProduct(
			@RequestBody @Valid final ProductDTO productDTO) {
		final Long createdProductId = productService.create(productDTO);
		return new ResponseEntity<>(productAssembler.toSimpleModel(createdProductId), HttpStatus.CREATED);
	}

	@PutMapping("/{productId}")
	public ResponseEntity<EntityModel<SimpleValue<Long>>> updateProduct(
			@PathVariable(name = "productId") final Long productId,
			@RequestBody @Valid final ProductDTO productDTO) {
		productService.update(productId, productDTO);
		return ResponseEntity.ok(productAssembler.toSimpleModel(productId));
	}
	@DeleteMapping("/{productId}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteProduct(
			@PathVariable(name = "productId") final Long productId) {
		final ReferencedWarning referencedWarning = productService.getReferencedWarning(productId);
		if (referencedWarning != null) {
			throw new ReferencedException(referencedWarning);
		}
		productService.delete(productId);
		return ResponseEntity.noContent().build();
	}
}

