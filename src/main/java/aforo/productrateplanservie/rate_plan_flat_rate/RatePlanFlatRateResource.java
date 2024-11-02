package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.exception.ReferencedException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/rateplans/flatrates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanFlatRateResource {

    private final RatePlanFlatRateService ratePlanFlatRateService;
    private final RatePlanFlatRateAssembler ratePlanFlatRateAssembler;
    private final PagedResourcesAssembler<RatePlanFlatRateDTO> pagedResourcesAssembler;

    @Autowired
    public RatePlanFlatRateResource(final RatePlanFlatRateService ratePlanFlatRateService,
                                    final RatePlanFlatRateAssembler ratePlanFlatRateAssembler,
                                    final PagedResourcesAssembler<RatePlanFlatRateDTO> pagedResourcesAssembler) {
        this.ratePlanFlatRateService = ratePlanFlatRateService;
        this.ratePlanFlatRateAssembler = ratePlanFlatRateAssembler;
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
    public ResponseEntity<PagedModel<EntityModel<RatePlanFlatRateDTO>>> getAllRatePlanFlatRates(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanFlatRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanFlatRateDTO> ratePlanFlatRateDTOs = ratePlanFlatRateService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler));
    }

    @Operation(
            summary = "Get all RatePlanFlatRates by RatePlan ID",
            description = "Retrieves all RatePlanFlatRates associated with the specified RatePlan ID."
    )
    @GetMapping("/ratePlan/{ratePlanId}")
    public ResponseEntity<PagedModel<EntityModel<RatePlanFlatRateDTO>>> getRatePlanFlatRatesByRatePlanId(
            @PathVariable("ratePlanId") final Long ratePlanId,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanFlatRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanFlatRateDTO> ratePlanFlatRateDTOs = ratePlanFlatRateService.findAllByRatePlanId(ratePlanId, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanFlatRateDTOs, ratePlanFlatRateAssembler));
    }

    @GetMapping("/{ratePlanFlatRateId}")
    public ResponseEntity<EntityModel<RatePlanFlatRateDTO>> getRatePlanFlatRate(
            @PathVariable(name = "ratePlanFlatRateId") final Long ratePlanFlatRateId) {
        final RatePlanFlatRateDTO ratePlanFlatRateDTO = ratePlanFlatRateService.get(ratePlanFlatRateId);
        return ResponseEntity.ok(ratePlanFlatRateAssembler.toModel(ratePlanFlatRateDTO));
    }

    @PostMapping("/{ratePlanId}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlanFlatRate(
            @PathVariable("ratePlanId") Long ratePlanId,
            @RequestBody @Valid final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final Long createdRatePlanFlatRateId = ratePlanFlatRateService.create(ratePlanId, ratePlanFlatRateDTO);
        return new ResponseEntity<>(ratePlanFlatRateAssembler.toSimpleModel(createdRatePlanFlatRateId), HttpStatus.CREATED);
    }

    @PutMapping("/{ratePlanFlatRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateRatePlanFlatRate(
            @PathVariable(name = "ratePlanFlatRateId") final Long ratePlanFlatRateId,
            @RequestBody @Valid final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        ratePlanFlatRateService.update(ratePlanFlatRateId, ratePlanFlatRateDTO);
        return ResponseEntity.ok(ratePlanFlatRateAssembler.toSimpleModel(ratePlanFlatRateId));
    }

    @DeleteMapping("/{ratePlanFlatRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanFlatRate(
            @PathVariable(name = "ratePlanFlatRateId") final Long ratePlanFlatRateId) {
        final ReferencedWarning referencedWarning = ratePlanFlatRateService.getReferencedWarning(ratePlanFlatRateId);
        if (referencedWarning != null && referencedWarning.hasWarnings()) { // Assuming hasWarnings() checks for any warnings
            throw new ReferencedException(referencedWarning);
        }
        ratePlanFlatRateService.delete(ratePlanFlatRateId);
        return ResponseEntity.noContent().build();
    }
}
