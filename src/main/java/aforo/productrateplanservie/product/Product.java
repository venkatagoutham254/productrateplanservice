package aforo.productrateplanservie.product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.OffsetDateTime;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.util.enums.Status;
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Product {
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@Column(nullable = false, length = 100,unique=true)
	private String productName;
	@Column(columnDefinition = "longtext")
	private String productDescription;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	@OneToMany(mappedBy = "product")
	private Set<RatePlan> productRatePlans;
	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private OffsetDateTime dateCreated;
	@UpdateTimestamp
	@Column(nullable = false)
	private OffsetDateTime lastUpdated;  
	@Column
	private Long producerId;
	@Column
	private Long organizationId;
	@Column
	private Long divisionId;

	public Long getProducerId() {
		return producerId;
	}
	public void setProducerId(Long producerId) {
		this.producerId = producerId;
	}
	public Long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	public Long getDivisionId() {
		return divisionId;
	}
	public void setDivisionId(Long divisionId) {
		this.divisionId = divisionId;
	}
	public Long getProductId() {
		return productId;
	}
	public String getProductName() {
		return productName;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public Set<RatePlan> getProductRatePlans() {
		return productRatePlans;
	}
	public void setProductRatePlans(Set<RatePlan> productRatePlans) {
		this.productRatePlans = productRatePlans;
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

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
}

