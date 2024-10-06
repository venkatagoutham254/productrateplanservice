package aforo.productrateplanservie.rate_plan_flat_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanFlatRateDTO {

    private Long ratePlanFlatRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanFlatDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String flatRateUnitCalculation;

    @NotNull
    @Size(max = 255)
    private String maxLimitFrequency;

    @NotNull
    private Long ratePlan;

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

	public String getFlatRateUnitCalculation() {
		return flatRateUnitCalculation;
	}

	public void setFlatRateUnitCalculation(String flatRateUnitCalculation) {
		this.flatRateUnitCalculation = flatRateUnitCalculation;
	}

	public String getMaxLimitFrequency() {
		return maxLimitFrequency;
	}

	public void setMaxLimitFrequency(String maxLimitFrequency) {
		this.maxLimitFrequency = maxLimitFrequency;
	}

	public Long getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(Long ratePlan) {
		this.ratePlan = ratePlan;
	}

	public RatePlanFlatRateDTO(Long ratePlanFlatRateId, @NotNull @Size(max = 100) String ratePlanFlatDescription,
			String description, @NotNull @Size(max = 255) String unitType,
			@NotNull @Size(max = 255) String unitMeasurement, @NotNull @Size(max = 255) String flatRateUnitCalculation,
			@NotNull @Size(max = 255) String maxLimitFrequency, @NotNull Long ratePlan) {
		super();
		this.ratePlanFlatRateId = ratePlanFlatRateId;
		this.ratePlanFlatDescription = ratePlanFlatDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.flatRateUnitCalculation = flatRateUnitCalculation;
		this.maxLimitFrequency = maxLimitFrequency;
		this.ratePlan = ratePlan;
	}

	public RatePlanFlatRateDTO() {
		// TODO Auto-generated constructor stub
	}

	public void setRatePlan(Object ratePlan2) {
		// TODO Auto-generated method stub
		
	}
    

}
