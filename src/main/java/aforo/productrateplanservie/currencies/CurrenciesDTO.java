package aforo.productrateplanservie.currencies;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CurrenciesDTO {

    private Long currencyId;

    @NotNull
    @Size(max = 3)
    private String currencyCode;

    @NotNull
    @Size(max = 50)
    private String currencyName;

    @NotNull
    @JsonProperty("isActive")
    private Boolean isActive;

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public CurrenciesDTO(Long currencyId, @NotNull @Size(max = 3) String currencyCode,
			@NotNull @Size(max = 50) String currencyName, @NotNull Boolean isActive) {
		super();
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.isActive = isActive;
	}

	public CurrenciesDTO() {
		// TODO Auto-generated constructor stub
	}
    

}
