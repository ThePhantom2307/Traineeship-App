package myy803.traineeship.model;

public enum ApplicationStatus {
	PENDING("PENDING"),
	ACCEPTED("ACCEPTED"),
	REJECTED("REJECTED");
	
	private final String value;
	
	ApplicationStatus(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
	
	public static ApplicationStatus fromString(String inputStatus) {
	    if (inputStatus == null || inputStatus.trim().isEmpty()) {
	        throw new IllegalArgumentException("Invalid or empty status");
	    }
	    
	    try {
	        return ApplicationStatus.valueOf(inputStatus.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        throw new IllegalArgumentException("Unknown status: " + inputStatus);
	    }
	}

}
