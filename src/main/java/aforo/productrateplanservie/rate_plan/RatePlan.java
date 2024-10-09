package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRate;
import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRate;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRate;
import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBased;
import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;
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
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)

public class RatePlan {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanId;

    @Column(nullable = false, length = 100)
    private String ratePlanName;

    @Column(name = "\"description\"", columnDefinition = "longtext")
    private String description;

    @Column(nullable = false)
    private RatePlanType ratePlanType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Long productId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Long currencyId;

    @OneToMany(mappedBy = "ratePlan")
    private Set<RatePlanUsageBased> ratePlanRatePlanUsageBaseds;

    @OneToMany(mappedBy = "ratePlan")
    private Set<RatePlanTieredRate> ratePlanRatePlanTieredRates;

    @OneToMany(mappedBy = "ratePlan")
    private Set<RatePlanFlatRate> ratePlanRatePlanFlatRates;

    @OneToMany(mappedBy = "ratePlan")
    private Set<RatePlanSubscriptionRate> ratePlanRatePlanSubscriptionRates;

    @OneToMany(mappedBy = "ratePlan")
    private Set<RatePlanFreemiumRate> ratePlanRatePlanFreemiumRates;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RatePlanType getRatePlanType() {
		return ratePlanType;
	}

	public void setRatePlanType(RatePlanType ratePlanType) {
		this.ratePlanType = ratePlanType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}
}