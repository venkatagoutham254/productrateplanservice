package aforo.productrateplanservice.rate_plan;


import aforo.productrateplanservice.product.enums.RatePlanType;
import aforo.productrateplanservice.product.enums.RatePlanStatus;
import lombok.Data;
import java.time.LocalDateTime; 
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatePlanDTO {
    private Long ratePlanId;
    private Long productId;
    private String productName;
    private String ratePlanName;
    private String description;
    private RatePlanType ratePlanType;
    private RatePlanStatus status;
    private LocalDateTime createdAt;
}
