package aforo.productrateplanservice.minimumcommitment;

import aforo.productrateplanservice.rate_plan.RatePlan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MinimumCommitmentMapper {

    public MinimumCommitment toEntity(MinimumCommitmentDTO dto, RatePlan ratePlan) {
        return MinimumCommitment.builder()
                .ratePlan(ratePlan)
                .minimumCommitment(dto.getMinimumCommitment())
                .commitmentDuration(dto.getCommitmentDuration())
                .commitmentUnit(dto.getCommitmentUnit())
                .isActive(true)
                .isDeleted(false)
                .version(1)
                .createdBy("system")
                .createdAt(LocalDateTime.now())
                .build();
    }
}
