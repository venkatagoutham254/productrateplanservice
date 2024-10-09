package aforo.productrateplanservie.rate_plan_freemium_rate;

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
public interface RatePlanFreemiumRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFreemiumRateDTO updateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        ratePlanFreemiumRateDTO.setRatePlanId(ratePlanFreemiumRate.getRatePlanId() == null ? null : (Long) ratePlanFreemiumRate.getRatePlanId());
    }

    @Mapping(target = "ratePlanFreemiumRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFreemiumRate updateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO,
            @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO,
            @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate,
            @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanFreemiumRateDTO.getRatePlanId() == null ? null : ratePlanRepository.findById(ratePlanFreemiumRateDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanFreemiumRate.setRatePlanId(ratePlan.getRatePlanId());
    }

}
