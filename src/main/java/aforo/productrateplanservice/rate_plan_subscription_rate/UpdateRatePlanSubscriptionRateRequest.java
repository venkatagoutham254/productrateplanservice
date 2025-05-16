package aforo.productrateplanservice.rate_plan_subscription_rate;

import lombok.Data;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_subscription_rate_details.UpdateRatePlanSubscriptionRateDetailsRequest;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservice.util.enums.UnitType;
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
