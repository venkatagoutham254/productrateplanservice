package aforo.productrateplanservice.product.request;

import aforo.productrateplanservice.product.enums.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductSQLResultRequest {
    private String queryTemplate;
    private DBType dbType;
    private String resultSize;
    private Freshness freshness;
    private ExecutionFrequency executionFrequency;
    private String expectedRowRange;
    private Boolean isCached;
    private JoinComplexity joinComplexity;
}
