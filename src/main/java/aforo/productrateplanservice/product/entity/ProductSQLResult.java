package aforo.productrateplanservice.product.entity;

import jakarta.persistence.*;
import lombok.*;
import aforo.productrateplanservice.product.enums.DBType;
import aforo.productrateplanservice.product.enums.Freshness;
import aforo.productrateplanservice.product.enums.ExecutionFrequency;
import aforo.productrateplanservice.product.enums.JoinComplexity;

@Entity
@Table(name = "aforo_product_sqlresult")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductSQLResult {

    @Id
    private Long productId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "productId")
    private Product product;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String queryTemplate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DBType dbType;

    private String resultSize;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Freshness freshness;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ExecutionFrequency executionFrequency;

    private String expectedRowRange;

    private boolean isCached;

    @Enumerated(EnumType.STRING)
    private JoinComplexity joinComplexity;
}
