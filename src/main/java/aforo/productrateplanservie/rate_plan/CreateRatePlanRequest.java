package aforo.productrateplanservie.rate_plan;

import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateRatePlanRequest {
//    @NotNull
    @Size(max = 100)
    private String ratePlanName;

    private String description;

//    @NotNull
    private RatePlanType ratePlanType;

//    @NotNull
    private Status status;

//    @NotNull
    private Long currencyId;

//    @NotNull
    private LocalDateTime startDate;

//    @NotNull
    private LocalDateTime endDate;
}
