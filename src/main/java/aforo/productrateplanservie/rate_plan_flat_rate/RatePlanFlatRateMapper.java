package aforo.productrateplanservie.rate_plan_flat_rate;

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
public interface RatePlanFlatRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFlatRateDTO updateRatePlanFlatRateDTO(RatePlanFlatRate ratePlanFlatRate,
            @MappingTarget RatePlanFlatRateDTO ratePlanFlatRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanFlatRateDTO(RatePlanFlatRate ratePlanFlatRate,
            @MappingTarget RatePlanFlatRateDTO ratePlanFlatRateDTO) {
        ratePlanFlatRateDTO.setRatePlanId(ratePlanFlatRate.getRatePlanId() == null ? null : ratePlanFlatRate.getRatePlanId());
    }

    @Mapping(target = "ratePlanFlatRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFlatRate updateRatePlanFlatRate(RatePlanFlatRateDTO ratePlanFlatRateDTO,
            @MappingTarget RatePlanFlatRate ratePlanFlatRate,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanFlatRate(RatePlanFlatRateDTO ratePlanFlatRateDTO,
            @MappingTarget RatePlanFlatRate ratePlanFlatRate,
            @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanFlatRateDTO.getRatePlanId() == null ? null : ratePlanRepository.findById(ratePlanFlatRateDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanFlatRate.setRatePlanId(ratePlan.getRatePlanId());
    }

}
