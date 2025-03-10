package myy803.traineeship.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name="Skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "skills")
    private Set<Student> students;

    public Skill() {}

    public Skill(String name) {
        this.name = name;
    }

    public int getSkillId() {
        return skillId;
    }

    public String getName() {
        return name;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
}
