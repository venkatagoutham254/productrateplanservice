package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan_subscription_rate_details.CreateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;
@Data
public class CreateRatePlanSubscriptionRateRequest {
    @NotNull
    @Size(max = 100)
    private String ratePlanSubscriptionDescription;
    private String description;
    @NotNull
    private UnitType unitType;
    @NotNull
    private UnitMeasurement unitMeasurement;
    @NotNull
    private UnitBillingFrequency unitBillingFrequency;
    @NotNull
    private UnitPriceFixedFrequency unitPriceFixedFrequency;
    @NotNull
    private Set<CreateRatePlanSubscriptionRateDetailsRequest> ratePlanSubscriptionRateDetails;
}
