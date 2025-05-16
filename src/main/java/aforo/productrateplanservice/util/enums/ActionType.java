package aforo.productrateplanservice.util.enums;

public enum ActionType {
	LOGIN("LOGIN"),
	LOGOUT("LOGOUT"),
	ACCESS_GRANT("ACCESS_GRANT"),
	ACCESS_REVOKE("ACCESS_REVOKE");

	private final String value;
	ActionType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
