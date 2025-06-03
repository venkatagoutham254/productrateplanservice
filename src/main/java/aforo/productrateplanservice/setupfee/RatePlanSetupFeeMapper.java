package aforo.productrateplanservice.setupfee;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;

@Component
public class RatePlanSetupFeeMapper {

    public RatePlanSetupFee toEntity(RatePlanSetupFeeDTO dto, RatePlan ratePlan) {
        return RatePlanSetupFee.builder()
                .ratePlan(ratePlan)
                .oneTimeFee(dto.getOneTimeFee())
                .applicationTiming(dto.getApplicationTiming())
                .invoiceDescription(dto.getInvoiceDescription())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .createdBy("system")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
