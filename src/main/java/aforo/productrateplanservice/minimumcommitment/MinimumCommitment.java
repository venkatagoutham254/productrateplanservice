package aforo.productrateplanservice.minimumcommitment;

import aforo.productrateplanservice.rate_plan.RatePlan;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "rate_plan_minimum_commitment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinimumCommitment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rate_plan_id", nullable = false)
    private RatePlan ratePlan;

    @Column(name = "minimum_commitment", nullable = false)
    private Double minimumCommitment;

    @Column(name = "commitment_duration", nullable = false)
    private String commitmentDuration;

    @Column(name = "commitment_unit", nullable = false)
    private String commitmentUnit;

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
