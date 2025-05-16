package aforo.productrateplanservice.currencies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCurrenciesRequest {
    @NotNull(message = "Currency code cannot be null")
    @Size(max = 3, message = "Currency code must not exceed 3 characters")
    private String currencyCode;

    @NotNull(message = "Currency name cannot be null")
    @Size(max = 50, message = "Currency name must not exceed 50 characters")
    private String currencyName;

    @NotNull(message = "Active status cannot be null")
    @JsonProperty("isActive")
    private Boolean isActive;
}