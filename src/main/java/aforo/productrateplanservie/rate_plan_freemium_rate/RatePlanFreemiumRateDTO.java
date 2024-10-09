package aforo.productrateplanservie.rate_plan_freemium_rate;

import aforo.productrateplanservie.util.enums.UnitBillingFrequency;
import aforo.productrateplanservie.util.enums.UnitFreePriceFixedFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
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
    private UnitType unitType;

    @NotNull
    @Size(max = 255)
    private UnitMeasurement unitMeasurement;

    @NotNull
    @Size(max = 255)
    private UnitBillingFrequency unitBillingFrequency;

    @NotNull
    @Size(max = 255)
    private UnitFreePriceFixedFrequency unitFreePriceFixedFrequency;

    @NotNull
    private Long ratePlanId;

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

	public UnitFreePriceFixedFrequency getUnitFreePriceFixedFrequency() {
		return unitFreePriceFixedFrequency;
	}

	public void setUnitFreePriceFixedFrequency(UnitFreePriceFixedFrequency unitFreePriceFixedFrequency) {
		this.unitFreePriceFixedFrequency = unitFreePriceFixedFrequency;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public RatePlanFreemiumRateDTO(Long ratePlanFreemiumRateId,
			@NotNull @Size(max = 100) String ratePlanFreemiumDescription, String description,
			@NotNull @Size(max = 255) UnitType unitType, @NotNull @Size(max = 255) UnitMeasurement unitMeasurement,
			@NotNull @Size(max = 255) UnitBillingFrequency unitBillingFrequency,
			@NotNull @Size(max = 255) UnitFreePriceFixedFrequency unitFreePriceFixedFrequency, @NotNull Long ratePlanId) {
		super();
		this.ratePlanFreemiumRateId = ratePlanFreemiumRateId;
		this.ratePlanFreemiumDescription = ratePlanFreemiumDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitBillingFrequency = unitBillingFrequency;
		this.unitFreePriceFixedFrequency = unitFreePriceFixedFrequency;
		this.ratePlanId = ratePlanId;
	}

	public RatePlanFreemiumRateDTO() {
		// TODO Auto-generated constructor stub
	}

    
}
