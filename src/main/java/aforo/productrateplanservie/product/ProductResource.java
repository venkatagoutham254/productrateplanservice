package aforo.productrateplanservie.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.exception.ReferencedException;
import aforo.productrateplanservie.exception.ReferencedWarning;

@RestController
@Tag(name = "Products", description = "Operations related to Products")
@RequestMapping(value = "/v1/api/products", produces = MediaType.APPLICATION_JSON_VALUE)
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
							name = "customerId",
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
			@RequestParam(name = "customerId", required = false) final Long customerId,
			@RequestParam(name = "organizationId", required = false) final Long organizationId,
			@RequestParam(name = "divisionId", required = false) final Long divisionId,
			@Parameter(hidden = true) @SortDefault(sort = "productId") @PageableDefault(size = 20) final Pageable pageable) {

		// Fetch paginated products
		final Page<ProductDTO> productDTOs = productService.findAll(filter, customerId, organizationId, divisionId, pageable);

		// Convert to PagedModel using PagedResourcesAssembler
		PagedModel<EntityModel<ProductDTO>> pagedModel = pagedResourcesAssembler.toModel(productDTOs, productAssembler);

		return ResponseEntity.ok(pagedModel);
	}

	@Operation(summary = "Get Product by ID",
			description = "Returns a Product based on the provided ID")
	@ApiResponse(responseCode = "200",
			description = "Successfully retrieved the Product")
	@GetMapping("/{productId}")
	public ResponseEntity<EntityModel<ProductDTO>> getProduct(
			@PathVariable(name = "productId") final Long productId) {
		final ProductDTO productDTO = productService.get(productId);

		return ResponseEntity.ok(productAssembler.toModel(productDTO));
	}

	@Operation(
			summary = "Create new Product",
			description = "Creates a new Product with the provided information"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "201",
					description = "Product created successfully",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiResponse.class)
					)
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input data",
					content = @Content(
							mediaType = MediaType.APPLICATION_JSON_VALUE,
							schema = @Schema(implementation = ApiResponse.class)
					)
			)
	})
	// @PostMapping
	// public ResponseEntity<EntityModel<SimpleValue<Long>>> createProduct(
	// 		@Valid @RequestBody final CreateProductRequest createProductRequest) {
	// 	final Long createdProductId = productService.create(createProductRequest);

	// 	return new ResponseEntity<>(productAssembler.toSimpleModel(createdProductId), HttpStatus.CREATED);
	// }


	
@PostMapping(value = "/productFile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createProduct(
//          @RequestPart (value = "request") final @Valid @JsonProperty CreateProductRequest createProductRequest,
            @RequestPart (value = "request") final @Valid String requestJson,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart(value = "documentFile", required = false) MultipartFile documentFile) throws JsonProcessingException {

        if (file != null) {
            System.out.println("Received file: " + file.getOriginalFilename());
        }
        if (documentFile != null) {
//          log.info("Received documentFile: " + documentFile.getOriginalFilename());
        }

        ObjectMapper objectMapper = new ObjectMapper();
        CreateProductRequest createProductRequest = objectMapper.readValue(requestJson, CreateProductRequest.class);

//      createProductRequest.setFile(file);
        final Long createdProductId = productService.create(createProductRequest, file, documentFile);

        return new ResponseEntity<>(productAssembler.toSimpleModel(createdProductId), HttpStatus.CREATED);
    }




	@Operation(
			summary = "Update existing Product",
			description = "Updates an existing Product's information"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Product updated successfully",
					content = @Content(schema = @Schema(implementation = ApiResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Product not found",
					content = @Content(schema = @Schema(implementation = ApiResponse.class))
			),
			@ApiResponse(
					responseCode = "400",
					description = "Invalid input data",
					content = @Content(schema = @Schema(implementation = ApiResponse.class))
			)
	})
	@PutMapping("/{productId}")
	public ResponseEntity<EntityModel<SimpleValue<Long>>> updateProduct(
			@PathVariable(name = "productId") final Long productId,
			@RequestBody final CreateProductRequest createProductRequest) {
		productService.update(productId, createProductRequest);

		return ResponseEntity.ok(productAssembler.toSimpleModel(productId));
	}

	@Operation(
			summary = "Delete Product",
			description = "Deletes an existing Product by ID"
	)
	@ApiResponses(value = {
			@ApiResponse(
					responseCode = "200",
					description = "Product deleted successfully",
					content = @Content(schema = @Schema(implementation = ApiResponse.class))
			),
			@ApiResponse(
					responseCode = "404",
					description = "Product not found",
					content = @Content(schema = @Schema(implementation = ApiResponse.class))
			)
	})

	@DeleteMapping("/{productId}")
	@ApiResponse(responseCode = "204")
	public ResponseEntity<Void> deleteProduct(
			@PathVariable(name = "productId") final Long productId) {

		productService.delete(productId);

		return ResponseEntity.noContent().build();
	}

	@GetMapping("product/count")
	public ResponseEntity<Long> getProductCount() {
		long count = productService.getProductCount();
		return ResponseEntity.ok(count);
	}
}
