package aforo.productrateplanservice.resetperiod;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetPeriodDTO {
    private Long ratePlanId;
    private String resetFrequency;
    private LocalDate startDate;
    private LocalDate endDate;
}
