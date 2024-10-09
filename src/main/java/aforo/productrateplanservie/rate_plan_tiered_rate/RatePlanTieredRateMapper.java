package aforo.productrateplanservie.rate_plan_tiered_rate;

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
public interface RatePlanTieredRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanTieredRateDTO updateRatePlanTieredRateDTO(RatePlanTieredRate ratePlanTieredRate,
            @MappingTarget RatePlanTieredRateDTO ratePlanTieredRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanTieredRateDTO(RatePlanTieredRate ratePlanTieredRate,
            @MappingTarget RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        ratePlanTieredRateDTO.setRatePlanId(ratePlanTieredRate.getRatePlanId() == null ? null : (Long) ratePlanTieredRate.getRatePlanId());
    }

    @Mapping(target = "ratePlanTieredRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanTieredRate updateRatePlanTieredRate(RatePlanTieredRateDTO ratePlanTieredRateDTO,
            @MappingTarget RatePlanTieredRate ratePlanTieredRate,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanTieredRate(RatePlanTieredRateDTO ratePlanTieredRateDTO,
            @MappingTarget RatePlanTieredRate ratePlanTieredRate,
            @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanTieredRateDTO.getRatePlanId() == null ? null : ratePlanRepository.findById(ratePlanTieredRateDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanTieredRate.setRatePlanId(ratePlan.getRatePlanId());
    }

}
