package aforo.productrateplanservie.product;
import aforo.productrateplanservie.util.enums.ProductType;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProductDTO {
    private Long productId;
    @NotNull
    @Size(max = 100)
    private String productName;
    private String productDescription;	
	private ProductType productType;
	private String apiEndpoint;
	private String productFileLocation;
	private String documentation;
	private String documentationFileLocation;
	private String fileName;
	private String customerName;
    @NotNull
    private Status status;
    private Long customerId;
    private Long organizationId;
    private Long divisionId;

    public ProductDTO(Long productId, @NotNull @Size(max = 100) String productName, String productDescription,
			@NotNull Status status, Long customerId, Long organizationId, Long divisionId, String fileName,
			String customerName, String apiEndpoint, String productFileLocation, String documentation, String documentationFileLocation) {
		super();
		this.productId = productId;
		this.productName = productName;
		this.productDescription = productDescription;
		this.status = status;
		this.customerId = customerId;
		this.organizationId = organizationId;
		this.divisionId = divisionId;
		this.fileName = fileName;
		this.customerName = customerName;
		this.apiEndpoint = apiEndpoint;
		this.productFileLocation = productFileLocation;
		this.documentation = documentation;
		this.documentationFileLocation = documentationFileLocation;
	}
	
	public ProductDTO() {
		super();
	}
}
