package aforo.productrateplanservie.product;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class ProductDTO {
    private Long productId;
    @NotNull
    @Size(max = 100)
    private String productName;
    private String productDescription;
    @NotNull
    private Status status;
    private Long producerId;
    private Long organizationId;
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
			@NotNull Status status, Long producerId, Long organizationId, Long divisionId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.status = status;
		this.producerId = producerId;
		this.organizationId = organizationId;
		this.divisionId = divisionId;
	}
	public ProductDTO() {
		super();
	}
}

