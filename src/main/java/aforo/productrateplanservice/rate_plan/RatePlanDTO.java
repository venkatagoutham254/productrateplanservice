package aforo.productrateplanservice.rate_plan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import aforo.productrateplanservice.util.enums.RatePlanType;
import aforo.productrateplanservice.util.enums.Status;
import lombok.Data;

@Data
public class RatePlanDTO {
    private Long ratePlanId;

    @NotNull
    @Size(max = 100)
    private String ratePlanName;

    private String description;

    @NotNull
    private RatePlanType ratePlanType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;
    
    @NotNull
    private Status status;

    @NotNull
    private Long productId;

    @NotNull
    private Long currencyId;

	public RatePlanDTO(Long ratePlanId, @NotNull @Size(max = 100) String ratePlanName, String description,
			@NotNull RatePlanType ratePlanType, @NotNull LocalDate startDate, @NotNull LocalDate endDate,
			@NotNull Status status, @NotNull Long productId, @NotNull Long currencyId) {
		super();
		this.ratePlanId = ratePlanId;
		this.ratePlanName = ratePlanName;
		this.description = description;
		this.ratePlanType = ratePlanType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.productId = productId;
		this.currencyId = currencyId;
	}

	public RatePlanDTO() {
		// TODO Auto-generated constructor stub
	}
}
