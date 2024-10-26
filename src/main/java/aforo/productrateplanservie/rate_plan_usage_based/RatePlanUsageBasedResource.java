package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRateDTO;
import aforo.productrateplanservie.util.ReferencedException;
import aforo.productrateplanservie.util.ReferencedWarning;
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
@RequestMapping(value = "/api/ratePlanUsageBaseds", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanUsageBasedResource {

    private final RatePlanUsageBasedService ratePlanUsageBasedService;
    private final RatePlanUsageBasedAssembler ratePlanUsageBasedAssembler;
    private final PagedResourcesAssembler<RatePlanUsageBasedDTO> pagedResourcesAssembler;

    public RatePlanUsageBasedResource(final RatePlanUsageBasedService ratePlanUsageBasedService,
            final RatePlanUsageBasedAssembler ratePlanUsageBasedAssembler,
            final PagedResourcesAssembler<RatePlanUsageBasedDTO> pagedResourcesAssembler) {
        this.ratePlanUsageBasedService = ratePlanUsageBasedService;
        this.ratePlanUsageBasedAssembler = ratePlanUsageBasedAssembler;
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
    public ResponseEntity<PagedModel<EntityModel<RatePlanUsageBasedDTO>>> getAllRatePlanUsageBaseds(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanUsageRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanUsageBasedDTO> ratePlanUsageBasedDTOs = ratePlanUsageBasedService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanUsageBasedDTOs, ratePlanUsageBasedAssembler));
    }

    @GetMapping("/{ratePlanUsageRateId}")
    public ResponseEntity<EntityModel<RatePlanUsageBasedDTO>> getRatePlanUsageBased(
            @PathVariable(name = "ratePlanUsageRateId") final Long ratePlanUsageRateId) {
        final RatePlanUsageBasedDTO ratePlanUsageBasedDTO = ratePlanUsageBasedService.get(ratePlanUsageRateId );
        return ResponseEntity.ok(ratePlanUsageBasedAssembler.toModel(ratePlanUsageBasedDTO));
    }

    @PostMapping("/{ratePlanId}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlanUsageBased(
            @PathVariable("ratePlanId") Long ratePlanId,
            @RequestBody @Valid final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        final Long createdRatePlanUsageRateId = ratePlanUsageBasedService.create(ratePlanId,ratePlanUsageBasedDTO);
        return new ResponseEntity<>(ratePlanUsageBasedAssembler.toSimpleModel(createdRatePlanUsageRateId), HttpStatus.CREATED);
    }


    @PutMapping("/{ratePlanUsageRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateRatePlanUsageBased(
            @PathVariable(name = "ratePlanUsageRateId") final Long ratePlanUsageRateId,
            @RequestBody @Valid final RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        ratePlanUsageBasedService.update(ratePlanUsageRateId, ratePlanUsageBasedDTO);
        return ResponseEntity.ok(ratePlanUsageBasedAssembler.toSimpleModel(ratePlanUsageRateId));
    }

    @DeleteMapping("/{ratePlanUsageRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanUsageBased(
            @PathVariable(name = "ratePlanUsageRateId") final Long ratePlanUsageRateId) {
//        final ReferencedWarning referencedWarning = ratePlanUsageBasedService.getReferencedWarning(ratePlanUsageRateId);
//        if (referencedWarning != null) {
//            throw new ReferencedException(referencedWarning);
//        }
        ratePlanUsageBasedService.delete(ratePlanUsageRateId);
        return ResponseEntity.noContent().build();
    }

}
