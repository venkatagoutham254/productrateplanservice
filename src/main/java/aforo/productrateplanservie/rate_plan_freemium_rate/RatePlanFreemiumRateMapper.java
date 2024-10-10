package aforo.productrateplanservie.rate_plan_freemium_rate;


import org.mapstruct.AfterMapping;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan.RatePlanRepository;
import aforo.productrateplanservie.util.NotFoundException;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RatePlanFreemiumRateMapper {

    @Mapping(target = "ratePlanId", ignore = true)
    RatePlanFreemiumRateDTO updateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        ratePlanFreemiumRateDTO.setRatePlanId(
                ratePlanFreemiumRate.getRatePlan() == null ? null : ratePlanFreemiumRate.getRatePlan().getRatePlanId()
        );
    }

    @Mapping(target = "ratePlanFreemiumRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true) // Ignore to handle it manually
    RatePlanFreemiumRate updateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO,
            @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO,
            @MappingTarget RatePlanFreemiumRate ratePlanFreemiumRate,
            @Context RatePlanRepository ratePlanRepository) {
        // Retrieve RatePlan using the provided ratePlanId from DTO
        final RatePlan ratePlan = ratePlanFreemiumRateDTO.getRatePlanId() == null ? null :
                ratePlanRepository.findById(ratePlanFreemiumRateDTO.getRatePlanId())
                .orElseThrow(() -> new NotFoundException("RatePlan not found"));

        // Set the associated RatePlan to the RatePlanFreemiumRate entity
        ratePlanFreemiumRate.setRatePlan(ratePlan);
    }
}
