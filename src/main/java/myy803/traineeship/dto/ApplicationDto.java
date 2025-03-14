package myy803.traineeship.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.springframework.stereotype.Component;

@Component
public class ApplicationDto {
	private Integer applicationId;
	private Integer studentId;
	private String applicationDate;
	private String status;
	
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy ss:mm:HH");
	
	public ApplicationDto() {}
	
	public ApplicationDto(Integer studentId, LocalDateTime applicationDate, String status) {
		this.studentId = studentId;
		this.applicationDate = formatDate(applicationDate);
		this.status = status;
	}
	
	public ApplicationDto(Integer studentId, String applicationDate, String status) {
		this.studentId = studentId;
		this.applicationDate = applicationDate;
		this.status = status;
	}
	
	public Integer getApplicationId() {
		return this.applicationId;
	}
	
	public Integer getStudentId() {
		return this.studentId;
	}
	
	public String getApplicationDate() {
		String[] applicationDateSplitted = this.applicationDate.split("T");
		if (applicationDateSplitted.length > 1) {
			return applicationDateSplitted[0] + " " + applicationDateSplitted[1];
		} else {
			return this.applicationDate;
		}
		
	}
	
	public LocalDateTime getApplicationDateFormatted() {
        if (applicationDate == null || applicationDate.trim().isEmpty()) {
            return LocalDateTime.now();
        }
        try {
            return LocalDateTime.parse(applicationDate, FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + applicationDate, e);
        }
    }
	
	public String getStatus() {
		return this.status;
	}
	
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}
	
	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	
	public void setApplicationDate(LocalDateTime applicationDate) {
		this.applicationDate = applicationDate.toString();
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	private String formatDate(LocalDateTime date) {
        return (date != null) ? date.format(FORMATTER) : null;
    }
}
