package aforo.productrateplanservie.rate_plan_flat_rate;


import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;

@Setter
@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class RatePlanFlatRate {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanFlatRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanFlatDescription;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitType unitType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UnitMeasurement unitMeasurement;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FlatRateUnitCalculation flatRateUnitCalculation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MaxLimitFrequency maxLimitFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "ratePlanFlatRate", cascade = CascadeType.REMOVE)
    private Set<RatePlanFlatRateDetails> ratePlanFlatRateDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;


}