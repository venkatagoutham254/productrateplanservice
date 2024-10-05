package aforo.rateplanservie.service;

import aforo.rateplanservie.domain.RatePlan;
import aforo.rateplanservie.domain.RatePlanFreemiumRate;
import aforo.rateplanservie.model.RatePlanFreemiumRateDTO;
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
public interface RatePlanFreemiumRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanFreemiumRateDTO updateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
            @MappingTarget RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
        ratePlanFreemiumRateDTO.setRatePlan(ratePlanFreemiumRate.getRatePlan() == null ? null : ratePlanFreemiumRate.getRatePlan().getRatePlanId());
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
        final RatePlan ratePlan = ratePlanFreemiumRateDTO.getRatePlan() == null ? null : ratePlanRepository.findById(ratePlanFreemiumRateDTO.getRatePlan())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanFreemiumRate.setRatePlan(ratePlan);
    }

}
