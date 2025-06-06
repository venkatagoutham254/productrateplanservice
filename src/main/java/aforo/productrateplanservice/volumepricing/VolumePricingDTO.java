package aforo.productrateplanservice.volumepricing;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolumePricingDTO {
    private Long id;
    private Long ratePlanId;
    private String volumeBracket;
    private BigDecimal unitPrice;
    private String currency;
    private Date startDate;
    private Date endDate;
}
