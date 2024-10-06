package aforo.productrateplanservie.rate_plan_subscription_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanSubscriptionRateDTO {

    private Long ratePlanSubscriptionRateId;

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
    private Long ratePlan;

	public Long getRatePlanSubscriptionRateId() {
		return ratePlanSubscriptionRateId;
	}

	public void setRatePlanSubscriptionRateId(Long ratePlanSubscriptionRateId) {
		this.ratePlanSubscriptionRateId = ratePlanSubscriptionRateId;
	}

	public String getRatePlanSubscriptionDescription() {
		return ratePlanSubscriptionDescription;
	}

	public void setRatePlanSubscriptionDescription(String ratePlanSubscriptionDescription) {
		this.ratePlanSubscriptionDescription = ratePlanSubscriptionDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUnitType() {
		return unitType;
	}

	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}

	public String getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(String unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public String getUnitBillingFrequency() {
		return unitBillingFrequency;
	}

	public void setUnitBillingFrequency(String unitBillingFrequency) {
		this.unitBillingFrequency = unitBillingFrequency;
	}

	public String getUnitPriceFixedFrequency() {
		return unitPriceFixedFrequency;
	}

	public void setUnitPriceFixedFrequency(String unitPriceFixedFrequency) {
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
	}

	public Long getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(Long ratePlan) {
		this.ratePlan = ratePlan;
	}

	public RatePlanSubscriptionRateDTO(Long ratePlanSubscriptionRateId,
			@NotNull @Size(max = 100) String ratePlanSubscriptionDescription, String description,
			@NotNull @Size(max = 255) String unitType, @NotNull @Size(max = 255) String unitMeasurement,
			@NotNull @Size(max = 255) String unitBillingFrequency,
			@NotNull @Size(max = 255) String unitPriceFixedFrequency, @NotNull Long ratePlan) {
		super();
		this.ratePlanSubscriptionRateId = ratePlanSubscriptionRateId;
		this.ratePlanSubscriptionDescription = ratePlanSubscriptionDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitBillingFrequency = unitBillingFrequency;
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
		this.ratePlan = ratePlan;
	}

	public RatePlanSubscriptionRateDTO() {
		// TODO Auto-generated constructor stub
	}

}
