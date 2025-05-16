package aforo.productrateplanservice.product.entity;

import jakarta.persistence.*;
import lombok.*;
import aforo.productrateplanservice.product.enums.TokenProvider;
import aforo.productrateplanservice.product.enums.CalculationMethod;
import aforo.productrateplanservice.product.enums.InferencePriority;
import aforo.productrateplanservice.product.enums.ComputeTier;
import java.math.BigDecimal;

@Entity
@Table(name = "aforo_product_llmtoken")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductLLMToken {

    @Id
    private Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenProvider tokenProvider;

    @Column(nullable = false)
    private String modelName;

    private BigDecimal tokenUnitCost;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CalculationMethod calculationMethod;

    private Integer quota;

    @Column(columnDefinition = "TEXT")
    private String promptTemplate;

    @Enumerated(EnumType.STRING)
    private InferencePriority inferencePriority;

    @Enumerated(EnumType.STRING)
    private ComputeTier computeTier;
}

