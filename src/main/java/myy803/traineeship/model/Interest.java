package myy803.traineeship.model;

import java.util.Set;

import jakarta.persistence.*;

@Entity
@Table(name="Interests")
public class Interest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="interest_id")
	private int interestID;
	
	@Column(name="name", nullable=false, unique=true)
	private String name;
	
	@ManyToMany(mappedBy="interests")
	private Set<Student> students;
	
	public Interest() {}
	
	public Interest(String name) {
		this.name = name;
	}

    public int getInterestId() {
        return interestID;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }
    
    public void setInterestId(int interestId) {
        this.interestID = interestId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
