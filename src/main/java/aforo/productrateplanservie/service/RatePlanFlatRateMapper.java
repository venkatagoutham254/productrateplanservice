package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanFlatRate;
import aforo.productrateplanservie.model.RatePlanFlatRateDTO;
import aforo.productrateplanservie.repos.RatePlanRepository;
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
        ratePlanFlatRateDTO.setRatePlan(ratePlanFlatRate.getRatePlan() == null ? null : ratePlanFlatRate.getRatePlan().getRatePlanId());
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
        final RatePlan ratePlan = ratePlanFlatRateDTO.getRatePlan() == null ? null : ratePlanRepository.findById(ratePlanFlatRateDTO.getRatePlan())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanFlatRate.setRatePlan(ratePlan);
    }

}
