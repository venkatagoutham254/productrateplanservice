package aforo.productrateplanservie.rate_plan_subscription_rate.service;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan.repos.RatePlanRepository;
import aforo.productrateplanservie.rate_plan_subscription_rate.domain.RatePlanSubscriptionRate;
import aforo.productrateplanservie.rate_plan_subscription_rate.model.RatePlanSubscriptionRateDTO;
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
public interface RatePlanSubscriptionRateMapper {

    @Mapping(target = "ratePlan", ignore = true)
    RatePlanSubscriptionRateDTO updateRatePlanSubscriptionRateDTO(
            RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @MappingTarget RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO);

    @AfterMapping
    default void afterUpdateRatePlanSubscriptionRateDTO(
            RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @MappingTarget RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
        ratePlanSubscriptionRateDTO.setRatePlan(ratePlanSubscriptionRate.getRatePlan() == null ? null : ratePlanSubscriptionRate.getRatePlan().getRatePlanId());
    }

    @Mapping(target = "ratePlanSubscriptionRateId", ignore = true)
    @Mapping(target = "ratePlan", ignore = true)
    RatePlanSubscriptionRate updateRatePlanSubscriptionRate(
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO,
            @MappingTarget RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @Context RatePlanRepository ratePlanRepository);

    @AfterMapping
    default void afterUpdateRatePlanSubscriptionRate(
            RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO,
            @MappingTarget RatePlanSubscriptionRate ratePlanSubscriptionRate,
            @Context RatePlanRepository ratePlanRepository) {
        final RatePlan ratePlan = ratePlanSubscriptionRateDTO.getRatePlan() == null ? null : ratePlanRepository.findById(ratePlanSubscriptionRateDTO.getRatePlan())
                .orElseThrow(() -> new NotFoundException("ratePlan not found"));
        ratePlanSubscriptionRate.setRatePlan(ratePlan);
    }

}
