package myy803.traineeship.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {	
	@Id
	@Column(name="student_id")
	private Integer studentId;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="student_id", referencedColumnName="user_id")
	private User user;
	
	@Column(name="fullname")
	private String fullname;
	
	@Column(name="university_id")
	private String universityId;
	
	@Column(name="preferred_location")
	private String preferredLocation;
	
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinTable(
			name="student_interests",
			joinColumns = @JoinColumn(name="student_id", referencedColumnName="student_id"),
			inverseJoinColumns = @JoinColumn(name="interest_id", referencedColumnName="interest_id")
			)
	private List<Interest> interests;
	
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinTable(
			name="student_skills",
			joinColumns = @JoinColumn(name="student_id", referencedColumnName="student_id"),
			inverseJoinColumns = @JoinColumn(name="skill_id", referencedColumnName="skill_id")
			)
	private List<Skill> skills;
	
	public Student() {}
	
	public Student(Integer studentId, User user, String fullname, String universityId, String preferredLocation) {
		this.studentId = studentId;
		this.user = user;
		this.fullname = fullname;
		this.universityId = universityId;
		this.preferredLocation = preferredLocation;
	}
	
	public Integer getStudentId() {
		return this.studentId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getUniversityId() {
		return this.universityId;
	}
	
	public String getPreferredLocation() {
		return this.preferredLocation;
	}
	
	public List<Interest> getInterests() {
		return this.interests;
	}
	
	public List<Skill> getSkills() {
		return this.skills;
	}
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}
	
	public void setPreferredLocation(String preferredLocation) {
		this.preferredLocation = preferredLocation;
	}
	
	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}
	
	public void setSkills(List<Skill> skills) {
		this.skills = skills;
	}
}
