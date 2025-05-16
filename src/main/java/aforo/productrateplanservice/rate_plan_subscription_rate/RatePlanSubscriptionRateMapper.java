package aforo.productrateplanservice.rate_plan_subscription_rate;

import org.mapstruct.*;

import aforo.productrateplanservice.rate_plan.RatePlan;
import aforo.productrateplanservice.rate_plan.RatePlanRepository;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.CreateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatePlanSubscriptionRateMapper {

    // Map from entity to DTO, including rate plan subscription details
    @Mapping(target = "ratePlanSubscriptionRateDetailsDTO", source = "ratePlanSubscriptionRateDetails")
    RatePlanSubscriptionRateDTO updateRatePlanSubscriptionRateDTO(
            RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @MappingTarget RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanSubscriptionRateDTO(
            RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @MappingTarget RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        ratePlanSubscriptionRateDTO.setRatePlanId(
                ratePlanSubscriptionRate.getRatePlan() == null ? null : ratePlanSubscriptionRate.getRatePlan().getRatePlanId()
        );
    }

    @Mapping(target = "ratePlanSubscriptionRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    void updateRatePlanSubscriptionRate(
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO,
            @MappingTarget RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @Context RatePlanRepository ratePlanRepository);

    // Map nested RatePlanSubscriptionRateDetails from DTO
    @Mapping(target = "id", ignore = true)
    RatePlanSubscriptionRateDetails mapToRatePlanSubscriptionRateDetails(RatePlanSubscriptionRateDetailsDTO detailsDTO);

    // Map nested RatePlanSubscriptionRateDetails from entity
    RatePlanSubscriptionRateDetailsDTO mapToRatePlanSubscriptionRateDetailsDTO(RatePlanSubscriptionRateDetails details);

    // Create mapping
    @Mapping(target = "ratePlanSubscriptionRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true) // Manually set in service
    @Mapping(target = "ratePlanSubscriptionRateDetails", ignore = true) // Manually handle nested details
    RatePlanSubscriptionRate toEntity(CreateRatePlanSubscriptionRateRequest dto);

    // Helper to map nested details request
    default RatePlanSubscriptionRate mapToEntity(CreateRatePlanSubscriptionRateRequest dto, RatePlan ratePlan) {
        RatePlanSubscriptionRate ratePlanSubscriptionRate = toEntity(dto);
        ratePlanSubscriptionRate.setRatePlan(ratePlan);

        Set<RatePlanSubscriptionRateDetails> details = dto.getRatePlanSubscriptionRateDetails().stream()
                .map(this::mapDetailsRequestToEntity)
                .peek(detail -> detail.setRatePlanSubscriptionRate(ratePlanSubscriptionRate))
                .collect(Collectors.toSet());

        ratePlanSubscriptionRate.setRatePlanSubscriptionRateDetails(details);
        return ratePlanSubscriptionRate;
    }

    // Helper for mapping nested details
    RatePlanSubscriptionRateDetails mapDetailsRequestToEntity(CreateRatePlanSubscriptionRateDetailsRequest detailsRequest);
}
