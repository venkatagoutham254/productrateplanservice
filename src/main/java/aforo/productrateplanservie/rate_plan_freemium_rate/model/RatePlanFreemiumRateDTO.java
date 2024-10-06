package aforo.productrateplanservie.rate_plan_freemium_rate.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanFreemiumRateDTO {

    private Long ratePlanFreemiumRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanFreemiumDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String unitBillingFrequency;

    @NotNull
    @Size(max = 255)
    private String unitFreePriceFixedFrequency;

    @NotNull
    private Long ratePlan;

}
