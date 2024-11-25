package aforo.productrateplanservie.rate_plan_flat_rate_details;

import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name="aforo_rate_plan_flat_rate_details")
public class RatePlanFlatRateDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal unitRate;

    @NotNull
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal maxLimit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_flat_rate_id", nullable = false)
    private RatePlanFlatRate ratePlanFlatRate;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
