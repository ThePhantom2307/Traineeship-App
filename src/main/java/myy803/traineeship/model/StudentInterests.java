package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="student_interests")
public class StudentInterests {
	@Id
	@ManyToOne
	@JoinColumn(name="student_id", referencedColumnName="student_id")
	private Student student;
	
	@Id
	@ManyToOne
	@JoinColumn(name="interest_id", referencedColumnName="interest_id")
	private Interest interest;
	
	public StudentInterests () {}
	
	public StudentInterests (Student student, Interest interest) {
		this.interest = interest;
		this.student = student;
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public Interest getInterest() {
		return this.interest;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void setInterest(Interest interest) {
		this.interest = interest;
	}
}
