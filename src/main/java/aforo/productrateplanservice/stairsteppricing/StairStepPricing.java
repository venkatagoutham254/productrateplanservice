package aforo.productrateplanservice.stairsteppricing;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "rate_plan_stair_step_pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StairStepPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_plan_id", nullable = false)
    private Long ratePlanId;

    @Column(name = "usage_threshold_start", nullable = false)
    private Long usageThresholdStart;

    @Column(name = "usage_threshold_end", nullable = false)
    private Long usageThresholdEnd;

    @Column(name = "monthly_charge", nullable = false)
    private BigDecimal monthlyCharge;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "version")
    private Integer version;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
