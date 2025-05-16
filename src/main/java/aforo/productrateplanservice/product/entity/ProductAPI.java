package aforo.productrateplanservice.product.entity;

import jakarta.persistence.*;
import lombok.*;
import aforo.productrateplanservice.product.enums.AuthType;
import aforo.productrateplanservice.product.enums.LatencyClass;

@Entity
@Table(name = "aforo_product_api")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductAPI {

    @Id
    private Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "productId")
    private Product product;

    @Column(nullable = false)
    private String endpointUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AuthType authType;

    private String payloadSizeMetric;
    private String rateLimitPolicy;
    private String meteringGranularity;
    private String grouping;

    private boolean cachingFlag;

    @Enumerated(EnumType.STRING)
    private LatencyClass latencyClass;
}
