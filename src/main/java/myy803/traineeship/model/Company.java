package myy803.traineeship.model;

import jakarta.persistence.*;

@Entity
@Table(name="companies")
public class Company {
	
	@Id
    @Column(name="username")
    private String username;

    @Column(name="company_name")
    private String companyName;

    @Column(name="location")
    private String location;

    public Company() {}

    public Company(String username, String companyName, String location) {
    	this.username = username;
        this.companyName = companyName;
        this.location = location;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCompanyName() {
        return companyName;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
}