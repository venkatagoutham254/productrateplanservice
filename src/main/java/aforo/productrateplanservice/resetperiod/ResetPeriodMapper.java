package aforo.productrateplanservice.resetperiod;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ResetPeriodMapper {

    public ResetPeriod toEntity(ResetPeriodDTO dto, RatePlan ratePlan) {
        return ResetPeriod.builder()
                .ratePlan(ratePlan)
                .resetFrequency(dto.getResetFrequency())
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
