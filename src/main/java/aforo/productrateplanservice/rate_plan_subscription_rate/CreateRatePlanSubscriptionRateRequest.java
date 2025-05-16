package aforo.productrateplanservice.rate_plan_subscription_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

import aforo.productrateplanservice.rate_plan_subscription_rate_details.CreateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservice.util.enums.UnitType;
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
