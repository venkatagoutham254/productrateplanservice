package aforo.productrateplanservice.flatfee;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlatFeeDTO {
    private Long id;
    private Long ratePlanId;
    private BigDecimal recurringFee;
    private String billingFrequency;
    private String currency;
    private Date startDate;
    private Date endDate;
}
