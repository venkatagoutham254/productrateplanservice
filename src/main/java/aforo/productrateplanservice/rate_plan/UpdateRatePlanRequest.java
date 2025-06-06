package aforo.productrateplanservice.rate_plan;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;  
import aforo.productrateplanservice.product.enums.RatePlanType;
import aforo.productrateplanservice.product.enums.RatePlanStatus;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateRatePlanRequest {

    private Long productId;

    private String productName;

    private String ratePlanName;

    private String description;

    private RatePlanType ratePlanType;

    private RatePlanStatus status;
}

