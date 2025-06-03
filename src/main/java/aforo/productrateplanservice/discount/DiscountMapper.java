package aforo.productrateplanservice.discount;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DiscountMapper {

    public Discount toEntity(DiscountDTO dto, RatePlan ratePlan) {
        return Discount.builder()
                .ratePlan(ratePlan)
                .discountType(dto.getDiscountType())
                .eligibility(dto.getEligibility())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .createdBy("system")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
