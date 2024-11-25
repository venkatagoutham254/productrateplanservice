package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.util.enums.UnitCalculation;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetails;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
@Table(name="aforo_rate_plan_tiered_rate")
public class RatePlanTieredRate {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanTieredRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanTieredDescription;

    @Column(name = "\"description\"")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitMeasurement unitMeasurement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitCalculation unitCalculation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @OneToMany(mappedBy = "ratePlanTieredRate", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER,orphanRemoval = true)
    private Set<RatePlanTieredRateDetails> ratePlanTieredRateDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
