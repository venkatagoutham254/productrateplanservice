package aforo.productrateplanservice.util.enums;

public enum FlatRateUnitCalculation {
	MONTHLY("MONTHLY"), 
	DAILY("DAILY"),
	HOURLY("HOURLY"), 
	YEARLY("YEARLY"),
	TWICE_A_MONTH("TWICE_A_MONTH");
	
	
	private final String value;
	FlatRateUnitCalculation(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
