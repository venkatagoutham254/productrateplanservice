package aforo.productrateplanservie.product;

import aforo.productrateplanservie.util.enums.Status;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class ProductDTO {

    private Long productId;

    @NotNull
    @Size(max = 100)
    private String productName;

    private String productDescription;

    @NotNull
    @Size(max = 255)
    private Status status;
    
    @Column(nullable = false)
	private Long producerId;
	@Column
	private Long organizationId;
	@Column
	private Long divisionId;


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public ProductDTO(Long productId, @NotNull @Size(max = 100) String productName, String productDescription,
			@NotNull @Size(max = 255) Status status, Long organizationId, Long producerId, Long divisionId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.status = status;
		this.organizationId = organizationId;
		this.producerId = producerId;
		this.divisionId = divisionId;
		
		
		
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}
    

}
