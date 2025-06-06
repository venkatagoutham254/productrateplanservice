package aforo.productrateplanservice.tieredpricing;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "rate_plan_tiered_pricing")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TieredPricing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "rate_plan_id", nullable = false)
    private Long ratePlanId;

    @Column(name = "start_range", nullable = false)
    private Long startRange;

    @Column(name = "end_range", nullable = false)
    private Long endRange;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "uom", nullable = false)
    private String uom;

    @Column(name = "currency", nullable = false)
    private String currency;

    @Column(name = "tier_order")
    private Integer tierOrder;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "description")
    private String description;

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
