package aforo.productrateplanservie.rate_plan_usage_based;

import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
public class RatePlanUsageBasedDTO {

    private Long ratePlanUsageRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanUsageDescription;

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

	public Long getRatePlanUsageRateId() {
		return ratePlanUsageRateId;
	}

	public void setRatePlanUsageRateId(Long ratePlanUsageRateId) {
		this.ratePlanUsageRateId = ratePlanUsageRateId;
	}

	public String getRatePlanUsageDescription() {
		return ratePlanUsageDescription;
	}

	public void setRatePlanUsageDescription(String ratePlanUsageDescription) {
		this.ratePlanUsageDescription = ratePlanUsageDescription;
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

	public void setRatePlan(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public RatePlanUsageBasedDTO(Long ratePlanUsageRateId, @NotNull @Size(max = 100) String ratePlanUsageDescription,
			String description, @NotNull @Size(max = 255) UnitType unitType,
			@NotNull @Size(max = 255) UnitMeasurement unitMeasurement, @NotNull @Size(max = 255) UnitCalculation unitCalculation,
			@NotNull Long ratePlanId) {
		super();
		this.ratePlanUsageRateId = ratePlanUsageRateId;
		this.ratePlanUsageDescription = ratePlanUsageDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlanId = ratePlanId;
	}

	public RatePlanUsageBasedDTO() {
		// TODO Auto-generated constructor stub
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId=ratePlanId;
		
	}

    
}
