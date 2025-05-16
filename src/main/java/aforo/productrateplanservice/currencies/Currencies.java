package aforo.productrateplanservice.currencies;

import jakarta.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

import lombok.Data;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import aforo.productrateplanservice.rate_plan.RatePlan;

@Entity
@Table(name="aforo_currencies")
@EntityListeners(AuditingEntityListener.class)
@Data
public class Currencies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long currencyId;

    @Column(nullable = false, length = 3)
    private String currencyCode;

    @Column(nullable = false, length = 50)
    private String currencyName;

    @Column(nullable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "currency")
    private Set<RatePlan> currencyRatePlans;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
}
