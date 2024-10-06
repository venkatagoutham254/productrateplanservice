package aforo.productrateplanservie.rate_plan_usage_based.domain;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import aforo.productrateplanservie.rate_plan_usage_based_rates.domain.RatePlanUsageBasedRates;
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

}
