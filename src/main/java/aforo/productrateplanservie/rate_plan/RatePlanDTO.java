package aforo.productrateplanservie.rate_plan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

import aforo.productrateplanservie.util.enums.RatePlanType;
import aforo.productrateplanservie.util.enums.Status;

public class RatePlanDTO {

    private Long ratePlanId;

    @NotNull
    @Size(max = 100)
    private String ratePlanName;

    private String description;

    @NotNull
    @Size(max = 255)
    private RatePlanType ratePlanType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(max = 255)
    private Status status;

    @NotNull
    private Long productId;

    @NotNull
    private Long currencyId;

	public Long getRatePlanId() {
		return ratePlanId;
	}

	public void setRatePlanId(Long ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public String getRatePlanName() {
		return ratePlanName;
	}

	public void setRatePlanName(String ratePlanName) {
		this.ratePlanName = ratePlanName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public RatePlanType getRatePlanType() {
		return ratePlanType;
	}

	public void setRatePlanType(RatePlanType ratePlanType) {
		this.ratePlanType = ratePlanType;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public RatePlanDTO(Long ratePlanId, @NotNull @Size(max = 100) String ratePlanName, String description,
			@NotNull @Size(max = 255) RatePlanType ratePlanType, @NotNull LocalDate startDate, @NotNull LocalDate endDate,
			@NotNull @Size(max = 255) Status status, @NotNull Long productId, @NotNull Long currencyId) {
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
