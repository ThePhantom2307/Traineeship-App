package myy803.traineeship.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dto.ApplicationDto;
import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.mappers.ApplicationMapper;
import myy803.traineeship.mappers.StudentMapper;
import myy803.traineeship.model.Application;
import myy803.traineeship.model.ApplicationStatus;
import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;

@Service
public class StudentService implements IntStudentService {	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private ApplicationMapper applicationMapper;
	
	@Autowired
	private IntUserService userService;
	
	@Autowired
	private IntInterestSkillService interestSkillService;
	
	@Override
	public void saveStudent(Student student) {
		studentMapper.save(student);
	}
	
	@Override
	public void saveApplication(Application application) {
		Student student = application.getStudent();
		Optional<Application> optApplication = applicationMapper.findById(student.getStudentId());
		if (optApplication.isEmpty()) {
			applicationMapper.save(application);
		}
	}
	
	@Override
	public StudentDto getOrCreateStudentDto(Integer userId) {
		Optional<Student> optStudentProfile = studentMapper.findById(userId);
		StudentDto studentDto;
		
		if (optStudentProfile.isPresent()) {
			Student student = optStudentProfile.get();
			String fullname = student.getFullname();
			String universityId = student.getUniversityId();
			List<Interest> interests = student.getInterests();
			List<Skill> skills = student.getSkills();
			String preferredLocation = student.getPreferredLocation();
			studentDto = new StudentDto(userId, fullname, universityId, interests, skills, preferredLocation);
		} else {
			studentDto = new StudentDto();
	        studentDto.setUserId(userId);
	        studentDto.setFullname("");
	        studentDto.setUniversityId("");
	        studentDto.setInterests("");
	        studentDto.setSkills("");
	        studentDto.setPreferredLocation("");
		}
		
		return studentDto;
	}
	
	@Override
	public Student getOrCreateStudent(StudentDto studentDto) {
		Optional<Student> optStudent = studentMapper.findById(studentDto.getUserId());
	    Student student;
	    
	    if (optStudent.isPresent()) {
	        student = optStudent.get();
	    } else {
	        student = new Student();
	        User user = userService.findById(studentDto.getUserId());
	        student.setUser(user);
	    }
	    
	    student.setFullname(studentDto.getFullname());
	    student.setUniversityId(studentDto.getUniversityId());
	    student.setPreferredLocation(studentDto.getPreferredLocation());
	    
	    List<Skill> studentSkillsList = getOrCreateSkills(studentDto.getSkillsList());
	    student.setSkills(studentSkillsList);
	    
	    List<Interest> studentInterestsList = getOrCreateInterests(studentDto.getInterestsList());
	    student.setInterests(studentInterestsList);
	    
		return student;
	}
	
	public List<Skill> getOrCreateSkills(List<Skill> skills) {
	    List<Skill> skillsList = new ArrayList<>();

	    for (Skill skill : skills) {
	        Optional<Skill> existingSkill = interestSkillService.findSkillByName(skill.getName());

	        Skill finalSkill = existingSkill.orElseGet(() -> interestSkillService.saveSkill(new Skill(skill.getName())));
	        skillsList.add(finalSkill);
	    }

	    return skillsList;
	}

	
	public List<Interest> getOrCreateInterests(List<Interest> interests) {
	    List<Interest> interestsList = new ArrayList<>();

	    for (Interest interest : interests) {
	        Optional<Interest> existingInterest = interestSkillService.findInterestByName(interest.getName());

	        Interest finalInterest = existingInterest.orElseGet(() -> interestSkillService.saveInterest(new Interest(interest.getName())));
	        interestsList.add(finalInterest);
	    }

	    return interestsList;
	}
	
	@Override
	public ApplicationDto getOrCreateTraineeshipApplicationDto(Integer studentId) {
		Optional<Application> optApplication = applicationMapper.findById(studentId);
		ApplicationDto applicationDto;
		
		if (optApplication.isPresent()) {
			Application application = optApplication.get();
			LocalDateTime applicationDate = application.getApplicationDate();
			String status = application.getStatus().getValue();
			applicationDto = new ApplicationDto(studentId, applicationDate, status);
		} else {
			applicationDto = new ApplicationDto();
			applicationDto.setStudentId(studentId);
			applicationDto.setApplicationDate("");
			applicationDto.setStatus("");
		}
		
		return applicationDto;
	}
	
	@Override
	public Application getOrCreateTraineeshipApplication(ApplicationDto applicationDto) {
	    Optional<Application> optApplication = Optional.empty();

	    if (applicationDto.getApplicationId() != null) {
	        optApplication = applicationMapper.findById(applicationDto.getApplicationId());
	    }

	    Optional<Student> optStudent = studentMapper.findById(applicationDto.getStudentId());

	    if (optStudent.isEmpty()) {
	        throw new IllegalArgumentException("Student with ID " + applicationDto.getStudentId() + " not found.");
	    }

	    Student student = optStudent.get();
	    Application application;

	    if (optApplication.isPresent()) {
	        application = optApplication.get();
	    } else {
	        application = new Application();
	        application.setStudent(student);
	        application.setStatus(ApplicationStatus.PENDING);
	    }

	    if (applicationDto.getApplicationDateFormated() != null) {
	        application.setApplicationDate(applicationDto.getApplicationDateFormated());
	    } else {
	        application.setApplicationDate(LocalDateTime.now());
	    }

	    return application;
	}

}
