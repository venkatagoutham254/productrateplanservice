package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.rate_plan_usage_based_rates.RatePlanUsageBasedRates;
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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class RatePlanUsageBased {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanUsageRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanUsageDescription;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false)
    private String unitType;

    @Column(nullable = false)
    private String unitMeasurement;

    @Column(nullable = false)
    private String unitCalculation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @OneToMany(mappedBy = "ratePlanUsageRate")
    private Set<RatePlanUsageBasedRates> ratePlanUsageRateRatePlanUsageBasedRateses;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

	public Object getRatePlanUsageRateId() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getRatePlanUsageDescription() {
		return ratePlanUsageDescription;
	}

	public void setRatePlanUsageDescription(String ratePlanUsageDescription) {
		this.ratePlanUsageDescription = ratePlanUsageDescription;
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

	public String getUnitCalculation() {
		return unitCalculation;
	}

	public void setUnitCalculation(String unitCalculation) {
		this.unitCalculation = unitCalculation;
	}

	public RatePlan getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(RatePlan ratePlan) {
		this.ratePlan = ratePlan;
	}

	public Set<RatePlanUsageBasedRates> getRatePlanUsageRateRatePlanUsageBasedRateses() {
		return ratePlanUsageRateRatePlanUsageBasedRateses;
	}

	public void setRatePlanUsageRateRatePlanUsageBasedRateses(
			Set<RatePlanUsageBasedRates> ratePlanUsageRateRatePlanUsageBasedRateses) {
		this.ratePlanUsageRateRatePlanUsageBasedRateses = ratePlanUsageRateRatePlanUsageBasedRateses;
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

	public void setRatePlanUsageRateId(Long ratePlanUsageRateId) {
		this.ratePlanUsageRateId = ratePlanUsageRateId;
	}

}
