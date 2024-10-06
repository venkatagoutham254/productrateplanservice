package aforo.productrateplanservie.rate_plan.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanDTO {

    private UUID ratePlanId;

    @NotNull
    @Size(max = 100)
    private String ratePlanName;

    private String description;

    @NotNull
    @Size(max = 255)
    private String ratePlanType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(max = 255)
    private String status;

    @NotNull
    private UUID product;

    @NotNull
    private UUID currency;

}
