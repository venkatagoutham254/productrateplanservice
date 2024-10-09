package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;

public class RatePlanSubscriptionRateMapperImpl implements RatePlanSubscriptionRateMapper {

	@Override
	public RatePlanSubscriptionRateDTO updateRatePlanSubscriptionRateDTO(
			RatePlanSubscriptionRate ratePlanSubscriptionRate,
			RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO) {
		RatePlanSubscriptionRateDTO dto = ratePlanSubscriptionRateDTO;
		
		dto.setRatePlanSubscriptionRateId(ratePlanSubscriptionRate.getRatePlanSubscriptionRateId());
        dto.setRatePlanSubscriptionDescription(ratePlanSubscriptionRate.getRatePlanSubscriptionDescription());
        dto.setDescription(ratePlanSubscriptionRate.getDescription());
        dto.setUnitType(ratePlanSubscriptionRate.getUnitType());
        dto.setUnitMeasurement(ratePlanSubscriptionRate.getUnitMeasurement());
        dto.setUnitBillingFrequency(ratePlanSubscriptionRate.getUnitBillingFrequency());
        dto.setUnitPriceFixedFrequency(ratePlanSubscriptionRate.getUnitPriceFixedFrequency());
        dto.setRatePlanId(ratePlanSubscriptionRate.getRatePlan());
		return dto;
	}

	@Override
	public RatePlanSubscriptionRate updateRatePlanSubscriptionRate(
			RatePlanSubscriptionRateDTO ratePlanSubscriptionRateDTO, RatePlanSubscriptionRate ratePlanSubscriptionRate,
			RatePlanRepository ratePlanRepository) {
		RatePlanSubscriptionRate localRatePlanSubscriptionRate = ratePlanSubscriptionRate;
		 localRatePlanSubscriptionRate.setRatePlanSubscriptionRateId(ratePlanSubscriptionRateDTO.getRatePlanSubscriptionRateId());
         localRatePlanSubscriptionRate.setRatePlanSubscriptionDescription(ratePlanSubscriptionRateDTO.getRatePlanSubscriptionDescription());
         localRatePlanSubscriptionRate.setDescription(ratePlanSubscriptionRateDTO.getDescription());
         localRatePlanSubscriptionRate.setUnitType(ratePlanSubscriptionRateDTO.getUnitType());
         localRatePlanSubscriptionRate.setUnitMeasurement(ratePlanSubscriptionRateDTO.getUnitMeasurement());
         localRatePlanSubscriptionRate.setUnitBillingFrequency(ratePlanSubscriptionRateDTO.getUnitBillingFrequency());
         localRatePlanSubscriptionRate.setUnitPriceFixedFrequency(ratePlanSubscriptionRateDTO.getUnitPriceFixedFrequency());
		return localRatePlanSubscriptionRate;
	}

}
