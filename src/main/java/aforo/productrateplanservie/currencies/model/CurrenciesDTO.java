package aforo.productrateplanservie.currencies.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CurrenciesDTO {

    private UUID currencyId;

    @NotNull
    @Size(max = 3)
    private String currencyCode;

    @NotNull
    @Size(max = 50)
    private String currencyName;

    @NotNull
    @JsonProperty("isActive")
    private Boolean isActive;

}
