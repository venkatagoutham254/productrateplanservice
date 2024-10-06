package aforo.productrateplanservie.rate_plan_tiered_rate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanTieredRateDTO {

    private Long ratePlanTieredRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanTieredDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String unitCalculation;

    @NotNull
    private Long ratePlan;

	public RatePlanTieredRateDTO(Long ratePlanTieredRateId, @NotNull @Size(max = 100) String ratePlanTieredDescription,
			String description, @NotNull @Size(max = 255) String unitType,
			@NotNull @Size(max = 255) String unitMeasurement, @NotNull @Size(max = 255) String unitCalculation,
			@NotNull Long ratePlan) {
		super();
		this.ratePlanTieredRateId = ratePlanTieredRateId;
		this.ratePlanTieredDescription = ratePlanTieredDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlan = ratePlan;
	}

	public Long getRatePlanTieredRateId() {
		return ratePlanTieredRateId;
	}

	public void setRatePlanTieredRateId(Long ratePlanTieredRateId) {
		this.ratePlanTieredRateId = ratePlanTieredRateId;
	}

	public String getRatePlanTieredDescription() {
		return ratePlanTieredDescription;
	}

	public void setRatePlanTieredDescription(String ratePlanTieredDescription) {
		this.ratePlanTieredDescription = ratePlanTieredDescription;
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

	public String getUnitCalculation() {
		return unitCalculation;
	}

	public void setUnitCalculation(String unitCalculation) {
		this.unitCalculation = unitCalculation;
	}

	public Long getRatePlan() {
		return ratePlan;
	}

	public void setRatePlan(Long ratePlan) {
		this.ratePlan = ratePlan;
	}
    

}
