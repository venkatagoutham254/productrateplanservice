package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.currencies.Currencies;
import aforo.productrateplanservie.product.Product;
import aforo.productrateplanservie.rate_plan_flat_rate.RatePlanFlatRate;
import aforo.productrateplanservie.rate_plan_freemium_rate.RatePlanFreemiumRate;
import aforo.productrateplanservie.rate_plan_subscription_rate.RatePlanSubscriptionRate;
import aforo.productrateplanservie.rate_plan_tiered_rate.RatePlanTieredRate;
import aforo.productrateplanservie.rate_plan_usage_based.RatePlanUsageBased;
import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Data

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatePlanType ratePlanType;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currencies currency;

    @OneToMany(mappedBy = "ratePlan",cascade = CascadeType.REMOVE)
    private Set<RatePlanUsageBased> ratePlanRatePlanUsageBaseds;

    @OneToMany(mappedBy = "ratePlan",cascade = CascadeType.ALL)
    private Set<RatePlanTieredRate> ratePlanRatePlanTieredRates;

    @OneToMany(mappedBy = "ratePlan",cascade = CascadeType.REMOVE)
    private Set<RatePlanFlatRate> ratePlanRatePlanFlatRates;

    @OneToMany(mappedBy = "ratePlan",cascade = CascadeType.REMOVE)
    private Set<RatePlanSubscriptionRate> ratePlanRatePlanSubscriptionRates;

    @OneToMany(mappedBy = "ratePlan",cascade = CascadeType.REMOVE)
    private Set<RatePlanFreemiumRate> ratePlanRatePlanFreemiumRates;


    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    // Getters and Setters

}
