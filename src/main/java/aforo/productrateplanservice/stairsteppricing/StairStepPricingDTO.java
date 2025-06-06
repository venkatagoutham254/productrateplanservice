package aforo.productrateplanservice.stairsteppricing;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StairStepPricingDTO {
    private Long id;
    private Long ratePlanId;
    private Long usageThresholdStart;
    private Long usageThresholdEnd;
    private BigDecimal monthlyCharge;
    private String currency;
    private Date startDate;
    private Date endDate;
}
