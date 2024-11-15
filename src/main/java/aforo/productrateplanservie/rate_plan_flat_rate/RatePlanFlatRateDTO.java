package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class RatePlanFlatRateDTO {
    private Long ratePlanFlatRateId;
    @NotNull
    @Size(max = 100)
    private String ratePlanFlatDescription;
    private String description;
    @NotNull
    private UnitType unitType;
    @NotNull
    private UnitMeasurement unitMeasurement;
    @NotNull
    private FlatRateUnitCalculation flatRateUnitCalculation;
    @NotNull
    private MaxLimitFrequency maxLimitFrequency;
    private Long ratePlanId;
    private List<RatePlanFlatRateDetailsDTO> ratePlanFlatRateDetails;

}
