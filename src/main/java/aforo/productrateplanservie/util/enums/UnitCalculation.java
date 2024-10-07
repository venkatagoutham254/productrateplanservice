package aforo.productrateplanservie.util.enums;

public enum UnitCalculation {

	MONTHLY("MONTHLY"),
	DAILY("DAILY"),
	HOURLY("HOURLY"),
	YEARLY("YEARLY"),
	TWICE_A_MONTH("TWICE_A_MONTH"),
	QUARTERLY("QUARTERLY");

	private final String value;
	UnitCalculation(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
