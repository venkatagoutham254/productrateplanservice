package aforo.productrateplanservice.freemium;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FreemiumMapper {

    public Freemium toEntity(FreemiumDTO dto, RatePlan ratePlan) {
        return Freemium.builder()
                .ratePlan(ratePlan)
                .freemiumType(dto.getFreemiumType())
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
