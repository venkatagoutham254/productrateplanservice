package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.rate_plan_freemium_rate_details.RatePlanFreemiumRateDetails;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitFreePriceFixedFrequency;
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

public class RatePlanFreemiumRate {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanFreemiumRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanFreemiumDescription;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false)
    private UnitType unitType;

    @Column(nullable = false)
    private UnitMeasurement unitMeasurement;

    @Column(nullable = false)
    private UnitBillingFrequency unitBillingFrequency;

    @Column(nullable = false)
    private UnitFreePriceFixedFrequency unitFreePriceFixedFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private Long ratePlanId;

    @OneToMany(mappedBy = "ratePlanFreemiumRate")
    private Set<RatePlanFreemiumRateDetails> ratePlanFreemiumRateRatePlanFreemiumRateDetailses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

	
	

	public String getRatePlanFreemiumDescription() {
		return ratePlanFreemiumDescription;
	}

	public void setRatePlanFreemiumDescription(String ratePlanFreemiumDescription) {
		this.ratePlanFreemiumDescription = ratePlanFreemiumDescription;
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

	public UnitBillingFrequency getUnitBillingFrequency() {
		return unitBillingFrequency;
	}

	public void setUnitBillingFrequency(UnitBillingFrequency unitBillingFrequency) {
		this.unitBillingFrequency = unitBillingFrequency;
	}

	public UnitFreePriceFixedFrequency getUnitFreePriceFixedFrequency() {
		return unitFreePriceFixedFrequency;
	}

	public void setUnitFreePriceFixedFrequency(UnitFreePriceFixedFrequency unitFreePriceFixedFrequency) {
		this.unitFreePriceFixedFrequency = unitFreePriceFixedFrequency;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public Set<RatePlanFreemiumRateDetails> getRatePlanFreemiumRateRatePlanFreemiumRateDetailses() {
		return ratePlanFreemiumRateRatePlanFreemiumRateDetailses;
	}

	public void setRatePlanFreemiumRateRatePlanFreemiumRateDetailses(
			Set<RatePlanFreemiumRateDetails> ratePlanFreemiumRateRatePlanFreemiumRateDetailses) {
		this.ratePlanFreemiumRateRatePlanFreemiumRateDetailses = ratePlanFreemiumRateRatePlanFreemiumRateDetailses;
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
	
	public Long getRatePlanFreemiumRateId() {
		return ratePlanFreemiumRateId;
	}

	public void setRatePlanFreemiumRateId(Long ratePlanFreemiumRateId) {
		this.ratePlanFreemiumRateId = ratePlanFreemiumRateId;
	}
	
	

}
