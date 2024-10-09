package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlanRepository;

public class RatePlanUsageBasedMapperImpl implements RatePlanUsageBasedMapper {

	@Override
	public RatePlanUsageBasedDTO updateRatePlanUsageBasedDTO(RatePlanUsageBased ratePlanUsageBased,
			RatePlanUsageBasedDTO ratePlanUsageBasedDTO) {
		RatePlanUsageBasedDTO dto = ratePlanUsageBasedDTO;
		 dto.setRatePlanUsageRateId(ratePlanUsageBased.getRatePlanUsageRateId());
         dto.setRatePlanUsageDescription(ratePlanUsageBased.getRatePlanUsageDescription());
         dto.setDescription(ratePlanUsageBased.getDescription());
         dto.setUnitType(ratePlanUsageBased.getUnitType());
         dto.setUnitMeasurement(ratePlanUsageBased.getUnitMeasurement());
         dto.setUnitCalculation(ratePlanUsageBased.getUnitCalculation());
         dto.setRatePlan(ratePlanUsageBased.getRatePlanId());
		return null;
	}

	@Override
	public RatePlanUsageBased updateRatePlanUsageBased(RatePlanUsageBasedDTO ratePlanUsageBasedDTO,
			RatePlanUsageBased ratePlanUsageBased, RatePlanRepository ratePlanRepository) {
		RatePlanUsageBased localRatePlanUsageBased = ratePlanUsageBased;
		localRatePlanUsageBased.setRatePlanUsageRateId(ratePlanUsageBasedDTO.getRatePlanUsageRateId());
		localRatePlanUsageBased.setRatePlanUsageDescription(ratePlanUsageBasedDTO.getRatePlanUsageDescription());
		localRatePlanUsageBased.setDescription(ratePlanUsageBasedDTO.getDescription());
		localRatePlanUsageBased.setUnitType(ratePlanUsageBasedDTO.getUnitType());
		localRatePlanUsageBased.setUnitMeasurement(ratePlanUsageBasedDTO.getUnitMeasurement());
		localRatePlanUsageBased.setUnitCalculation(ratePlanUsageBasedDTO.getUnitCalculation());
        return  null;
	}

}
