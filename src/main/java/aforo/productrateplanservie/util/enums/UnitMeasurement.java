package aforo.productrateplanservie.util.enums;

public enum UnitMeasurement {
	GB("GB"),
	MB("MB"), 
	TB("TB"),
	CPU("CPU"),
	NO_OF_API_CALLS("NO_OF_API_CALLS");

	private final String value;
	
	UnitMeasurement(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}

}
