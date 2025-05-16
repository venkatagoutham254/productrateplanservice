package aforo.productrateplanservice.currencies;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
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
