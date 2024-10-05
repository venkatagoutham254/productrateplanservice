package aforo.rateplanservie.rest;

import aforo.rateplanservie.model.RatePlanSubscriptionRateDTO;
import aforo.rateplanservie.model.SimpleValue;
import aforo.rateplanservie.service.RatePlanSubscriptionRateService;
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
@RequestMapping(value = "/api/ratePlanSubscriptionRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanSubscriptionRateResource {

    private final RatePlanSubscriptionRateService ratePlanSubscriptionRateService;
    private final RatePlanSubscriptionRateAssembler ratePlanSubscriptionRateAssembler;
    private final PagedResourcesAssembler<RatePlanSubscriptionRateDTO> pagedResourcesAssembler;

    public RatePlanSubscriptionRateResource(
            final RatePlanSubscriptionRateService ratePlanSubscriptionRateService,
            final RatePlanSubscriptionRateAssembler ratePlanSubscriptionRateAssembler,
            final PagedResourcesAssembler<RatePlanSubscriptionRateDTO> pagedResourcesAssembler) {
        this.ratePlanSubscriptionRateService = ratePlanSubscriptionRateService;
        this.ratePlanSubscriptionRateAssembler = ratePlanSubscriptionRateAssembler;
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
    public ResponseEntity<PagedModel<EntityModel<RatePlanSubscriptionRateDTO>>> getAllRatePlanSubscriptionRates(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "ratePlanSubscriptionRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanSubscriptionRateDTO> ratePlanSubscriptionRateDTOs = ratePlanSubscriptionRateService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanSubscriptionRateDTOs, ratePlanSubscriptionRateAssembler));
    }

    @GetMapping("/{ratePlanSubscriptionRateId}")
    public ResponseEntity<EntityModel<RatePlanSubscriptionRateDTO>> getRatePlanSubscriptionRate(
            @PathVariable(name = "ratePlanSubscriptionRateId") final Integer ratePlanSubscriptionRateId) {
        final RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO = ratePlanSubscriptionRateService.get(ratePlanSubscriptionRateId);
        return ResponseEntity.ok(ratePlanSubscriptionRateAssembler.toModel(ratePlanSubscriptionRateDTO));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> createRatePlanSubscriptionRate(
            @RequestBody @Valid final RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        final Integer createdRatePlanSubscriptionRateId = ratePlanSubscriptionRateService.create(ratePlanSubscriptionRateDTO);
        return new ResponseEntity<>(ratePlanSubscriptionRateAssembler.toSimpleModel(createdRatePlanSubscriptionRateId), HttpStatus.CREATED);
    }

    @PutMapping("/{ratePlanSubscriptionRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Integer>>> updateRatePlanSubscriptionRate(
            @PathVariable(name = "ratePlanSubscriptionRateId") final Integer ratePlanSubscriptionRateId,
            @RequestBody @Valid final RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        ratePlanSubscriptionRateService.update(ratePlanSubscriptionRateId, ratePlanSubscriptionRateDTO);
        return ResponseEntity.ok(ratePlanSubscriptionRateAssembler.toSimpleModel(ratePlanSubscriptionRateId));
    }

    @DeleteMapping("/{ratePlanSubscriptionRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanSubscriptionRate(
            @PathVariable(name = "ratePlanSubscriptionRateId") final Integer ratePlanSubscriptionRateId) {
        final ReferencedWarning referencedWarning = ratePlanSubscriptionRateService.getReferencedWarning(ratePlanSubscriptionRateId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        ratePlanSubscriptionRateService.delete(ratePlanSubscriptionRateId);
        return ResponseEntity.noContent().build();
    }

}
