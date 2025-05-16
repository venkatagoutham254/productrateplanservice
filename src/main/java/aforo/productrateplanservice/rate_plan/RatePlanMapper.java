package aforo.productrateplanservice.rate_plan;

import org.mapstruct.*;

import aforo.productrateplanservice.currencies.Currencies;
import aforo.productrateplanservice.currencies.CurrenciesRepository;
import aforo.productrateplanservice.exception.NotFoundException;
import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.repository.ProductRepository;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RatePlanMapper {

	RatePlanDTO createRatePlanRequestToRatePlanDTO(CreateRatePlanRequest createRatePlanRequest);

	@Mapping(target = "productId", source = "product.productId")
	@Mapping(target = "currencyId", source = "currency.currencyId")
	RatePlanDTO updateRatePlanDTO(RatePlan ratePlan, @MappingTarget RatePlanDTO ratePlanDTO);

	@AfterMapping
	default void afterUpdateRatePlanDTO(RatePlan ratePlan, @MappingTarget RatePlanDTO ratePlanDTO) {
		ratePlanDTO.setProductId(ratePlan.getProduct() == null ? null : ratePlan.getProduct().getProductId());
		ratePlanDTO.setCurrencyId(ratePlan.getCurrency() == null ? null : ratePlan.getCurrency().getCurrencyId());
	}

	@Mapping(target = "ratePlanId", ignore = true)
	@Mapping(target = "product", ignore = true)
	@Mapping(target = "currency", ignore = true)
	RatePlan updateRatePlan(RatePlanDTO ratePlanDTO, @MappingTarget RatePlan ratePlan,
			@Context ProductRepository productRepository, @Context CurrenciesRepository currenciesRepository);

	@AfterMapping
	default void afterUpdateRatePlan(RatePlanDTO ratePlanDTO, @MappingTarget RatePlan ratePlan,
			@Context ProductRepository productRepository, @Context CurrenciesRepository currenciesRepository) {
		final Product product = ratePlanDTO.getProductId() == null ? null
				: productRepository.findById(ratePlanDTO.getProductId())
						.orElseThrow(() -> new NotFoundException("Product not found"));
		ratePlan.setProduct(product); // Set the entire Product object

		final Currencies currency = ratePlanDTO.getCurrencyId() == null ? null
				: currenciesRepository.findById(ratePlanDTO.getCurrencyId())
						.orElseThrow(() -> new NotFoundException("Currency not found"));
		ratePlan.setCurrency(currency); // Set the entire Currencies object
	}
}
