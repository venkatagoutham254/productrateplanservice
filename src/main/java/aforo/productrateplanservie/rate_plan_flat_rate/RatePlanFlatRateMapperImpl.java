package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;

public class RatePlanFlatRateMapperImpl implements RatePlanFlatRateMapper  {

	@Override
	public RatePlanFlatRateDTO updateRatePlanFlatRateDTO(RatePlanFlatRate ratePlanFlatRate,
			RatePlanFlatRateDTO ratePlanFlatRateDTO) {
		
		RatePlanFlatRateDTO dto = ratePlanFlatRateDTO;
		dto.setRatePlanFlatRateId(ratePlanFlatRate.getRatePlanFlatRateId());
        dto.setRatePlanFlatDescription(ratePlanFlatRate.getRatePlanFlatDescription());
        dto.setDescription(ratePlanFlatRate.getDescription());
        dto.setUnitType(ratePlanFlatRate.getUnitType());
        dto.setUnitMeasurement(ratePlanFlatRate.getUnitMeasurement());
        dto.setFlatRateUnitCalculation(ratePlanFlatRate.getFlatRateUnitCalculation());
        dto.setMaxLimitFrequency(ratePlanFlatRate.getMaxLimitFrequency());
        dto.setRatePlanId(ratePlanFlatRate.getRatePlanId());
        
		return dto;
	}

	@Override
	public RatePlanFlatRate updateRatePlanFlatRate(RatePlanFlatRateDTO ratePlanFlatRateDTO,
			RatePlanFlatRate ratePlanFlatRate, RatePlanRepository ratePlanRepository) {
		RatePlanFlatRate localRatePlanFlatRate = ratePlanFlatRate;
		localRatePlanFlatRate.setRatePlanFlatRateId(ratePlanFlatRateDTO.getRatePlanFlatRateId());
		localRatePlanFlatRate.setRatePlanFlatDescription(ratePlanFlatRateDTO.getRatePlanFlatDescription());
		localRatePlanFlatRate.setDescription(ratePlanFlatRateDTO.getDescription());
		localRatePlanFlatRate.setUnitType(ratePlanFlatRateDTO.getUnitType());
		localRatePlanFlatRate.setUnitMeasurement(ratePlanFlatRateDTO.getUnitMeasurement());
		localRatePlanFlatRate.setFlatRateUnitCalculation(ratePlanFlatRateDTO.getFlatRateUnitCalculation());
		localRatePlanFlatRate.setMaxLimitFrequency(ratePlanFlatRateDTO.getMaxLimitFrequency());
		localRatePlanFlatRate.setRatePlanId(ratePlanFlatRateDTO.getRatePlanId());
		
		return localRatePlanFlatRate;
	}
	

}
