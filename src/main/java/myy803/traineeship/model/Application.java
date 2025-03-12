package myy803.traineeship.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name="applications")
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="application_id", unique=true, nullable=false)
	private Integer applicationId;
	
	@OneToOne
	@MapsId
	@JoinColumn(name="student_id", unique=true, referencedColumnName="student_id")
	private Student student;
	
	@Column(name="application_date")
	private LocalDateTime applicationDate;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private ApplicationStatus status;
	
	public Application() {}
	
	public Application(Integer applicationId, Student student, LocalDateTime applicationDate, ApplicationStatus status) {
		this.applicationId = applicationId;
		this.student = student;
		this.applicationDate = applicationDate;
		this.status = status;
	}
	
	public Integer getApplicationId() {
		return this.applicationId;
	}
	
	public Student getStudent() {
		return this.student;
	}
	
	public LocalDateTime getApplicationDate() {
		return this.applicationDate;
	}
	
	public ApplicationStatus getStatus() {
		return this.status;
	}
	
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	public void setStudent(Student student) {
		this.student = student;
	}
	
	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	public void setStatus(ApplicationStatus status) {
		this.status = status;
	}
}
