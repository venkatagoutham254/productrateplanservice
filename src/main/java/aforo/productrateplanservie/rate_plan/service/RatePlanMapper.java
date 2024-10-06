package aforo.productrateplanservie.rate_plan.service;

import aforo.productrateplanservie.currencies.domain.Currencies;
import aforo.productrateplanservie.currencies.repos.CurrenciesRepository;
import aforo.productrateplanservie.product.domain.Product;
import aforo.productrateplanservie.product.repos.ProductRepository;
import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan.model.RatePlanDTO;
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
        ratePlanDTO.setProduct(ratePlan.getProduct() == null ? null : ratePlan.getProduct().getProductId());
        ratePlanDTO.setCurrency(ratePlan.getCurrency() == null ? null : ratePlan.getCurrency().getCurrencyId());
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
        final Product product = ratePlanDTO.getProduct() == null ? null : productRepository.findById(ratePlanDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        ratePlan.setProduct(product);
        final Currencies currency = ratePlanDTO.getCurrency() == null ? null : currenciesRepository.findById(ratePlanDTO.getCurrency())
                .orElseThrow(() -> new NotFoundException("currency not found"));
        ratePlan.setCurrency(currency);
    }

}
