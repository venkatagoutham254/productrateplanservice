package aforo.productrateplanservice.setupfee;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatePlanSetupFeeDTO {
    private Long ratePlanId;
    private Double oneTimeFee;
    private String applicationTiming;
    private String invoiceDescription;
}
