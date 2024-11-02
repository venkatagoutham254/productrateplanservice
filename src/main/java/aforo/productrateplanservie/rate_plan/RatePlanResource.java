package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.model.SimpleValue;
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
@RequestMapping(value = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanResource {
    private final RatePlanService ratePlanService;
    private final RatePlanAssembler ratePlanAssembler;
    private final PagedResourcesAssembler<RatePlanDTO> pagedResourcesAssembler;

    public RatePlanResource(final RatePlanService ratePlanService, final RatePlanAssembler ratePlanAssembler,
                            final PagedResourcesAssembler<RatePlanDTO> pagedResourcesAssembler) {
        this.ratePlanService = ratePlanService;
        this.ratePlanAssembler = ratePlanAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

	@Operation(parameters = {
			@Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
			@Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
			@Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class)) })
	@GetMapping("/ratePlans")
	public ResponseEntity<PagedModel<EntityModel<RatePlanDTO>>> getAllRatePlans(
			@RequestParam(name = "filter", required = false) final String filter,
			@Parameter(hidden = true) @SortDefault(sort = "ratePlanId") @PageableDefault(size = 20) final Pageable pageable) {
		final Page<RatePlanDTO> ratePlanDTOs = ratePlanService.findAll(filter, pageable);

		return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanDTOs, ratePlanAssembler));
	}

	@Operation(parameters = {
			@Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
			@Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
			@Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))})
	@GetMapping("/products/{productId}/ratePlans")
	public ResponseEntity<PagedModel<EntityModel<RatePlanDTO>>> getAllRatePlansByProductId(
			@RequestParam(name = "filter", required = false, defaultValue = "productId") final String filter,
			@PathVariable("productId") Long productId,
			@Parameter(hidden = true) @SortDefault(sort = "ratePlanId") @PageableDefault(size = 20) final Pageable pageable) {
		Page<RatePlanDTO> ratePlanDTOs = ratePlanService.getRatePlansByProductId(productId, filter, pageable);

		return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanDTOs, ratePlanAssembler));
	}

	@GetMapping("ratePlans/{ratePlanId}")
	public ResponseEntity<EntityModel<RatePlanDTO>> getRatePlan(
			@PathVariable(name = "ratePlanId") final Long ratePlanId) {
		final RatePlanDTO ratePlanDTO = ratePlanService.get(ratePlanId);

		return ResponseEntity.ok(ratePlanAssembler.toModel(ratePlanDTO));
	}

	@PostMapping("/products/{productId}/ratePlans")
	@ApiResponse(responseCode = "201")
	public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlan(
			@PathVariable("productId") Long productId,
			@RequestBody @Valid final  CreateRatePlanRequest createRatePlanRequest) {
		final Long createdRatePlanId = ratePlanService.create(productId, createRatePlanRequest);

		return new ResponseEntity<>(ratePlanAssembler.toSimpleModel(createdRatePlanId), HttpStatus.CREATED);
	}

	@PutMapping("/ratePlans/{ratePlanId}")
	public ResponseEntity<EntityModel<SimpleValue<Long>>> updateRatePlan(
			@PathVariable(name = "ratePlanId") final Long ratePlanId,
			@RequestBody @Valid final CreateRatePlanRequest createRatePlanRequest) {
		ratePlanService.update(ratePlanId, createRatePlanRequest);

		return ResponseEntity.ok(ratePlanAssembler.toSimpleModel(ratePlanId));
	}

    @DeleteMapping("/ratePlans/{ratePlanId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlan(@PathVariable(name = "ratePlanId") final Long ratePlanId) {
//    Commented out the referenced warning as we enabled the CASCADE.REMOVE at entity level
//    final ReferencedWarning referencedWarning = ratePlanService.getReferencedWarning(ratePlanId);
//
//    if (referencedWarning != null) {
//        throw new ReferencedException(referencedWarning);
//    }

        ratePlanService.delete(ratePlanId);
        return ResponseEntity.noContent().build();
    }

}
