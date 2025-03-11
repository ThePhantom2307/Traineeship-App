package myy803.traineeship.dto;

import org.springframework.stereotype.Component;

@Component
public class StudentDto {
	private Integer userID;
	private String fullname;
	private String universityID;
	private String interests;
	private String skills;
	private String preferredLocation;
	
	public StudentDto(Integer userID, String fullname, String universityID,
			String interests, String skills, String preferredLocation) {
		super();	
		this.userID = userID;
		this.fullname = fullname;
		this.universityID = universityID;
		this.interests = interests;
		this.skills = skills;
		this.preferredLocation = preferredLocation;
	}
	
	public StudentDto() {}

	public Integer getUserID() {
		return this.userID;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getUniversityID() {
		return this.universityID;
	}
	
	public String getInterests() {
		return this.interests;
	}
	
	public String getSkills() {
		return this.skills;
	}
	
	public String getPreferredLocation() {
		return this.preferredLocation;
	}
	
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setUniversityID(String universityID) {
		this.universityID = universityID;
	}
	
	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	@Override
	public String toString() {
		return "UserProfileDto: [userID=" + userID + ", fullname="+ fullname +
				", universityID="+ universityID + ", skills=(" + this.skills +
				"), interests=(" + this.interests + "), preferredLocation=" + preferredLocation + "]";
	}
}
