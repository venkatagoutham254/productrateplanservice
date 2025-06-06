package aforo.productrateplanservice.volumepricing;

import org.springframework.stereotype.Component;

@Component
public class VolumePricingMapper {

    public VolumePricing toEntity(VolumePricingDTO dto) {
        return VolumePricing.builder()
                .ratePlanId(dto.getRatePlanId())
                .volumeBracket(dto.getVolumeBracket())
                .unitPrice(dto.getUnitPrice())
                .currency(dto.getCurrency())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .build();
    }

    public void updateEntity(VolumePricing entity, VolumePricingDTO dto) {
        entity.setRatePlanId(dto.getRatePlanId());
        entity.setVolumeBracket(dto.getVolumeBracket());
        entity.setUnitPrice(dto.getUnitPrice());
        entity.setCurrency(dto.getCurrency());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }

    public VolumePricingDTO toDTO(VolumePricing entity) {
        return VolumePricingDTO.builder()
                .id(entity.getId())
                .ratePlanId(entity.getRatePlanId())
                .volumeBracket(entity.getVolumeBracket())
                .unitPrice(entity.getUnitPrice())
                .currency(entity.getCurrency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
