package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.model.SimpleValue;
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
@RequestMapping(value = "/api/ratePlanFreemiumRates", produces = MediaType.APPLICATION_JSON_VALUE)
public class RatePlanFreemiumRateResource {

    private final RatePlanFreemiumRateService ratePlanFreemiumRateService;
    private final RatePlanFreemiumRateAssembler ratePlanFreemiumRateAssembler;
    private final PagedResourcesAssembler<RatePlanFreemiumRateDTO> pagedResourcesAssembler;

    public RatePlanFreemiumRateResource(final RatePlanFreemiumRateService ratePlanFreemiumRateService, final RatePlanFreemiumRateAssembler ratePlanFreemiumRateAssembler, final PagedResourcesAssembler<RatePlanFreemiumRateDTO> pagedResourcesAssembler) {
        this.ratePlanFreemiumRateService = ratePlanFreemiumRateService;
        this.ratePlanFreemiumRateAssembler = ratePlanFreemiumRateAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Operation(parameters = {@Parameter(name = "page", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)), @Parameter(name = "size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)), @Parameter(name = "sort", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))})
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<RatePlanFreemiumRateDTO>>> getAllRatePlanFreemiumRates(@RequestParam(name = "filter", required = false) final String filter, @Parameter(hidden = true) @SortDefault(sort = "ratePlanFreemiumRateId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<RatePlanFreemiumRateDTO> ratePlanFreemiumRateDTOs = ratePlanFreemiumRateService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(ratePlanFreemiumRateDTOs, ratePlanFreemiumRateAssembler));
    }

    @GetMapping("/{ratePlanFreemiumRateId}")
    public ResponseEntity<EntityModel<RatePlanFreemiumRateDTO>> getRatePlanFreemiumRate(@PathVariable(name = "ratePlanFreemiumRateId") final Long ratePlanFreemiumRateId) {
        final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO = ratePlanFreemiumRateService.get(ratePlanFreemiumRateId);
        return ResponseEntity.ok(ratePlanFreemiumRateAssembler.toModel(ratePlanFreemiumRateDTO));
    }

    @PostMapping("/{ratePlanId}")
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createRatePlanFreemiumRate(@PathVariable("ratePlanId") Long ratePlanId, @RequestBody @Valid final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {

        final Long createdRatePlanFreemiumRateId = ratePlanFreemiumRateService.create(ratePlanId, ratePlanFreemiumRateDTO);
        return new ResponseEntity<>(ratePlanFreemiumRateAssembler.toSimpleModel(createdRatePlanFreemiumRateId), HttpStatus.CREATED);
    }


    @PutMapping("/{ratePlanFreemiumRateId}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateRatePlanFreemiumRate(@PathVariable(name = "ratePlanFreemiumRateId") final Long ratePlanFreemiumRateId, @RequestBody @Valid final RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        ratePlanFreemiumRateService.update(ratePlanFreemiumRateId, ratePlanFreemiumRateDTO);
        return ResponseEntity.ok(ratePlanFreemiumRateAssembler.toSimpleModel(ratePlanFreemiumRateId));
    }

    @DeleteMapping("/{ratePlanFreemiumRateId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteRatePlanFreemiumRate(@PathVariable(name = "ratePlanFreemiumRateId") final Long ratePlanFreemiumRateId) {
//        final ReferencedWarning referencedWarning = ratePlanFreemiumRateService.getReferencedWarning(ratePlanFreemiumRateId);
//        if (referencedWarning != null) {
//            throw new ReferencedException(referencedWarning);
//        }
        ratePlanFreemiumRateService.delete(ratePlanFreemiumRateId);
        return ResponseEntity.noContent().build();
    }

}
