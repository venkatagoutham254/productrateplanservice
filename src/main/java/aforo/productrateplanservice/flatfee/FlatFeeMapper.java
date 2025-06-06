package aforo.productrateplanservice.flatfee;

import org.springframework.stereotype.Component;

@Component
public class FlatFeeMapper {

    public FlatFee toEntity(FlatFeeCreateUpdateDTO dto) {
        return FlatFee.builder()
                .ratePlanId(dto.getRatePlanId())
                .recurringFee(dto.getRecurringFee())
                .billingFrequency(dto.getBillingFrequency())
                .currency(dto.getCurrency())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .build();
    }

    public void updateEntity(FlatFee entity, FlatFeeCreateUpdateDTO dto) {
        entity.setRatePlanId(dto.getRatePlanId());
        entity.setRecurringFee(dto.getRecurringFee());
        entity.setBillingFrequency(dto.getBillingFrequency());
        entity.setCurrency(dto.getCurrency());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
    }

    public FlatFeeDTO toDTO(FlatFee entity) {
        return FlatFeeDTO.builder()
                .id(entity.getId())
                .ratePlanId(entity.getRatePlanId())
                .recurringFee(entity.getRecurringFee())
                .billingFrequency(entity.getBillingFrequency())
                .currency(entity.getCurrency())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .build();
    }
}
