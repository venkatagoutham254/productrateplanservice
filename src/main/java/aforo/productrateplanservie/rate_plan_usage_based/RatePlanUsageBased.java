package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservie.rate_plan.RatePlan;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name="aforo_rate_plan_usage_based")
public class RatePlanUsageBased {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanUsageRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanUsageDescription;

    @Column(name = "\"description\"")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitMeasurement unitMeasurement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitCalculation unitCalculation;

    @OneToMany(mappedBy = "ratePlanUsageBased", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RatePlanUsageBasedRates> RatePlanUsageBasedRates;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

}
