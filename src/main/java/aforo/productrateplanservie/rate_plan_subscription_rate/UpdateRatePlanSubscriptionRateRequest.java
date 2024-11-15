package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitType;
import lombok.Data;

import java.util.Set;
@Data
public class UpdateRatePlanSubscriptionRateRequest {
    private String ratePlanSubscriptionDescription;
    private String description;
    private UnitType unitType;
    private UnitMeasurement unitMeasurement;
    private UnitBillingFrequency unitBillingFrequency;
    private UnitPriceFixedFrequency unitPriceFixedFrequency;
    private Set<UpdateRatePlanSubscriptionRateDetailsRequest> ratePlanSubscriptionRateDetails;
}
