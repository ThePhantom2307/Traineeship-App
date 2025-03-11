package myy803.traineeship.model;

public enum Role {
	STUDENT("Student"),
	PROFESSOR("Professor"),
	COMPANY("Company"),
	TRAINEESHIP_COMMITEE_MEMBER("Traineeship commitee member");
	
	private final String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
