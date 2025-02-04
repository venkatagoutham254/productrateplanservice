package aforo.productrateplanservie.product;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class ProductDTO {
    private Long productId;
    @NotNull
    @Size(max = 100)
    private String productName;
    private String productDescription;
    @NotNull
    private Status status;
    private Long customerId;
    private Long organizationId;
    private Long divisionId;

    public ProductDTO(Long productId, @NotNull @Size(max = 100) String productName, String productDescription,
			@NotNull Status status, Long customerId, Long organizationId, Long divisionId) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.status = status;
		this.customerId = customerId;
		this.organizationId = organizationId;
		this.divisionId = divisionId;
	}
	public ProductDTO() {
		super();
	}
}
