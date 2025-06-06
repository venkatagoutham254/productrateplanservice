package aforo.productrateplanservice.tieredpricing;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TieredPricingDTO {
    private Long id;
    private Long ratePlanId;
    private Long startRange;
    private Long endRange;
    private BigDecimal unitPrice;
    private String uom;
    private String currency;
    private Integer tierOrder;
    private Date startDate;
    private Date endDate;
    private String description;
}
