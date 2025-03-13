package myy803.traineeship.model;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name="professors")
public class Professor {
	@Id
	@Column(name="professor_id")
	private Integer professorId;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="professor_id", referencedColumnName="user_id")
	private User user;
	
	@Column(name="fullname")
	private String fullname;
	
	@ManyToMany(fetch = FetchType.EAGER, 
			cascade = {CascadeType.PERSIST,CascadeType.MERGE,CascadeType.REFRESH,CascadeType.REMOVE})
	@JoinTable(
			name="professor_interests",
			joinColumns = @JoinColumn(name="professor_id", referencedColumnName="professor_id"),
			inverseJoinColumns = @JoinColumn(name="interest_id", referencedColumnName="interest_id")
			)
	private List<Interest> interests;
	
	public Professor() {}
	
	public Professor(Integer professorId, User user, String fullname) {
		this.professorId = professorId;
		this.user = user;
		this.fullname = fullname;
	}
	
	public Integer getProfessorId() {
		return this.professorId;
	}
	
	public User getUser() {
		return this.user;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public List<Interest> getInterests() {
		return this.interests;
	}
	
	public void setProfessorId(Integer professorId) {
		this.professorId = professorId;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setInterests(List<Interest> interests) {
		this.interests = interests;
	}
}
