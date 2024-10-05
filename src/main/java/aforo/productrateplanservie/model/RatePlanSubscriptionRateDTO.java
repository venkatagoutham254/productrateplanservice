package aforo.productrateplanservie.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanSubscriptionRateDTO {

    private Integer ratePlanSubscriptionRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanSubscriptionDescription;

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
    private String unitPriceFixedFrequency;

    @NotNull
    private Integer ratePlan;

}
