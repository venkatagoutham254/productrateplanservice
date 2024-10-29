package aforo.productrateplanservie.rate_plan_freemium_rate_details;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RatePlanFreemiumRateDetailsDTO {

    private Long id;
    private BigDecimal freemiumMaxUnitQuantity;
    private Long ratePlanFreemiumRateId; // Expose only the ID of the associated RatePlanFreemiumRate
    private OffsetDateTime dateCreated;
    private OffsetDateTime lastUpdated;

}
