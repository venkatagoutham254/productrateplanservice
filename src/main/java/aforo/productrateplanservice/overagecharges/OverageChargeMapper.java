package aforo.productrateplanservice.overagecharges;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class OverageChargeMapper {

    public OverageCharge toEntity(OverageChargeDTO dto, RatePlan ratePlan) {
        return OverageCharge.builder()
                .ratePlan(ratePlan)
                .usageAccount(dto.getUsageAccount())
                .overageUnitRate(dto.getOverageUnitRate())
                .graceBuffer(dto.getGraceBuffer())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .createdBy("system")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
