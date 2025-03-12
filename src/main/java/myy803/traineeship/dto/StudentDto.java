package myy803.traineeship.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;

@Component
public class StudentDto {
	private Integer userId;
	private String fullname;
	private String universityId;
	private String interests;
	private String skills;
	private String preferredLocation;
	
	public StudentDto(Integer userId, String fullname, String universityId,
			String interests, String skills, String preferredLocation) {
		super();
		this.userId = userId;
		this.fullname = fullname;
		this.universityId = universityId;
		this.interests = interests;
		this.skills = skills;
		this.preferredLocation = preferredLocation;
	}
	
	public StudentDto(Integer userId, String fullname, String universityId,
			List<Interest> interests, List<Skill> skills, String preferredLocation) {
		super();	
		this.userId = userId;
		this.fullname = fullname;
		this.universityId = universityId;
		this.setInterests(interests);
		this.setSkills(skills);
		this.preferredLocation = preferredLocation;
	}
	
	public StudentDto() {}

	public Integer getUserId() {
		return this.userId;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getUniversityId() {
		return this.universityId;
	}
	
	public List<Interest> getInterestsList() {
		String[] studentInterests = this.interests.split(", ");
		List<Interest> studentInterestsList = new ArrayList<Interest>();
	    for (String s: studentInterests) {
	    	Interest interest = new Interest(s);
	    	studentInterestsList.add(interest);
	    }
		return studentInterestsList;
	}
	
	public String getInterests() {
		return this.interests;
	}
	
	public List<Skill> getSkillsList() {
		String[] studentSkills = this.skills.split(", ");
		List<Skill> studentSkillsList = new ArrayList<Skill>();
	    for (String s: studentSkills) {
	    	Skill skill = new Skill(s);
	    	studentSkillsList.add(skill);
	    }
		return studentSkillsList;
	}
	
	public String getSkills() {
		return this.skills;
	}
	
	public String getPreferredLocation() {
		return this.preferredLocation;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	
	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	public void setInterests(List<Interest> interests) {
		this.interests = interests.stream().map(Interest::getName).collect(Collectors.joining(", "));
	}
	
	public void setSkills(String skills) {
		this.skills = skills;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills.stream().map(Skill::getName).collect(Collectors.joining(", "));
	}
	
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	@Override
	public String toString() {
		return "UserProfileDto: [userID=" + userId + ", fullname="+ fullname +
				", universityID="+ universityId + ", skills=(" + this.skills +
				"), interests=(" + this.interests + "), preferredLocation=" + preferredLocation + "]";
	}
}
