package aforo.productrateplanservie.service;

import aforo.productrateplanservie.domain.RatePlan;
import aforo.productrateplanservie.domain.RatePlanTieredRate;
import aforo.productrateplanservie.model.RatePlanTieredRateDTO;
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
public interface RatePlanTieredRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanTieredRateDTO updateRatePlanTieredRateDTO(RatePlanTieredRate ratePlanTieredRate,
            @MappingTarget RatePlanTieredRateDTO ratePlanTieredRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanTieredRateDTO(RatePlanTieredRate ratePlanTieredRate,
            @MappingTarget RatePlanTieredRateDTO ratePlanTieredRateDTO) {
        ratePlanTieredRateDTO.setRatePlan(ratePlanTieredRate.getRatePlan() == null ? null : ratePlanTieredRate.getRatePlan().getRatePlanId());
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
        final RatePlan ratePlan = ratePlanTieredRateDTO.getRatePlan() == null ? null : ratePlanRepository.findById(ratePlanTieredRateDTO.getRatePlan())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanTieredRate.setRatePlan(ratePlan);
    }

}
