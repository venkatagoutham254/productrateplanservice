package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRatePlanRequest {
//    @NotNull(message = "Rate plan name is required")
//    @NotEmpty(message = "Rate plan name cannot be empty")
//    @Size(max = 100, message = "Rate plan name cannot exceed 100 characters")
    private String ratePlanName;

    private String description;

//    @NotNull(message = "Rate plan type is required")
//    @NotEmpty(message = "Rate plan type cannot be empty")
    private RatePlanType ratePlanType;

//    @NotNull(message = "Status is required")
//    @NotEmpty(message = "Status cannot be empty")
    private Status status;

//    @NotNull(message = "currencyId is required")
//    @NotEmpty(message = "currencyId cannot be empty")
    private Long currencyId;

//    @NotNull(message = "startDate is required")
//    @NotEmpty(message = "startDate cannot be empty")
    private LocalDateTime startDate;

//    @NotNull(message = "endDate is required")
//    @NotEmpty(message = "endDate cannot be empty")
    private LocalDateTime endDate;
}
