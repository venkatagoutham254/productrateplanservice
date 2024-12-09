package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.exception.ReferencedException;
import aforo.productrateplanservie.exception.ReferencedWarning;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.RestController;


@RestController
@Tag(name = "RatePlanTieredRates", description = "Operations related to RatePlanTieredRates")
@RequestMapping(value = "/v1/api/rateplans", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanTieredRateResource {

    private final RatePlanTieredRateService ratePlanTieredRateService;
    private final RatePlanTieredRateAssembler ratePlanTieredRateAssembler;
    private final PagedResourcesAssembler<RatePlanTieredRateDTO> pagedResourcesAssembler;

    public RatePlanTieredRateResource(final RatePlanTieredRateService ratePlanTieredRateService,
            final RatePlanTieredRateAssembler ratePlanTieredRateAssembler,
            final PagedResourcesAssembler<RatePlanTieredRateDTO> pagedResourcesAssembler) {
        this.ratePlanTieredRateService = ratePlanTieredRateService;
        this.ratePlanTieredRateAssembler = ratePlanTieredRateAssembler;
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
    @GetMapping("/tiered")
    public ResponseEntity<PagedModel<EntityModel<RatePlanTieredRateDTO>>> getAllRatePlanTieredRates(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanTieredRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanTieredRateDTO> ratePlanTieredRateDTOs = ratePlanTieredRateService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanTieredRateDTOs, ratePlanTieredRateAssembler));
    }

    @GetMapping("/tiered/{ratePlanTieredRateId}")
    public ResponseEntity<EntityModel<RatePlanTieredRateDTO>> getRatePlanTieredRate(
            @PathVariable(name = "ratePlanTieredRateId") final Long ratePlanTieredRateId) {
        final RatePlanTieredRateDTO ratePlanTieredRateDTO = ratePlanTieredRateService.get(ratePlanTieredRateId);
        return ResponseEntity.ok(ratePlanTieredRateAssembler.toModel(ratePlanTieredRateDTO));
    }

    @PostMapping("/{ratePlanId}/tiered")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlanTieredRate(
            @PathVariable("ratePlanId") Long ratePlanId,
            @RequestBody @Valid CreateRatePlanTieredRateRequest createRequest) {
        final Long createdRatePlanTieredRateId = ratePlanTieredRateService.create(ratePlanId, createRequest);
        return new ResponseEntity<>(ratePlanTieredRateAssembler.toSimpleModel(createdRatePlanTieredRateId), HttpStatus.CREATED);
    }

    @PutMapping("/{ratePlanId}/tiered/{ratePlanTieredRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateRatePlanTieredRate(
            @PathVariable("ratePlanId") Long ratePlanId,
            @PathVariable("ratePlanTieredRateId") Long ratePlanTieredRateId,
            @RequestBody @Valid UpdateRatePlanTieredRateRequest updateRequest) {
        ratePlanTieredRateService.update(ratePlanId, ratePlanTieredRateId, updateRequest);
        return ResponseEntity.ok(ratePlanTieredRateAssembler.toSimpleModel(ratePlanTieredRateId));
    }


    @DeleteMapping("/tiered/{ratePlanTieredRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanTieredRate(
            @PathVariable(name = "ratePlanTieredRateId") final Long ratePlanTieredRateId) {
//        final ReferencedWarning referencedWarning = ratePlanTieredRateService.getReferencedWarning(ratePlanTieredRateId);
//        if (referencedWarning != null) {
//            throw new ReferencedException(referencedWarning);
//        }
        ratePlanTieredRateService.delete(ratePlanTieredRateId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("TieredRate/count")
    public ResponseEntity<Long> getRatePlanTieredRateCount() {
        long count = ratePlanTieredRateService.getRatePlanTieredRateCount();
        return ResponseEntity.ok(count);
    }
}
