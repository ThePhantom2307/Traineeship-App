package myy803.traineeship.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import myy803.traineeship.model.Interest;

public class ProfessorDto {
	private Integer userId;
	private String fullname;
	private String interests;
	
	public ProfessorDto() {}
	
	public ProfessorDto(Integer userId, String fullname, String interests) {
		this.userId = userId;
		this.fullname = fullname;
		this.interests = interests;
	}
	
	public ProfessorDto(Integer userId, String fullname, List<Interest> interests) {
		this.userId = userId;
		this.fullname = fullname;
		this.setInterests(interests);
	}
	
	public Integer getUserId() {
		return this.userId;
	}
	
	public String getFullname() {
		return this.fullname;
	}
	
	public String getInterests() {
		return this.interests;
	}
	
	public List<Interest> getInterestsList() {
		String[] studentInterests = this.interests.split(", ");
		List<Interest> studentInterestsList = new ArrayList<Interest>();
	    for (String s: studentInterests) {
	    	Interest interest = new Interest(s);
	    	studentInterestsList.add(interest);
	    }
		return studentInterestsList;
	}
	
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	
	public void setInterests(String interests) {
		this.interests = interests ;
	}
	
	public void setInterests(List<Interest> interests) {
		this.interests = interests.stream().map(Interest::getName).collect(Collectors.joining(", "));
	}
}
