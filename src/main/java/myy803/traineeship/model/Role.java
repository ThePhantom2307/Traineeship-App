package myy803.traineeship.model;

public enum Role {
	STUDENT("Student"),
	PROFESSOR("Professor"),
	COMPANY("Company"),
	TRAINEESHIP_COMMITTEE_MEMBER("Traineeship committee member");
	
	private final String value;
	
	private Role(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
