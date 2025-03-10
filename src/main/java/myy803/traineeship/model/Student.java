package myy803.traineeship.model;

import java.util.Set;

import jakarta.persistence.*;
import myy803.traineeship.dto.StudentDto;

@Entity
@Table(name="Students")
public class Student {
	@Id
	@Column(name="student_id")
	private int studentID;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="student_id")
	private User user;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="university_id")
	private String universityID;
	
	@Column(name="preferred_location")
	private String preferredLocation;
	
	@ManyToMany
	@JoinTable(
			name="StudentInterests",
			joinColumns=@JoinColumn(name="student_id"),
			inverseJoinColumns=@JoinColumn(name="interest_id")
			)
	private Set<Interest> interests;
	
	@ManyToMany
	@JoinTable(
			name="StudentSkills",
			joinColumns=@JoinColumn(name="student_id"),
			inverseJoinColumns=@JoinColumn(name="skill_id")
			)
	private Set<Skill> skills;
	
	public int getStudentID() {
		return this.studentID;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getUniversityID() {
		return this.universityID;
	}
	
	public String getPreferredLocation() {
		return this.preferredLocation;
	}
	
	public Set<Interest> getInterests() {
        return interests;
    }

    public Set<Skill> getSkills() {
        return this.skills;
    }
	
	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setUniversityID(String universityID) {
		this.universityID = universityID;
	}
	
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}

    public void setInterests(Set<Interest> interests) {
        this.interests = interests;
    }

    public void setSkills(Set<Skill> skills) {
        this.skills = skills;
    }
    
    public StudentDto createStudentDto() {
    	StudentDto studentDto = new StudentDto(this.studentID, this.fullname, this.universityID, this.interests, this.skills, this.preferredLocation);
    	return studentDto;
    }
}
