package aforo.productrateplanservie.rate_plan_tiered_rate;

import aforo.productrateplanservie.rate_plan_subscription_rate_details.RatePlanSubscriptionRateDetailsDTO;
import aforo.productrateplanservie.rate_plan_tiered_rate_details.RatePlanTieredRateDetailsDTO;
import aforo.productrateplanservie.util.enums.UnitCalculation;
import aforo.productrateplanservie.util.enums.UnitMeasurement;
import aforo.productrateplanservie.util.enums.UnitType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;


import java.util.List;
import java.util.Set;

@Data
public class RatePlanTieredRateDTO {

    private Long ratePlanTieredRateId;


    @NotNull
    @Size(max = 100)
    private String ratePlanTieredDescription;

	@NotNull
    private String description;

    private UnitType unitType;
    private UnitMeasurement unitMeasurement;
    private UnitCalculation unitCalculation;
    private Long ratePlanId;

	@JsonProperty("ratePlanTieredRateDetails")
	private Set<RatePlanTieredRateDetailsDTO> ratePlanTieredRateDetailsDTO;

	public RatePlanTieredRateDTO(Long ratePlanTieredRateId, @NotNull @Size(max = 100) String ratePlanTieredDescription,
								 String description,  UnitType unitType,
								  UnitMeasurement unitMeasurement, UnitCalculation unitCalculation,
								  Long ratePlanId, Set<RatePlanTieredRateDetailsDTO> ratePlanTieredRateDetailsDTO){
		super();
		this.ratePlanTieredRateId = ratePlanTieredRateId;
		this.ratePlanTieredDescription = ratePlanTieredDescription;
		this.description = description;
		this.unitType = unitType;
		this.unitMeasurement = unitMeasurement;
		this.unitCalculation = unitCalculation;
		this.ratePlanId = ratePlanId;
		this.ratePlanTieredRateDetailsDTO = ratePlanTieredRateDetailsDTO;
	}

	public RatePlanTieredRateDTO() {
		// TODO Auto-generated constructor stub
	}



}
