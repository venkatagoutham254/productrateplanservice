package aforo.productrateplanservie.product.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private UUID productId;

    @NotNull
    @Size(max = 100)
    private String productName;

    private String productDescription;

    @NotNull
    @Size(max = 255)
    private String status;

}
