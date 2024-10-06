package aforo.productrateplanservie.currencies;

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
@RequestMapping(value = "/api/currenciess", produces = MediaType.APPLICATION_JSON_VALUE)
public class CurrenciesResource {

    private final CurrenciesService currenciesService;
    private final CurrenciesAssembler currenciesAssembler;
    private final PagedResourcesAssembler<CurrenciesDTO> pagedResourcesAssembler;

    public CurrenciesResource(final CurrenciesService currenciesService,
            final CurrenciesAssembler currenciesAssembler,
            final PagedResourcesAssembler<CurrenciesDTO> pagedResourcesAssembler) {
        this.currenciesService = currenciesService;
        this.currenciesAssembler = currenciesAssembler;
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
    public ResponseEntity<PagedModel<EntityModel<CurrenciesDTO>>> getAllCurrenciess(
            @RequestParam(name = "filter", required = false) final String filter,
            @Parameter(hidden = true) @SortDefault(sort = "currencyId") @PageableDefault(size = 20) final Pageable pageable) {
        final Page<CurrenciesDTO> currenciesDTOs = currenciesService.findAll(filter, pageable);
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(currenciesDTOs, currenciesAssembler));
    }

    @GetMapping("/{currencyId}")
    public ResponseEntity<EntityModel<CurrenciesDTO>> getCurrencies(
            @PathVariable(name = "currencyId") final Long currencyId) {
        final CurrenciesDTO currenciesDTO = currenciesService.get(currencyId);
        return ResponseEntity.ok(currenciesAssembler.toModel(currenciesDTO));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> createCurrencies(
            @RequestBody @Valid final CurrenciesDTO currenciesDTO) {
        final Long createdCurrencyId = currenciesService.create(currenciesDTO);
        return new ResponseEntity<>(currenciesAssembler.toSimpleModel(createdCurrencyId), HttpStatus.CREATED);
    }

    @PutMapping("/{currencyId}")
    public ResponseEntity<EntityModel<SimpleValue<Long>>> updateCurrencies(
            @PathVariable(name = "currencyId") final Long currencyId,
            @RequestBody @Valid final CurrenciesDTO currenciesDTO) {
        currenciesService.update(currencyId, currenciesDTO);
        return ResponseEntity.ok(currenciesAssembler.toSimpleModel(currencyId));
    }

    @DeleteMapping("/{currencyId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteCurrencies(
            @PathVariable(name = "currencyId") final Long currencyId) {
        final ReferencedWarning referencedWarning = currenciesService.getReferencedWarning(currencyId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        currenciesService.delete(currencyId);
        return ResponseEntity.noContent().build();
    }

}
