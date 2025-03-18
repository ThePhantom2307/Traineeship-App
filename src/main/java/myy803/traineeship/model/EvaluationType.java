package myy803.traineeship.model;

public enum EvaluationType {
	COMPANY("Company"),
	SUPERVISOR("Supervisor");

	private final String value;
	
	private EvaluationType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
}
