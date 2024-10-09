package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;

public class RatePlanFreemiumRateMapperImpl implements RatePlanFreemiumRateMapper{

	@Override
	public RatePlanFreemiumRateDTO updateRatePlanFreemiumRateDTO(RatePlanFreemiumRate ratePlanFreemiumRate,
			RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO) {
		RatePlanFreemiumRateDTO dto = ratePlanFreemiumRateDTO;
		dto.setRatePlanFreemiumRateId(ratePlanFreemiumRate.getRatePlanFreemiumRateId());
        dto.setRatePlanFreemiumDescription(ratePlanFreemiumRate.getRatePlanFreemiumDescription());
        dto.setDescription(ratePlanFreemiumRate.getDescription());
        dto.setUnitType(ratePlanFreemiumRate.getUnitType());
        dto.setUnitMeasurement(ratePlanFreemiumRate.getUnitMeasurement());
        dto.setUnitBillingFrequency(ratePlanFreemiumRate.getUnitBillingFrequency());
        dto.setUnitFreePriceFixedFrequency(ratePlanFreemiumRate.getUnitFreePriceFixedFrequency());
        dto.setRatePlanId(ratePlanFreemiumRate.getRatePlanId());
		
		return dto;
	}

	@Override
	public RatePlanFreemiumRate updateRatePlanFreemiumRate(RatePlanFreemiumRateDTO ratePlanFreemiumRateDTO,
			RatePlanFreemiumRate ratePlanFreemiumRate, RatePlanRepository ratePlanRepository) {
		RatePlanFreemiumRate localRatePlanFreemiumRate = ratePlanFreemiumRate;
		localRatePlanFreemiumRate.setRatePlanFreemiumRateId(ratePlanFreemiumRateDTO.getRatePlanFreemiumRateId());
		localRatePlanFreemiumRate.setRatePlanFreemiumDescription(ratePlanFreemiumRateDTO.getRatePlanFreemiumDescription());
		localRatePlanFreemiumRate.setDescription(ratePlanFreemiumRateDTO.getDescription());
		localRatePlanFreemiumRate.setUnitType(ratePlanFreemiumRateDTO.getUnitType());
		localRatePlanFreemiumRate.setUnitMeasurement(ratePlanFreemiumRateDTO.getUnitMeasurement());
		localRatePlanFreemiumRate.setUnitBillingFrequency(ratePlanFreemiumRateDTO.getUnitBillingFrequency());
		localRatePlanFreemiumRate.setUnitFreePriceFixedFrequency(ratePlanFreemiumRateDTO.getUnitFreePriceFixedFrequency());
	
		return localRatePlanFreemiumRate;
	}

}
