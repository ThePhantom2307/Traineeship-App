package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="interests")
public class Interest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="interest_id")
    private int interestId;

    @Column(name="name", nullable=false, unique=true)
    private String name;

    public Interest() {}

    public Interest(String name) {
        this.name = name;
    }

    public int getInterestId() {
        return interestId;
    }

    public String getName() {
        return name;
    }

    public void setInterestId(int interestId) {
        this.interestId = interestId;
    }

    public void setName(String name) {
        this.name = name;
    }
}
