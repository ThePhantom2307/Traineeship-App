package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="students")
public class Student {
	
	@Id
    @Column(name="username", unique=true)
    private String username;

    @Column(name="fullname")
    private String fullname;
    
    @Column(name="university_id")
    private String universityId;
    
    @Column(name="interests")
    private String interests;
    
    @Column(name="skills")
    private String skills;
    
    @Column(name="preferred_location")
    private String preferredLocation;
    
    @Column(name="looking_for_traineeship")
    private Boolean lookingForTraineeship;
    
    @Column(name="average_grade")
    private Double averageGrade;
    
    @OneToOne(mappedBy="student")
    private TraineeshipPosition traineeshipPosition;
    
    public Student() {}

    public Student(String username, String fullname, String universityId, String interests,
    		String skills, String preferredLocation, Boolean lookingForTraineeship,
    		Double averageGrade) {
    	this.username = username;
    	this.fullname = fullname;
        this.universityId = universityId;
        this.interests = interests;
        this.skills = skills;
        this.preferredLocation = preferredLocation;
        this.lookingForTraineeship = lookingForTraineeship;
        this.averageGrade = averageGrade;
        this.traineeshipPosition = null;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return this.fullname;
    }
    
    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUniversityId() {
        return this.universityId;
    }
    
    public void setUniversityId(String universityId) {
        this.universityId = universityId;
    }

    public String getInterests() {
        return this.interests;
    }
    
    public void setInterests(String interests) {
        this.interests = interests;
    }

    public String getSkills() {
        return this.skills;
    }
    
    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getPreferredLocation() {
        return this.preferredLocation;
    }
    
    public void setPreferredLocation(String preferredLocation) {
        this.preferredLocation = preferredLocation;
    }
    
    public Boolean getLookingForTraineeship() {
    	return this.lookingForTraineeship;
    }
    
    public void setLookingForTraineeship(Boolean lookingForTraineeship) {
    	this.lookingForTraineeship = lookingForTraineeship;
    }
    
    public Double getAverageGrade() {
    	return this.averageGrade;
    }
    
    public void setAverageGrade(Double averageGrade) {
    	this.averageGrade = averageGrade;
    }
    
    public TraineeshipPosition getTraineeshipPosition() {
    	return this.traineeshipPosition;
    }
    
    public void setAverageGrade(TraineeshipPosition traineeshipPosition) {
    	this.traineeshipPosition = traineeshipPosition;
    }
}
