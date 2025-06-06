package aforo.productrateplanservice.rate_plan;

import org.mapstruct.*;

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.repository.ProductRepository;
import aforo.productrateplanservice.rate_plan.RatePlanDTO;
import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.CreateRatePlanRequest;
import aforo.productrateplanservice.exception.NotFoundException;

@Mapper(
    componentModel = MappingConstants.ComponentModel.SPRING,
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatePlanMapper {

    RatePlanDTO createRatePlanRequestToRatePlanDTO(CreateRatePlanRequest request);

    @Mapping(target = "productId", source = "product.productId")
    @Mapping(target = "productName", source = "product.productName")
    RatePlanDTO toRatePlanDTO(RatePlan ratePlan);

    @Mapping(target = "ratePlanId", ignore = true)
    @Mapping(target = "product", ignore = true) // we will set this manually
    RatePlan updateRatePlanFromDTO(
        RatePlanDTO dto,
        @MappingTarget RatePlan ratePlan,
        @Context ProductRepository productRepository
    );

    @AfterMapping
    default void afterUpdateRatePlanFromDTO(
        RatePlanDTO dto,
        @MappingTarget RatePlan ratePlan,
        @Context ProductRepository productRepository
    ) {
        if (dto.getProductId() != null) {
            Product product = productRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new NotFoundException("Product not found with ID: " + dto.getProductId()));
            ratePlan.setProduct(product);
        }
    }
}
