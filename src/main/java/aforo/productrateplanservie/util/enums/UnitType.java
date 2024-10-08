package aforo.productrateplanservie.util.enums;

public enum UnitType {
	DATA_STORAGE("DATA_STORAGE"), 
	API("API"),
	PROCESSING("PROCESSING"),
	BANDWIDTH("BANDWIDTH");
	
	private final String value;
	
	UnitType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}
