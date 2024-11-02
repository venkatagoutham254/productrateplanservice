package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRatesDTO;
import aforo.productrateplanservie.exception.NotFoundException;
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

    @Mapping(target = "ratePlanId", ignore = true)
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

    @Mapping(target = "ratePlanUsageRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    void updateRatePlanUsageBased(RatePlanUsageBasedDTO ratePlanUsageBasedDTO,
                                  @MappingTarget RatePlanUsageBased ratePlanUsageBased,
                                  @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanUsageBased(RatePlanUsageBasedDTO ratePlanUsageBasedDTO,
                                               @MappingTarget RatePlanUsageBased ratePlanUsageBased,
                                               @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanUsageBasedDTO.getRatePlanId() == null ? null : ratePlanRepository.findById(ratePlanUsageBasedDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("RatePlan not found"));
        ratePlanUsageBased.setRatePlan(ratePlan);
    }

    RatePlanUsageBasedRatesDTO mapToRatePlanUsageBasedRatesDTO(RatePlanUsageBasedRates details);

    RatePlanUsageBasedRates mapToRatePlanUsageBasedRates(RatePlanUsageBasedRatesDTO detailsDTO);

    // Ensure this method is void and explicitly map the fields to avoid ambiguity
    @Mapping(target = "unitRate", source = "detailsDTO.unitRate")
    @Mapping(target = "id", source = "detailsDTO.id")
    void updateRatePlanUsageBasedRatesFromDTO(RatePlanUsageBasedRatesDTO detailsDTO, @MappingTarget RatePlanUsageBasedRates details);
}
