package aforo.productrateplanservie.product;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Long productId;

    @NotNull
    @Size(max = 100)
    private String productName;

    private String productDescription;

    @NotNull
    @Size(max = 255)
    private String status;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ProductDTO(Long productId, @NotNull @Size(max = 100) String productName, String productDescription,
			@NotNull @Size(max = 255) String status) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.status = status;
	}

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}
    

}
