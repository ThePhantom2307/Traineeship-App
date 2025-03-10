package myy803.traineeship.dto;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;
import myy803.traineeship.model.Student;

@Component
public class StudentDto {
	private int userID;
	private String fullname;
	private String universityID;
	private Set<Interest> interests;
	private Set<Skill> skills;
	private String preferredLocation;
	
	public StudentDto(int userID, String fullname, String universityID,
			Set<Interest> interests, Set<Skill> skills, String preferredLocation) {
		super();	
		this.userID = userID;
		this.fullname = fullname;
		this.universityID = universityID;
		this.interests = interests;
		this.skills = skills;
		this.preferredLocation = preferredLocation;
	}
	
	public StudentDto() {}

	public int getUserID() {
		return this.userID;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getUniversityID() {
		return this.universityID;
	}
	
	public Set<Interest> getInterests() {
		return this.interests;
	}
	
	public Set<Skill> getSkills() {
		return this.skills;
	}
	
	public String getPreferredLocation() {
		return this.preferredLocation;
	}
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setUniversityID(String universityID) {
		this.universityID = universityID;
	}
	
	public void setInterests(Set<Interest> interests) {
		this.interests = interests;
	}
	
	public void setSkills(Set<Skill> skills) {
		this.skills = skills;
	}
	
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	public void createStudentProfile(Student student) {
		
	}
	
	@Override
	public String toString() {
		String interestsString = this.interests.stream().map(Interest::getName).collect(Collectors.joining(", "));
		String skillsString = this.skills.stream().map(Skill::getName).collect(Collectors.joining(", "));
		return "UserProfileDto: [userID=" + userID + ", fullname="+ fullname +
				", universityID="+ universityID + ", skills=(" + skillsString +
				"), interests=(" + interestsString + "), preferredLocation=" + preferredLocation + "]";
	}
}
