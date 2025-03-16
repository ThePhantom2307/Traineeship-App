package myy803.traineeship.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name="traineeship_positions")
public class TraineeshipPosition {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer positionId;
	
	@Column(name="title")
	private String title;
	
	@Column(name="description")
	private String description;
	
	@Column(name="from_date")
	private LocalDate fromDate;
	
	@Column(name="to_date")
	private LocalDate toDate;
	
	@Column(name="topics")
	private String topics;
	
	@Column(name="skills")
	private String skills;
	
	@Column(name="is_assigned")
	private Boolean isAssigned;
	
	@Column(name="student_logbook")
	private String studentLogbook;
	
	@Column(name="passfail_grade")
	private Boolean passFailGrade;
	
	@OneToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="student_username")
	private Student student;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="professor_username")
	private Professor supervisor;
	
	@ManyToOne(fetch=FetchType.EAGER, cascade=CascadeType.ALL)
	@JoinColumn(name="company_username")
	private Company company;
	
	public TraineeshipPosition() {}

	public TraineeshipPosition(Integer positionId, String title, String description, LocalDate fromDate,
			LocalDate toDate, String topics, String skills, Boolean isAssigned, String studentLogbook,
			Boolean passFailGrade, Student student, Professor supervisor, Company company) {
		this.positionId = positionId;
		this.title = title;
		this.description = description;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.topics = topics;
		this.skills = skills;
		this.isAssigned = isAssigned;
		this.studentLogbook = studentLogbook;
		this.passFailGrade = passFailGrade;
		this.student = student;
		this.supervisor = supervisor;
		this.company = company;
	}

	// Getters and Setters

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}

	public String getTopics() {
		return topics;
	}

	public void setTopics(String topics) {
		this.topics = topics;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Boolean getIsAssigned() {
		return isAssigned;
	}

	public void setIsAssigned(Boolean isAssigned) {
		this.isAssigned = isAssigned;
	}

	public String getStudentLogbook() {
		return studentLogbook;
	}

	public void setStudentLogbook(String studentLogbook) {
		this.studentLogbook = studentLogbook;
	}

	public Boolean getPassFailGrade() {
		return passFailGrade;
	}

	public void setPassFailGrade(Boolean passFailGrade) {
		this.passFailGrade = passFailGrade;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Professor getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(Professor supervisor) {
		this.supervisor = supervisor;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
}
