package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetails;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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

    @Column(nullable = false)
    private UnitType unitType;

    @Column(nullable = false)
    private UnitMeasurement unitMeasurement;

    @Column(nullable = false)
    private FlatRateUnitCalculation flatRateUnitCalculation;

    @Column(nullable = false)
    private MaxLimitFrequency maxLimitFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private Long ratePlanId;

    @OneToMany(mappedBy = "ratePlanFlatRate")
    private Set<RatePlanFlatRateDetails> ratePlanFlatRateRatePlanFlatRateDetailses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
    public Long getRatePlanFlatRateId() {
		return ratePlanFlatRateId;
	}

	public void setRatePlanFlatRateId(Long ratePlanFlatRateId) {
		this.ratePlanFlatRateId = ratePlanFlatRateId;
	}

	public String getRatePlanFlatDescription() {
		return ratePlanFlatDescription;
	}

	public void setRatePlanFlatDescription(String ratePlanFlatDescription) {
		this.ratePlanFlatDescription = ratePlanFlatDescription;
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

	public FlatRateUnitCalculation getFlatRateUnitCalculation() {
		return flatRateUnitCalculation;
	}

	public void setFlatRateUnitCalculation(FlatRateUnitCalculation flatRateUnitCalculation) {
		this.flatRateUnitCalculation = flatRateUnitCalculation;
	}

	public MaxLimitFrequency getMaxLimitFrequency() {
		return maxLimitFrequency;
	}

	public void setMaxLimitFrequency(MaxLimitFrequency maxLimitFrequency) {
		this.maxLimitFrequency = maxLimitFrequency;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}


}
