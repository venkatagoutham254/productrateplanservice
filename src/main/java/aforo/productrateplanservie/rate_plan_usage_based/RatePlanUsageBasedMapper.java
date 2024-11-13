package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_usage_based_rates.CreateRatePlanUsageBasedRatesRequest;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesDTO;
import aforo.productrateplanservie.exception.NotFoundException;
import aforo.productrateplanservie.rate_plan_usage_based_rates.UpdateRatePlanUsageBasedRatesRequest;
import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatePlanUsageBasedMapper {

    // Map create request DTO to entity for RatePlanUsageBased
    @Mapping(target = "ratePlanUsageRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true) // Set manually in the service
    @Mapping(target = "ratePlanUsageBasedRates", ignore = true) // Managed in the service
    RatePlanUsageBased toEntity(CreateRatePlanUsageBasedRequest dto);

    // Map create request DTO to entity for RatePlanUsageBasedRates
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ratePlanUsageBased", ignore = true) // Set manually in the service
    RatePlanUsageBasedRates toEntity(CreateRatePlanUsageBasedRatesRequest dto);

    // Map RatePlanUsageBased entity to DTO
    @Mapping(target = "ratePlanId", source = "ratePlan.ratePlanId")
    @Mapping(target = "ratePlanUsageBasedRatesDTO", source = "ratePlanUsageBasedRates")
    RatePlanUsageBasedDTO toDto(RatePlanUsageBased ratePlanUsageBased);

    // Additional mapping for nested RatePlanUsageBasedRates entity to DTO
    RatePlanUsageBasedRatesDTO mapToRatePlanUsageBasedRatesDTO(RatePlanUsageBasedRates details);

    // Update RatePlanUsageBasedDTO with data from RatePlanUsageBased entity
    @Mapping(target = "ratePlanId", source = "ratePlan.ratePlanId")
    @Mapping(target = "ratePlanUsageBasedRatesDTO", source = "ratePlanUsageBasedRates")
    RatePlanUsageBasedDTO updateRatePlanUsageBasedDTO(RatePlanUsageBased ratePlanUsageBased,
                                                      @MappingTarget RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    @AfterMapping
    default void afterUpdateRatePlanUsageBasedDTO(RatePlanUsageBased ratePlanUsageBased,
                                                  @MappingTarget RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        ratePlanUsageBasedDTO.setRatePlanId(ratePlanUsageBased.getRatePlan().getRatePlanId());
        ratePlanUsageBasedDTO.setRatePlanUsageBasedRatesDTO(
                ratePlanUsageBased.getRatePlanUsageBasedRates().stream()
                        .map(this::mapToRatePlanUsageBasedRatesDTO)
                        .collect(Collectors.toSet())
        );
    }

    // Method for partial update of RatePlanUsageBased entity
    void updateRatePlanUsageBased(UpdateRatePlanUsageBasedRequest dto, @MappingTarget RatePlanUsageBased entity);

    // Method for partial update of nested RatePlanUsageBasedRates entity
    void updateRatePlanUsageBasedRates(UpdateRatePlanUsageBasedRatesRequest dto, @MappingTarget RatePlanUsageBasedRates entity);
}
