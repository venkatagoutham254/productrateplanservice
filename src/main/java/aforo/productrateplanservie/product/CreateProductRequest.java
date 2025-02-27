package aforo.productrateplanservie.product;

import aforo.productrateplanservie.util.enums.ProductType;
import aforo.productrateplanservie.util.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProductRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "productName is required")
    @Size(max = 100)
    @NotNull(message = "product name cannot be null")
    private String productName;
    @NotNull(message = "product description cannot be null")
    private String productDescription;
    @NotNull(message = "status cannot be null")
    private Status status;
    @NotNull(message = "customerId cannot be null")
    private Long customerId;
    private Long organizationId;
    private Long divisionId;
    

    private String fileName;
    private String customerName;
    private String apiEndpoint;
    private String documentation;
    private ProductType productType;
    private String productFileLocation;
    private String documentationFileLocation;
}