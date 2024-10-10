package aforo.productrateplanservie.rate_plan_tiered_rate;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
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
public class RatePlanTieredRate {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanTieredRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanTieredDescription;

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
    private MaxLimitFrequency maxLimitFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @OneToMany(mappedBy = "ratePlanTieredRate", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<RatePlanTieredRateDetails> ratePlanTieredRateDetails;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    // Getters and Setters
    public Long getRatePlanTieredRateId() {
        return ratePlanTieredRateId;
    }

    public void setRatePlanTieredRateId(Long ratePlanTieredRateId) {
        this.ratePlanTieredRateId = ratePlanTieredRateId;
    }

    public String getRatePlanTieredDescription() {
        return ratePlanTieredDescription;
    }

    public void setRatePlanTieredDescription(String ratePlanTieredDescription) {
        this.ratePlanTieredDescription = ratePlanTieredDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UnitType getUnitType() {
        return unitType;
    }

    public void setUnitType(UnitType unitType) {
        this.unitType = unitType;
    }

    public UnitMeasurement getUnitMeasurement() {
        return unitMeasurement;
    }

    public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
        this.unitMeasurement = unitMeasurement;
    }

    public MaxLimitFrequency getMaxLimitFrequency() {
        return maxLimitFrequency;
    }

    public void setMaxLimitFrequency(MaxLimitFrequency maxLimitFrequency) {
        this.maxLimitFrequency = maxLimitFrequency;
    }

    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
    }

    public Set<RatePlanTieredRateDetails> getRatePlanTieredRateDetails() {
        return ratePlanTieredRateDetails;
    }

    public void setRatePlanTieredRateDetails(Set<RatePlanTieredRateDetails> ratePlanTieredRateDetails) {
        this.ratePlanTieredRateDetails = ratePlanTieredRateDetails;
    }

    public OffsetDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(OffsetDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public OffsetDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(OffsetDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}
