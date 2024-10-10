package aforo.productrateplanservie.rate_plan_usage_based;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservie.rate_plan.RatePlan;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class RatePlanUsageBased {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanUsageRateId;

    @Column(nullable = false, length = 100)
    private String ratePlanUsageDescription;

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
    public Long getRatePlanUsageRateId() {
        return ratePlanUsageRateId;
    }

    public void setRatePlanUsageRateId(Long ratePlanUsageRateId) {
        this.ratePlanUsageRateId = ratePlanUsageRateId;
    }

    public String getRatePlanUsageDescription() {
        return ratePlanUsageDescription;
    }

    public void setRatePlanUsageDescription(String ratePlanUsageDescription) {
        this.ratePlanUsageDescription = ratePlanUsageDescription;
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
    
    // Add setter for ratePlanId if needed
    public void setRatePlanId(Long ratePlanId) {
        if (this.ratePlan != null) {
            this.ratePlan.setRatePlanId(ratePlanId); // Ensure RatePlan has a setRatePlanId method
        }
    }
}
