package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.Currencies;
import aforo.productrateplanservie.currencies.CurrenciesRepository;
import aforo.productrateplanservie.product.Product;
import aforo.productrateplanservie.product.ProductRepository;
import aforo.productrateplanservie.util.NotFoundException;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;


@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatePlanMapper {

    @Mapping(target = "product", ignore = true)
    @Mapping(target = "currency", ignore = true)
    RatePlanDTO updateRatePlanDTO(RatePlan ratePlan, @MappingTarget RatePlanDTO ratePlanDTO);

    @AfterMapping
    default void afterUpdateRatePlanDTO(RatePlan ratePlan, @MappingTarget RatePlanDTO ratePlanDTO) {
        ratePlanDTO.setProductId(ratePlan.getProductId() == null ? null : ratePlan.getProductId());
        ratePlanDTO.setCurrencyId(ratePlan.getCurrencyId() == null ? null : ratePlan.getCurrencyId());
    }

    @Mapping(target = "ratePlanId", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "currency", ignore = true)
    RatePlan updateRatePlan(RatePlanDTO ratePlanDTO, @MappingTarget RatePlan ratePlan,
            @Context ProductRepository productRepository,
            @Context CurrenciesRepository currenciesRepository);

    @AfterMapping
    default void afterUpdateRatePlan(RatePlanDTO ratePlanDTO, @MappingTarget RatePlan ratePlan,
            @Context ProductRepository productRepository,
            @Context CurrenciesRepository currenciesRepository) {
        final Product product = ratePlanDTO.getProductId() == null ? null : productRepository.findById(ratePlanDTO.getProductId())
                .orElseThrow(() -> new NotFoundException("product not found"));
        ratePlan.setProductId(product.getProductId());
        final Currencies currency = ratePlanDTO.getCurrencyId() == null ? null : currenciesRepository.findById(ratePlanDTO.getCurrencyId())
                .orElseThrow(() -> new NotFoundException("currency not found"));
        ratePlan.setCurrencyId(currency.getCurrencyId());
    }

}
