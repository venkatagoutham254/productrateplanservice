package aforo.productrateplanservice.setupfee;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import aforo.productrateplanservice.rate_plan.RatePlan;

@Entity
@Table(name = "rate_plan_setup_fee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatePlanSetupFee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @Column(name = "one_time_fee", nullable = false)
    private Double oneTimeFee;

    @Column(name = "application_timing", nullable = false)
    private String applicationTiming;

    @Column(name = "invoice_description")
    private String invoiceDescription;

    @Column(name = "is_active")
    private Boolean isActive = true;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "version")
    private Integer version = 1;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_by")
    private String updatedBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
