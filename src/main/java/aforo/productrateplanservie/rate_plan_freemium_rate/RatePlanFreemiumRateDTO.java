package aforo.productrateplanservie.rate_plan_freemium_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatePlanFreemiumRateDTO {

    private Long ratePlanFreemiumRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanFreemiumDescription;

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
    private String unitFreePriceFixedFrequency;

    @NotNull
    private Long ratePlan;

	public Long getRatePlanFreemiumRateId() {
		return ratePlanFreemiumRateId;
	}

	public void setRatePlanFreemiumRateId(Long ratePlanFreemiumRateId) {
		this.ratePlanFreemiumRateId = ratePlanFreemiumRateId;
	}

	public String getRatePlanFreemiumDescription() {
		return ratePlanFreemiumDescription;
	}

	public void setRatePlanFreemiumDescription(String ratePlanFreemiumDescription) {
		this.ratePlanFreemiumDescription = ratePlanFreemiumDescription;
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

	public String getUnitFreePriceFixedFrequency() {
		return unitFreePriceFixedFrequency;
	}

	public void setUnitFreePriceFixedFrequency(String unitFreePriceFixedFrequency) {
		this.unitFreePriceFixedFrequency = unitFreePriceFixedFrequency;
	}

	public Long getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(Long ratePlan) {
		this.ratePlan = ratePlan;
	}

	public RatePlanFreemiumRateDTO(Long ratePlanFreemiumRateId,
			@NotNull @Size(max = 100) String ratePlanFreemiumDescription, String description,
			@NotNull @Size(max = 255) String unitType, @NotNull @Size(max = 255) String unitMeasurement,
			@NotNull @Size(max = 255) String unitBillingFrequency,
			@NotNull @Size(max = 255) String unitFreePriceFixedFrequency, @NotNull Long ratePlan) {
		super();
		this.ratePlanFreemiumRateId = ratePlanFreemiumRateId;
		this.ratePlanFreemiumDescription = ratePlanFreemiumDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitBillingFrequency = unitBillingFrequency;
		this.unitFreePriceFixedFrequency = unitFreePriceFixedFrequency;
		this.ratePlan = ratePlan;
	}

	public RatePlanFreemiumRateDTO() {
		// TODO Auto-generated constructor stub
	}

    
}
