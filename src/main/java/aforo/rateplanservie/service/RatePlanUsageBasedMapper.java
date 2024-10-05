package aforo.rateplanservie.service;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanUsageBased;
import aforo.rateplanservie.model.RatePlanUsageBasedDTO;
import aforo.rateplanservie.repos.RatePlanRepository;
import aforo.rateplanservie.util.NotFoundException;
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
        ratePlanUsageBasedDTO.setRatePlan(ratePlanUsageBased.getRatePlan() == null ? null : ratePlanUsageBased.getRatePlan().getRatePlanId());
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
        final RatePlan ratePlan = ratePlanUsageBasedDTO.getRatePlan() == null ? null : ratePlanRepository.findById(ratePlanUsageBasedDTO.getRatePlan())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanUsageBased.setRatePlan(ratePlan);
    }

}
