package aforo.productrateplanservie.util.enums;

public enum UserRole {
	ADMINISTRATOR("ADMINISTRATOR"),
	VIEWER("VIEWER");
	
	private final String value;
	
	UserRole(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
