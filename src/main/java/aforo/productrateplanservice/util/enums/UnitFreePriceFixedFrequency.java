package aforo.productrateplanservice.util.enums;


public enum UnitFreePriceFixedFrequency {
	MONTHLY("MONTHLY"),
	DAILY("DAILY"),
	HOURLY("HOURLY"),
	YEARLY("YEARLY"),
	TWICE_A_MONTH("TWICE_A_MONTH"),
	QUARTERLY("QUARTERLY");
	
	private final String value;
	UnitFreePriceFixedFrequency(String value) {
		this.value = value;
	}

}
