package aforo.productrateplanservice.overagecharges;

import aforo.productrateplanservice.rate_plan.RatePlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_plan_overage_charge")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OverageCharge {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @Column(name = "usage_account", nullable = false, length = 100)
    private String usageAccount;

    @Column(name = "overage_unit_rate", nullable = false)
    private Double overageUnitRate;

    @Column(name = "grace_buffer")
    private Integer graceBuffer;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
}
