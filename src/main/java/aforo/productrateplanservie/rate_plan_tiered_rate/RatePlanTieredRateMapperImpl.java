package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;

public class RatePlanTieredRateMapperImpl implements RatePlanTieredRateMapper {

	@Override
	public RatePlanTieredRateDTO updateRatePlanTieredRateDTO(RatePlanTieredRate ratePlanTieredRate,
			RatePlanTieredRateDTO ratePlanTieredRateDTO) {
		RatePlanTieredRateDTO dto = ratePlanTieredRateDTO;
		dto.setRatePlanTieredRateId(ratePlanTieredRate.getRatePlanTieredRateId());
        dto.setRatePlanTieredDescription(ratePlanTieredRate.getRatePlanTieredDescription());
        dto.setDescription(ratePlanTieredRate.getDescription());
        dto.setUnitType(ratePlanTieredRate.getUnitType());
        dto.setUnitMeasurement(ratePlanTieredRate.getUnitMeasurement());
        dto.setUnitCalculation(ratePlanTieredRate.getUnitCalculation());
        dto.setRatePlanId(ratePlanTieredRate.getRatePlanId());
		return null;
	}

	@Override
	public RatePlanTieredRate updateRatePlanTieredRate(RatePlanTieredRateDTO ratePlanTieredRateDTO,
			RatePlanTieredRate ratePlanTieredRate, RatePlanRepository ratePlanRepository) {
	    RatePlanTieredRate localRatePlanTieredRate = ratePlanTieredRate;
		localRatePlanTieredRate.setRatePlanTieredRateId(ratePlanTieredRateDTO.getRatePlanTieredRateId());
		localRatePlanTieredRate.setRatePlanTieredDescription(ratePlanTieredRateDTO.getRatePlanTieredDescription());
		localRatePlanTieredRate.setDescription(ratePlanTieredRateDTO.getDescription());
		localRatePlanTieredRate.setUnitType(ratePlanTieredRateDTO.getUnitType());
		localRatePlanTieredRate.setUnitMeasurement(ratePlanTieredRateDTO.getUnitMeasurement());
		localRatePlanTieredRate.setUnitCalculation(ratePlanTieredRateDTO.getUnitCalculation());
		
		return null;
	}
	
	
}
