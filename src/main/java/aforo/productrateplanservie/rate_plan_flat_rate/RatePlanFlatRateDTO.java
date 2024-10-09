package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatePlanFlatRateDTO {

    private Long ratePlanFlatRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanFlatDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private UnitType unitType;

    @NotNull
    @Size(max = 255)
    private UnitMeasurement unitMeasurement;

    @NotNull
    @Size(max = 255)
    private FlatRateUnitCalculation flatRateUnitCalculation;

    @NotNull
    @Size(max = 255)
    private MaxLimitFrequency maxLimitFrequency;

    @NotNull
    private Long ratePlanId;

	public Long getRatePlanFlatRateId() {
		return ratePlanFlatRateId;
	}

	public void setRatePlanFlatRateId(Long ratePlanFlatRateId) {
		this.ratePlanFlatRateId = ratePlanFlatRateId;
	}

	public String getRatePlanFlatDescription() {
		return ratePlanFlatDescription;
	}

	public void setRatePlanFlatDescription(String ratePlanFlatDescription) {
		this.ratePlanFlatDescription = ratePlanFlatDescription;
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

	public FlatRateUnitCalculation getFlatRateUnitCalculation() {
		return flatRateUnitCalculation;
	}

	public void setFlatRateUnitCalculation(FlatRateUnitCalculation flatRateUnitCalculation) {
		this.flatRateUnitCalculation = flatRateUnitCalculation;
	}

	public MaxLimitFrequency getMaxLimitFrequency() {
		return maxLimitFrequency;
	}

	public void setMaxLimitFrequency(MaxLimitFrequency maxLimitFrequency) {
		this.maxLimitFrequency = maxLimitFrequency;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public RatePlanFlatRateDTO(Long ratePlanFlatRateId, @NotNull @Size(max = 100) String ratePlanFlatDescription,
			String description, @NotNull @Size(max = 255) UnitType unitType,
			@NotNull @Size(max = 255) UnitMeasurement unitMeasurement, @NotNull @Size(max = 255) FlatRateUnitCalculation flatRateUnitCalculation,
			@NotNull @Size(max = 255) MaxLimitFrequency maxLimitFrequency, @NotNull Long ratePlanId) {
		super();
		this.ratePlanFlatRateId = ratePlanFlatRateId;
		this.ratePlanFlatDescription = ratePlanFlatDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.flatRateUnitCalculation = flatRateUnitCalculation;
		this.maxLimitFrequency = maxLimitFrequency;
		this.ratePlanId = ratePlanId;
	}

	public RatePlanFlatRateDTO() {
		// TODO Auto-generated constructor stub
	}

	public void setRatePlan(Object ratePlan2) {
		// TODO Auto-generated method stub
		
	}
    

}
