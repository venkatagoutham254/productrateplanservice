package aforo.rateplanservie.rest;

import aforo.rateplanservie.model.RatePlanTieredRateDTO;
import aforo.rateplanservie.model.SimpleValue;
import aforo.rateplanservie.service.RatePlanTieredRateService;
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
@RequestMapping(value = "/api/ratePlanTieredRates", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<RatePlanTieredRateDTO>>> getAllRatePlanTieredRates(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanTieredRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanTieredRateDTO> ratePlanTieredRateDTOs = ratePlanTieredRateService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanTieredRateDTOs, ratePlanTieredRateAssembler));
    }

    @GetMapping("/{ratePlanTieredRateId}")
    public ResponseEntity<EntityModel<RatePlanTieredRateDTO>> getRatePlanTieredRate(
            @PathVariable(name = "ratePlanTieredRateId") final Integer ratePlanTieredRateId) {
        final RatePlanTieredRateDTO ratePlanTieredRateDTO = ratePlanTieredRateService.get(ratePlanTieredRateId);
        return ResponseEntity.ok(ratePlanTieredRateAssembler.toModel(ratePlanTieredRateDTO));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> createRatePlanTieredRate(
            @RequestBody @Valid final RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        final Integer createdRatePlanTieredRateId = ratePlanTieredRateService.create(ratePlanTieredRateDTO);
        return new ResponseEntity<>(ratePlanTieredRateAssembler.toSimpleModel(createdRatePlanTieredRateId), HttpStatus.CREATED);
    }

    @PutMapping("/{ratePlanTieredRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> updateRatePlanTieredRate(
            @PathVariable(name = "ratePlanTieredRateId") final Integer ratePlanTieredRateId,
            @RequestBody @Valid final RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        ratePlanTieredRateService.update(ratePlanTieredRateId, ratePlanTieredRateDTO);
        return ResponseEntity.ok(ratePlanTieredRateAssembler.toSimpleModel(ratePlanTieredRateId));
    }

    @DeleteMapping("/{ratePlanTieredRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanTieredRate(
            @PathVariable(name = "ratePlanTieredRateId") final Integer ratePlanTieredRateId) {
        final ReferencedWarning referencedWarning = ratePlanTieredRateService.getReferencedWarning(ratePlanTieredRateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ratePlanTieredRateService.delete(ratePlanTieredRateId);
        return ResponseEntity.noContent().build();
    }

}
