package aforo.productrateplanservice.util.enums;

public enum MaxLimitFrequency {
	DAILY("DAILY"),
	MONTHLY("MONTHLY"),
	YEARLY("YEARLY"),
	QUARTERLY("QUARTERLY");
	
	private final String value;
	MaxLimitFrequency(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
