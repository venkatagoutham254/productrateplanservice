package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
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

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Data
public class RatePlanSubscriptionRate {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanSubscriptionRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanSubscriptionDescription;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false)
    private String unitType;

    @Column(nullable = false)
    private String unitMeasurement;

    @Column(nullable = false)
    private String unitBillingFrequency;

    @Column(nullable = false)
    private String unitPriceFixedFrequency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @OneToMany(mappedBy = "ratePlanSubscriptionRate")
    private Set<RatePlanSubscriptionRateDetails> ratePlanSubscriptionRateRatePlanSubscriptionRateDetailses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

	public Long getRatePlanSubscriptionRateId() {
		return ratePlanSubscriptionRateId;
	}

	public void setRatePlanSubscriptionRateId(Long ratePlanSubscriptionRateId) {
		this.ratePlanSubscriptionRateId = ratePlanSubscriptionRateId;
	}

	public String getRatePlanSubscriptionDescription() {
		return ratePlanSubscriptionDescription;
	}

	public void setRatePlanSubscriptionDescription(String ratePlanSubscriptionDescription) {
		this.ratePlanSubscriptionDescription = ratePlanSubscriptionDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(String unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public String getUnitBillingFrequency() {
		return unitBillingFrequency;
	}

	public void setUnitBillingFrequency(String unitBillingFrequency) {
		this.unitBillingFrequency = unitBillingFrequency;
	}

	public String getUnitPriceFixedFrequency() {
		return unitPriceFixedFrequency;
	}

	public void setUnitPriceFixedFrequency(String unitPriceFixedFrequency) {
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
	}

	public RatePlan getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(RatePlan ratePlan) {
		this.ratePlan = ratePlan;
	}

	public Set<RatePlanSubscriptionRateDetails> getRatePlanSubscriptionRateRatePlanSubscriptionRateDetailses() {
		return ratePlanSubscriptionRateRatePlanSubscriptionRateDetailses;
	}

	public void setRatePlanSubscriptionRateRatePlanSubscriptionRateDetailses(
			Set<RatePlanSubscriptionRateDetails> ratePlanSubscriptionRateRatePlanSubscriptionRateDetailses) {
		this.ratePlanSubscriptionRateRatePlanSubscriptionRateDetailses = ratePlanSubscriptionRateRatePlanSubscriptionRateDetailses;
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
