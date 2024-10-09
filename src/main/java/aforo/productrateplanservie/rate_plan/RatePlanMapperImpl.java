package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.CurrenciesRepository;
import aforo.productrateplanservie.product.ProductRepository;

public class RatePlanMapperImpl implements RatePlanMapper {

	@Override
	public RatePlanDTO updateRatePlanDTO(RatePlan ratePlan, RatePlanDTO ratePlanDTO) {
		RatePlanDTO dto = ratePlanDTO;
		dto.setRatePlanId(ratePlan.getRatePlanId());
		dto.setRatePlanName(ratePlan.getRatePlanName());
		dto.setDescription(ratePlan.getDescription());
		dto.setRatePlanType(ratePlan.getRatePlanType());
		dto.setStartDate(ratePlan.getStartDate());
		dto.setEndDate(ratePlan.getEndDate());
		dto.setStatus(ratePlan.getStatus());
		dto.setProductId(ratePlan.getProductId());
		dto.setCurrencyId(ratePlan.getCurrencyId());
		
		
		return dto;
	}

	@Override
	public RatePlan updateRatePlan(RatePlanDTO ratePlanDTO, RatePlan ratePlan, ProductRepository productRepository,
			CurrenciesRepository currenciesRepository) {
		
	    RatePlan localRatePlan = ratePlan;
	    localRatePlan.setRatePlanId(ratePlanDTO.getRatePlanId());
	    localRatePlan.setRatePlanName(ratePlanDTO.getRatePlanName());
	    localRatePlan.setDescription(ratePlanDTO.getDescription());
	    localRatePlan.setRatePlanType(ratePlanDTO.getRatePlanType());
	    localRatePlan.setStartDate(ratePlanDTO.getStartDate());
	    localRatePlan.setEndDate(ratePlanDTO.getEndDate());
	    localRatePlan.setStatus(ratePlanDTO.getStatus());
	    localRatePlan.setProductId(ratePlanDTO.getProductId());
	    localRatePlan.setCurrencyId(ratePlanDTO.getCurrencyId());
		return localRatePlan;
	}
	
	
	

}
