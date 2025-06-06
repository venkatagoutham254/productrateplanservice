package aforo.productrateplanservice.stairsteppricing;

import org.springframework.stereotype.Component;

@Component
public class StairStepPricingMapper {

    public StairStepPricing toEntity(StairStepPricingDTO dto) {
        return StairStepPricing.builder()
                .ratePlanId(dto.getRatePlanId())
                .usageThresholdStart(dto.getUsageThresholdStart())
                .usageThresholdEnd(dto.getUsageThresholdEnd())
                .monthlyCharge(dto.getMonthlyCharge())
                .currency(dto.getCurrency())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .build();
    }

    public void updateEntity(StairStepPricing entity, StairStepPricingDTO dto) {
        entity.setRatePlanId(dto.getRatePlanId());
        entity.setUsageThresholdStart(dto.getUsageThresholdStart());
        entity.setUsageThresholdEnd(dto.getUsageThresholdEnd());
        entity.setMonthlyCharge(dto.getMonthlyCharge());
        entity.setCurrency(dto.getCurrency());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }

    public StairStepPricingDTO toDTO(StairStepPricing entity) {
        return StairStepPricingDTO.builder()
                .id(entity.getId())
                .ratePlanId(entity.getRatePlanId())
                .usageThresholdStart(entity.getUsageThresholdStart())
                .usageThresholdEnd(entity.getUsageThresholdEnd())
                .monthlyCharge(entity.getMonthlyCharge())
                .currency(entity.getCurrency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
