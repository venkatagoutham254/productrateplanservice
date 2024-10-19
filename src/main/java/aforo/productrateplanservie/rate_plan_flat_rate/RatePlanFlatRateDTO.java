package aforo.productrateplanservie.rate_plan_flat_rate;

import aforo.productrateplanservie.rate_plan_flat_rate_details.RatePlanFlatRateDetailsDTO;
import aforo.productrateplanservie.util.enums.FlatRateUnitCalculation;
import aforo.productrateplanservie.util.enums.MaxLimitFrequency;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;
@Setter
@Getter
public class RatePlanFlatRateDTO {

	@Setter
    @Getter
    private Long ratePlanFlatRateId;

	@NotNull
	@Size(max = 100)
	private String ratePlanFlatDescription;

	@Setter
    @Getter
    private String description;

	@NotNull
	private UnitType unitType;

	@NotNull
	private UnitMeasurement unitMeasurement;

	@NotNull
	private FlatRateUnitCalculation flatRateUnitCalculation;

	@NotNull
	private MaxLimitFrequency maxLimitFrequency;

	@Setter
    @Getter
    private Long ratePlanId;

	@Setter
    @Getter
    private List<RatePlanFlatRateDetailsDTO> ratePlanFlatRateDetails;

	// Getters and Setters

    public @NotNull @Size(max = 100) String getRatePlanFlatDescription() {
		return ratePlanFlatDescription;
	}

	public void setRatePlanFlatDescription(@NotNull @Size(max = 100) String ratePlanFlatDescription) {
		this.ratePlanFlatDescription = ratePlanFlatDescription;
	}

    public @NotNull UnitType getUnitType() {
		return unitType;
	}

	public void setUnitType(@NotNull UnitType unitType) {
		this.unitType = unitType;
	}

	public @NotNull UnitMeasurement getUnitMeasurement() {
		return unitMeasurement;
	}

	public void setUnitMeasurement(@NotNull UnitMeasurement unitMeasurement) {
		this.unitMeasurement = unitMeasurement;
	}

	public @NotNull FlatRateUnitCalculation getFlatRateUnitCalculation() {
		return flatRateUnitCalculation;
	}

	public void setFlatRateUnitCalculation(@NotNull FlatRateUnitCalculation flatRateUnitCalculation) {
		this.flatRateUnitCalculation = flatRateUnitCalculation;
	}

	public @NotNull MaxLimitFrequency getMaxLimitFrequency() {
		return maxLimitFrequency;
	}

	public void setMaxLimitFrequency(@NotNull MaxLimitFrequency maxLimitFrequency) {
		this.maxLimitFrequency = maxLimitFrequency;
	}

    // Constructor
	public RatePlanFlatRateDTO() {
	}

	public RatePlanFlatRateDTO(Long ratePlanFlatRateId, String ratePlanFlatDescription, String description,
							   UnitType unitType, UnitMeasurement unitMeasurement,
							   FlatRateUnitCalculation flatRateUnitCalculation,
							   MaxLimitFrequency maxLimitFrequency, Long ratePlanId,
							   List<RatePlanFlatRateDetailsDTO> ratePlanFlatRateDetails) {
		this.ratePlanFlatRateId = ratePlanFlatRateId;
		this.ratePlanFlatDescription = ratePlanFlatDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.flatRateUnitCalculation = flatRateUnitCalculation;
		this.maxLimitFrequency = maxLimitFrequency;
		this.ratePlanId = ratePlanId;
		this.ratePlanFlatRateDetails = ratePlanFlatRateDetails;
	}
}
