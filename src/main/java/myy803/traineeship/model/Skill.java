package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="skills")
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int skillId;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

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

    public void setSkillId(int skillId) {
        this.skillId = skillId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
