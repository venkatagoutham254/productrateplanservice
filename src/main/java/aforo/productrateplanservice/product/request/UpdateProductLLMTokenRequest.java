package aforo.productrateplanservice.product.request;

import aforo.productrateplanservice.product.enums.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductLLMTokenRequest {
    private TokenProvider tokenProvider;
    private String modelName;
    private BigDecimal tokenUnitCost;
    private CalculationMethod calculationMethod;
    private Integer quota;
    private String promptTemplate;
    private InferencePriority inferencePriority;
    private ComputeTier computeTier;
}
