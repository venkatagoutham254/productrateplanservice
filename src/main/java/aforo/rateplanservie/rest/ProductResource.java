package aforo.rateplanservie.rest;

import aforo.rateplanservie.model.ProductDTO;
import aforo.rateplanservie.model.SimpleValue;
import aforo.rateplanservie.service.ProductService;
import aforo.rateplanservie.util.ReferencedException;
import aforo.rateplanservie.util.ReferencedWarning;
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
                            name = "page",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "size",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = Integer.class)
                    ),
                    @Parameter(
                            name = "sort",
                            in = ParameterIn.QUERY,
                            schema = @Schema(implementation = String.class)
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<ProductDTO>>> getAllProducts(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "productId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<ProductDTO> productDTOs = productService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(productDTOs, productAssembler));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<EntityModel<ProductDTO>> getProduct(
            @PathVariable(name = "productId") final Integer productId) {
        final ProductDTO productDTO = productService.get(productId);
        return ResponseEntity.ok(productAssembler.toModel(productDTO));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> createProduct(
            @RequestBody @Valid final ProductDTO productDTO) {
        final Integer createdProductId = productService.create(productDTO);
        return new ResponseEntity<>(productAssembler.toSimpleModel(createdProductId), HttpStatus.CREATED);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> updateProduct(
            @PathVariable(name = "productId") final Integer productId,
            @RequestBody @Valid final ProductDTO productDTO) {
        productService.update(productId, productDTO);
        return ResponseEntity.ok(productAssembler.toSimpleModel(productId));
    }

    @DeleteMapping("/{productId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteProduct(
            @PathVariable(name = "productId") final Integer productId) {
        final ReferencedWarning referencedWarning = productService.getReferencedWarning(productId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        productService.delete(productId);
        return ResponseEntity.noContent().build();
    }

}
