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
    private Integer producerId;

    @NotNull
    private Integer organizationId;

    private Integer divisionId;

    @NotNull
    @Size(max = 100)
    private String productName;

    @NotNull
    private String productDescription;

    @NotNull
    @Size(max = 255)
    private String status;

}
