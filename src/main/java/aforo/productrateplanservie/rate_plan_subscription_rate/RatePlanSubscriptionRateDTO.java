package aforo.productrateplanservie.rate_plan_subscription_rate;

import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitPriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatePlanSubscriptionRateDTO {
    private Long ratePlanSubscriptionRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanSubscriptionDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private UnitType unitType;

    @NotNull
    @Size(max = 255)
    private UnitMeasurement unitMeasurement;

    @NotNull
    @Size(max = 255)
    private UnitBillingFrequency unitBillingFrequency;

    @NotNull
    @Size(max = 255)
    private UnitPriceFixedFrequency unitPriceFixedFrequency;

    @NotNull
    private Long ratePlanId;

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

	public UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(UnitType unitType) {
		this.unitType = unitType;
	}

	public UnitMeasurement getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(UnitMeasurement unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public UnitBillingFrequency getUnitBillingFrequency() {
		return unitBillingFrequency;
	}

	public void setUnitBillingFrequency(UnitBillingFrequency unitBillingFrequency) {
		this.unitBillingFrequency = unitBillingFrequency;
	}

	public UnitPriceFixedFrequency getUnitPriceFixedFrequency() {
		return unitPriceFixedFrequency;
	}

	public void setUnitPriceFixedFrequency(UnitPriceFixedFrequency unitPriceFixedFrequency) {
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public RatePlanSubscriptionRateDTO(Long ratePlanSubscriptionRateId,
			@NotNull @Size(max = 100) String ratePlanSubscriptionDescription, String description,
			@NotNull @Size(max = 255) UnitType unitType, @NotNull @Size(max = 255) UnitMeasurement unitMeasurement,
			@NotNull @Size(max = 255) UnitBillingFrequency unitBillingFrequency,
			@NotNull @Size(max = 255) UnitPriceFixedFrequency unitPriceFixedFrequency, @NotNull Long ratePlan) {
		super();
		this.ratePlanSubscriptionRateId = ratePlanSubscriptionRateId;
		this.ratePlanSubscriptionDescription = ratePlanSubscriptionDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitBillingFrequency = unitBillingFrequency;
		this.unitPriceFixedFrequency = unitPriceFixedFrequency;
		this.ratePlanId = ratePlanId;
	}

	public RatePlanSubscriptionRateDTO() {
		// TODO Auto-generated constructor stub
	}

}
