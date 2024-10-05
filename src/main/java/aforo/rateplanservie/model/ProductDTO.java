package aforo.rateplanservie.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ProductDTO {

    private Integer productId;

    @NotNull
    @Size(max = 100)
    private String productName;

    private String productDescription;

    @NotNull
    @Size(max = 255)
    private String status;

}
