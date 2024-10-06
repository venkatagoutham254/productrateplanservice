package aforo.productrateplanservie.rate_plan;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class RatePlanDTO {

    private Long ratePlanId;

    @NotNull
    @Size(max = 100)
    private String ratePlanName;

    private String description;

    @NotNull
    @Size(max = 255)
    private String ratePlanType;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @Size(max = 255)
    private String status;

    @NotNull
    private Long product;

    @NotNull
    private Long currency;

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

	public String getRatePlanType() {
		return ratePlanType;
	}

	public void setRatePlanType(String ratePlanType) {
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getProduct() {
		return product;
	}

	public void setProduct(Long product) {
		this.product = product;
	}

	public Long getCurrency() {
		return currency;
	}

	public void setCurrency(Long currency) {
		this.currency = currency;
	}

	public RatePlanDTO(Long ratePlanId, @NotNull @Size(max = 100) String ratePlanName, String description,
			@NotNull @Size(max = 255) String ratePlanType, @NotNull LocalDate startDate, @NotNull LocalDate endDate,
			@NotNull @Size(max = 255) String status, @NotNull Long product, @NotNull Long currency) {
		super();
		this.ratePlanId = ratePlanId;
		this.ratePlanName = ratePlanName;
		this.description = description;
		this.ratePlanType = ratePlanType;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
		this.product = product;
		this.currency = currency;
	}

	public RatePlanDTO() {
		// TODO Auto-generated constructor stub
	}
    
	

}
