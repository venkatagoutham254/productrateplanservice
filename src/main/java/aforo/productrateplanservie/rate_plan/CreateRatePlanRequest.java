package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRatePlanRequest {
    @NotNull(message = "Rate plan name is required")
    private String ratePlanName;

    private String description;

    @NotNull(message = "Rate plan type is required")
    private RatePlanType ratePlanType;

    @NotNull(message = "Status is required")
    private Status status;

    @NotNull(message = "currencyId is required")
    private Long currencyId;

    @NotNull(message = "startDate is required")
    private LocalDateTime startDate;

    @NotNull(message = "endDate is required")
    private LocalDateTime endDate;
}
