package aforo.productrateplanservie.rate_plan_usage_based;

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
    private String unitType;

    @NotNull
    @Size(max = 255)
    private String unitMeasurement;

    @NotNull
    @Size(max = 255)
    private String unitCalculation;

    @NotNull
    private Long ratePlan;

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

	public RatePlanUsageBasedDTO(Long ratePlanUsageRateId, @NotNull @Size(max = 100) String ratePlanUsageDescription,
			String description, @NotNull @Size(max = 255) String unitType,
			@NotNull @Size(max = 255) String unitMeasurement, @NotNull @Size(max = 255) String unitCalculation,
			@NotNull Long ratePlan) {
		super();
		this.ratePlanUsageRateId = ratePlanUsageRateId;
		this.ratePlanUsageDescription = ratePlanUsageDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlan = ratePlan;
	}

	public RatePlanUsageBasedDTO() {
		// TODO Auto-generated constructor stub
	}

    
}
