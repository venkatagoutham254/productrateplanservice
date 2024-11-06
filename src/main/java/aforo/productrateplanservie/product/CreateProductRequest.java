package aforo.productrateplanservie.product;

import aforo.productrateplanservie.util.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateProductRequest {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, description = "productName is required")
    @Size(max = 100)
    private String productName;
    private String productDescription;
    private Status status; // You may want to use an Enum for this
    private Long producerId;
    private Long organizationId;
    private Long divisionId;
}