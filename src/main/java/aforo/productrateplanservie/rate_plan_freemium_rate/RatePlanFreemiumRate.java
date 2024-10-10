package aforo.productrateplanservie.rate_plan_freemium_rate;
import jakarta.persistence.*;
import java.time.OffsetDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservie.rate_plan.RatePlan;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class RatePlanFreemiumRate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanFreemiumRateId;

    @Column(nullable = false, length = 100)
    private String freemiumRateDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    // Getters and Setters
    public Long getRatePlanFreemiumRateId() {
        return ratePlanFreemiumRateId;
    }

    public void setRatePlanFreemiumRateId(Long ratePlanFreemiumRateId) {
        this.ratePlanFreemiumRateId = ratePlanFreemiumRateId;
    }

    public String getFreemiumRateDescription() {
        return freemiumRateDescription;
    }

    public void setFreemiumRateDescription(String freemiumRateDescription) {
        this.freemiumRateDescription = freemiumRateDescription;
    }

    public RatePlan getRatePlan() {
        return ratePlan;
    }

    public void setRatePlan(RatePlan ratePlan) {
        this.ratePlan = ratePlan;
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
