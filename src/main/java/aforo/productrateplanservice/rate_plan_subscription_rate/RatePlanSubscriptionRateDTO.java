package aforo.productrateplanservice.rate_plan_subscription_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetails;
import aforo.productrateplanservice.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservice.util.enums.UnitBillingFrequency;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservice.util.enums.UnitType;
@Data
public class RatePlanSubscriptionRateDTO {

    private Long ratePlanSubscriptionRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanSubscriptionDescription;

    private String description;

    private UnitType unitType;

    private UnitMeasurement unitMeasurement;

    private UnitBillingFrequency unitBillingFrequency;

    private UnitPriceFixedFrequency unitPriceFixedFrequency;

    private Long ratePlanId;

	private Set<RatePlanSubscriptionRateDetailsDTO> ratePlanSubscriptionRateDetailsDTO;

    public RatePlanSubscriptionRateDTO(Long ratePlanSubscriptionRateId,
			@NotNull @Size(max = 100) String ratePlanSubscriptionDescription, String description,
			@NotNull  UnitType unitType, @NotNull  UnitMeasurement unitMeasurement,
			@NotNull  UnitBillingFrequency unitBillingFrequency,
			@NotNull UnitPriceFixedFrequency unitPriceFixedFrequency, @NotNull Long ratePlanId,
			@NotNull Set<RatePlanSubscriptionRateDetailsDTO> ratePlanSubscriptionRateDetailsDTO) {
		super();
		this.ratePlanSubscriptionRateId = ratePlanSubscriptionRateId;
		this.ratePlanSubscriptionDescription = ratePlanSubscriptionDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitBillingFrequency = unitBillingFrequency;
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
		this.ratePlanId = ratePlanId;
		this.ratePlanSubscriptionRateDetailsDTO = ratePlanSubscriptionRateDetailsDTO;
	}

	public RatePlanSubscriptionRateDTO() {
		// TODO Auto-generated constructor stub
	}

}
