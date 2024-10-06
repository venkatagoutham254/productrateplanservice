package aforo.productrateplanservie.currencies.domain;

import aforo.productrateplanservie.rate_plan.domain.RatePlan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
public class Currencies {

    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long currencyId;

    @Column(nullable = false, length = 3)
    private String currencyCode;

    @Column(nullable = false, length = 50)
    private String currencyName;

    @Column(nullable = false, columnDefinition = "tinyint", length = 1)
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
