package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="professors")
public class Professor {

    @Id
    @Column(name="username")
    private String username;

    @Column(name="fullname")
    private String fullname;

    @Column(name="interests")
    private String interests;

    public Professor() {}

    public Professor(String username, String fullname, String interests) {
        this.username = username;
        this.fullname = fullname;
        this.interests = interests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}