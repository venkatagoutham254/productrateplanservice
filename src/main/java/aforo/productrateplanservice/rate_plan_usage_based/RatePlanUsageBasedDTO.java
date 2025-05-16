package aforo.productrateplanservice.rate_plan_usage_based;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

import aforo.productrateplanservice.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservice.rate_plan_usage_based_rates.RatePlanUsageBasedRatesDTO;
import aforo.productrateplanservice.util.enums.UnitCalculation;
import aforo.productrateplanservice.util.enums.UnitMeasurement;
import aforo.productrateplanservice.util.enums.UnitType;


@Data
public class RatePlanUsageBasedDTO {

    private Long ratePlanUsageRateId;

    @NotNull
    @Size(max = 100)
    private String ratePlanUsageDescription;

	@NotNull
    private String description;
    private UnitType unitType;
    private UnitMeasurement unitMeasurement;
    private UnitCalculation unitCalculation;
    private Long ratePlanId;

	private Set<RatePlanUsageBasedRatesDTO> ratePlanUsageBasedRatesDTO;

	public RatePlanUsageBasedDTO(Long ratePlanUsageRateId,
			@NotNull @Size(max = 100) String ratePlanUsageDescription,
			String description,  UnitType unitType,
			UnitMeasurement unitMeasurement,   UnitCalculation unitCalculation,
			 Long ratePlanId, Set<RatePlanUsageBasedRatesDTO> ratePlanUsageBasedRatesDTO ) {
		super();
		this.ratePlanUsageRateId = ratePlanUsageRateId;
		this.ratePlanUsageDescription = ratePlanUsageDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlanId = ratePlanId;
		this.ratePlanUsageBasedRatesDTO = ratePlanUsageBasedRatesDTO;
	}

	public RatePlanUsageBasedDTO() {
		// TODO Auto-generated constructor stub
	}

}
