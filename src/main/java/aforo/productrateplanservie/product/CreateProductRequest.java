package aforo.productrateplanservie.product;

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
    @NotNull(message = "producerId cannot be null")
    private Long producerId;
    @NotNull(message = "organizationId cannot be null")
    private Long organizationId;
    @NotNull(message = "divisionId cannot be null")
    private Long divisionId;
}