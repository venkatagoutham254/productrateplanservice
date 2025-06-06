package aforo.productrateplanservice.rate_plan;

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

import aforo.productrateplanservice.product.entity.Product;
import aforo.productrateplanservice.product.enums.RatePlanType;
import aforo.productrateplanservice.product.enums.RatePlanStatus;
import java.time.LocalDateTime;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Table(name = "aforo_rate_plan", uniqueConstraints = {
    @UniqueConstraint(columnNames = "rate_plan_name")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ratePlanId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "rate_plan_name", nullable = false, unique = true)
    private String ratePlanName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatePlanType ratePlanType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RatePlanStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }
}
