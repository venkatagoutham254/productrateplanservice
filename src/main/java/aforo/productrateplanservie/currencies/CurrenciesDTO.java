package aforo.productrateplanservie.currencies;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
    
    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;
    
    @Column(nullable = false)
    private OffsetDateTime lastUpdated;
    

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
	
	public OffsetDateTime getDateCreated() {
	    return dateCreated;
	}

	public void setDateCreated(OffsetDateTime dateCreated) {
	    this.dateCreated = dateCreated;
	}

	public OffsetDateTime getLastUpdated() {
	    return lastUpdated;
	}

	public void setLastUpdated(OffsetDateTime lastUpdated) {
	    this.lastUpdated = lastUpdated;
	}

	public CurrenciesDTO(Long currencyId, @NotNull @Size(max = 3) String currencyCode,
			@NotNull @Size(max = 50) String currencyName, @NotNull Boolean isActive, OffsetDateTime dateCreated,OffsetDateTime lastUpdated ) {
		super();
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.currencyName = currencyName;
		this.isActive = isActive;
		this.dateCreated = dateCreated;
		this.lastUpdated = lastUpdated;
	}

	public CurrenciesDTO() {
		// TODO Auto-generated constructor stub
	}
    

}
