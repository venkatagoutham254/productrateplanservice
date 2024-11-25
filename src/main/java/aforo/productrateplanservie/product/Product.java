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

import jakarta.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import aforo.productrateplanservie.rate_plan.RatePlan;
import aforo.productrateplanservie.util.enums.Status;
@Entity
@Data
@EntityListeners(AuditingEntityListener.class)
@Table(name="aforo_product")
public class Product {
	@Id
	@Column(nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long productId;
	@Column(nullable = false, length = 100,unique=true)
	private String productName;
	@Column(nullable = true)
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

}

