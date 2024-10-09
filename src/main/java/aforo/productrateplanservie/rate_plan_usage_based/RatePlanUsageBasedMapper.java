package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
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
public interface RatePlanUsageBasedMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanUsageBasedDTO updateRatePlanUsageBasedDTO(RatePlanUsageBased ratePlanUsageBased,
            @MappingTarget RatePlanUsageBasedDTO ratePlanUsageBasedDTO);

    @AfterMapping
    default void afterUpdateRatePlanUsageBasedDTO(RatePlanUsageBased ratePlanUsageBased,
            @MappingTarget RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
        ratePlanUsageBasedDTO.setRatePlanId(ratePlanUsageBased.getRatePlanId() == null ? null : ratePlanUsageBased.getRatePlanId());
    }

    @Mapping(target = "ratePlanUsageRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanUsageBased updateRatePlanUsageBased(RatePlanUsageBasedDTO ratePlanUsageBasedDTO,
            @MappingTarget RatePlanUsageBased ratePlanUsageBased,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanUsageBased(RatePlanUsageBasedDTO ratePlanUsageBasedDTO,
            @MappingTarget RatePlanUsageBased ratePlanUsageBased,
            @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanUsageBasedDTO.getRatePlanId() == null ? null : ratePlanRepository.findById(ratePlanUsageBasedDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanUsageBased.setRatePlanId(ratePlan.getRatePlanId());
    }

}
