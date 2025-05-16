package aforo.productrateplanservice.rate_plan;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

import aforo.productrateplanservice.util.enums.RatePlanType;
import aforo.productrateplanservice.util.enums.Status;

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
