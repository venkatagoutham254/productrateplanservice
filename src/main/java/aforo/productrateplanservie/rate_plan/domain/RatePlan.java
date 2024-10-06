package aforo.productrateplanservie.rate_plan.domain;

import aforo.productrateplanservie.currencies.domain.Currencies;
import aforo.productrateplanservie.product.domain.Product;
import aforo.productrateplanservie.rate_plan_flat_rate.domain.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_freemium_rate.domain.RatePlanFreemiumRate;
import aforo.productrateplanservie.rate_plan_subscription_rate.domain.RatePlanSubscriptionRate;
import aforo.productrateplanservie.rate_plan_tiered_rate.domain.RatePlanTieredRate;
import aforo.productrateplanservie.rate_plan_usage_based.domain.RatePlanUsageBased;
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
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
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
    private String ratePlanType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currencies currency;

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

}
