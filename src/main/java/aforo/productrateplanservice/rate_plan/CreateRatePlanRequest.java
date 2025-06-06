package aforo.productrateplanservice.rate_plan;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import aforo.productrateplanservice.product.enums.RatePlanType;
import aforo.productrateplanservice.product.enums.RatePlanStatus;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRatePlanRequest {

    @NotNull
    private Long productId;

    @NotBlank
    private String productName;

    @NotBlank
    private String ratePlanName;

    private String description;

    @NotNull
    private RatePlanType ratePlanType;

    @NotNull
    private RatePlanStatus status;
}
