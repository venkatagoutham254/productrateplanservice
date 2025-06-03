package aforo.productrateplanservice.overagecharges;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OverageChargeDTO {
    private Long ratePlanId;
    private String usageAccount;
    private Double overageUnitRate;
    private Integer graceBuffer;
}
