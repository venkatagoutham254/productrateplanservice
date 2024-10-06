package aforo.productrateplanservie.rate_plan_flat_rate.rest;

import aforo.productrateplanservie.model.SimpleValue;
import aforo.productrateplanservie.rate_plan_flat_rate.model.RatePlanFlatRateDTO;
import aforo.productrateplanservie.rate_plan_flat_rate.service.RatePlanFlatRateService;
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
@RequestMapping(value = "/api/ratePlanFlatRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanFlatRateResource {

    private final RatePlanFlatRateService ratePlanFlatRateService;
    private final RatePlanFlatRateAssembler ratePlanFlatRateAssembler;
    private final PagedResourcesAssembler<RatePlanFlatRateDTO> pagedResourcesAssembler;

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

    @GetMapping("/{ratePlanFlatRateId}")
    public ResponseEntity<EntityModel<RatePlanFlatRateDTO>> getRatePlanFlatRate(
            @PathVariable(name = "ratePlanFlatRateId") final Long ratePlanFlatRateId) {
        final RatePlanFlatRateDTO ratePlanFlatRateDTO = ratePlanFlatRateService.get(ratePlanFlatRateId);
        return ResponseEntity.ok(ratePlanFlatRateAssembler.toModel(ratePlanFlatRateDTO));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlanFlatRate(
            @RequestBody @Valid final RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        final Long createdRatePlanFlatRateId = ratePlanFlatRateService.create(ratePlanFlatRateDTO);
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
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ratePlanFlatRateService.delete(ratePlanFlatRateId);
        return ResponseEntity.noContent().build();
    }

}
