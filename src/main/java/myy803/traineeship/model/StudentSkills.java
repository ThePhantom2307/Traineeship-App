package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="student_skills")
public class StudentSkills {
	@Id
	@ManyToOne
	@JoinColumn(name="student_id", referencedColumnName="student_id")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn(name="skill_id", referencedColumnName="skill_id")
	private Skill skill;
	
	public StudentSkills () {}
	
	public StudentSkills (Student student, Skill skill) {
		this.skill = skill;
		this.student = student;
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public Skill getInterest() {
		return this.skill;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void setInterest(Skill skill) {
		this.skill = skill;
	}
}
