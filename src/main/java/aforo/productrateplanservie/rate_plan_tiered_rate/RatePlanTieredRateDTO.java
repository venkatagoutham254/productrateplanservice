package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RatePlanTieredRateDTO {
    private Long ratePlanTieredRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanTieredDescription;

    private String description;

    @NotNull
    @Size(max = 255)
    private UnitType unitType;

    @NotNull
    @Size(max = 255)
    private UnitMeasurement unitMeasurement;

    @NotNull
    @Size(max = 255)
    private UnitCalculation unitCalculation;

    @NotNull
    private Long ratePlanId;

	public RatePlanTieredRateDTO(Long ratePlanTieredRateId, @NotNull @Size(max = 100) String ratePlanTieredDescription,
			String description, @NotNull @Size(max = 255) UnitType unitType,
			@NotNull @Size(max = 255) UnitMeasurement unitMeasurement, @NotNull @Size(max = 255) UnitCalculation unitCalculation,
			@NotNull Long ratePlanId) {
		super();
		this.ratePlanTieredRateId = ratePlanTieredRateId;
		this.ratePlanTieredDescription = ratePlanTieredDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlanId = ratePlanId;
	}

	public RatePlanTieredRateDTO() {
		// TODO Auto-generated constructor stub
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

	public UnitCalculation getUnitCalculation() {
		return unitCalculation;
	}

	public void setUnitCalculation(UnitCalculation unitCalculation) {
		this.unitCalculation = unitCalculation;
	}

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}
    

}
