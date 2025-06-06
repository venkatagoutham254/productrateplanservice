package aforo.productrateplanservice.tieredpricing;

import org.springframework.stereotype.Component;

@Component
public class TieredPricingMapper {

    public TieredPricing toEntity(TieredPricingDTO dto) {
        return TieredPricing.builder()
                .ratePlanId(dto.getRatePlanId())
                .startRange(dto.getStartRange())
                .endRange(dto.getEndRange())
                .unitPrice(dto.getUnitPrice())
                .uom(dto.getUom())
                .currency(dto.getCurrency())
                .tierOrder(dto.getTierOrder())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .description(dto.getDescription())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .build();
    }

    public void updateEntity(TieredPricing entity, TieredPricingDTO dto) {
        entity.setRatePlanId(dto.getRatePlanId());
        entity.setStartRange(dto.getStartRange());
        entity.setEndRange(dto.getEndRange());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setUom(dto.getUom());
        entity.setCurrency(dto.getCurrency());
        entity.setTierOrder(dto.getTierOrder());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
    }

    public TieredPricingDTO toDTO(TieredPricing entity) {
        return TieredPricingDTO.builder()
                .id(entity.getId())
                .ratePlanId(entity.getRatePlanId())
                .startRange(entity.getStartRange())
                .endRange(entity.getEndRange())
                .unitPrice(entity.getUnitPrice())
                .uom(entity.getUom())
                .currency(entity.getCurrency())
                .tierOrder(entity.getTierOrder())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .description(entity.getDescription())
                .build();
    }
}
