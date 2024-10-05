package aforo.productrateplanservie.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanFlatRateDTO {

    private Integer ratePlanFlatRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanFlatDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String flatRateUnitCalculation;

    @NotNull
    @Size(max = 255)
    private String maxLimitFrequency;

    @NotNull
    private Integer ratePlan;

}
