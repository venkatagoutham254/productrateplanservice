package aforo.productrateplanservie.currencies;

import aforo.productrateplanservie.rate_plan.RatePlan;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@EntityListeners(AuditingEntityListener.class)

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

    @OneToMany(mappedBy = "currencyId")
    private Set<RatePlan> currencyRatePlans;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @LastModifiedDate
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
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
