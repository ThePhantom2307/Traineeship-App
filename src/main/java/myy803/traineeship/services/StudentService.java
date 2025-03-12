package myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dto.StudentDto;
import myy803.traineeship.mappers.StudentMapper;
import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Skill;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.User;

@Service
public class StudentService implements IntStudentService {	
	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	private IntUserService userService;
	
	@Autowired
	private IntInterestSkillService interestSkillService;
	
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
	public void saveStudent(Student student) {
		studentMapper.save(student);
	}
}
