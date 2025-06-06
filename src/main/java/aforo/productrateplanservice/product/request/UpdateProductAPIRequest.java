package aforo.productrateplanservice.product.request;

import lombok.*;
import aforo.productrateplanservice.product.enums.AuthType;
import aforo.productrateplanservice.product.enums.LatencyClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateProductAPIRequest {
    private String endpointUrl;
    private AuthType authType;
    private String payloadSizeMetric;
    private String rateLimitPolicy;
    private String meteringGranularity;
    private String grouping;
    private Boolean cachingFlag;  // NOTE: boxed type
    private LatencyClass latencyClass;
}
