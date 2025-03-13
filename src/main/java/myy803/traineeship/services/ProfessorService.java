package myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.traineeship.dto.ProfessorDto;
import myy803.traineeship.mappers.ProfessorMapper;
import myy803.traineeship.model.Interest;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.User;

public class ProfessorService implements IntProfessorService{
	@Autowired
	private IntUserService userService;
	
	@Autowired
	private IntInterestSkillService interestSkillService;
	
	@Autowired
	private ProfessorMapper professorMapper;
	
	@Override
	public void saveProfessor(Professor professor) {
		professorMapper.save(professor);
	}
	
	@Override
	public ProfessorDto getOrCreateProfessorDto(Integer userId) {
		Optional<Professor> optProfessorProfile = professorMapper.findById(userId);
		ProfessorDto professorDto;
		
		if (optProfessorProfile.isPresent()) {
			Professor professor = optProfessorProfile.get();
			String fullname = professor.getFullname();
			List<Interest> interests = professor.getInterests();
			professorDto = new ProfessorDto(userId, fullname, interests);
		} else {
			professorDto = new ProfessorDto();
			professorDto.setUserId(userId);
			professorDto.setFullname("");
	        professorDto.setInterests("");
		}
		
		return professorDto;
	}
	
	@Override
	public Professor getOrCreateProfessor(ProfessorDto professorDto) {
		Optional<Professor> optProfessor = professorMapper.findById(professorDto.getUserId());
	    Professor professor;
	    
	    if (optProfessor.isPresent()) {
	    	professor = optProfessor.get();
	    } else {
	    	professor = new Professor();
	        User user = userService.findById(professorDto.getUserId());
	        professor.setUser(user);
	    }
	    
	    professor.setFullname(professorDto.getFullname());
	    
	    List<Interest> studentInterestsList = getOrCreateInterests(professorDto.getInterestsList());
	    professor.setInterests(studentInterestsList);
	    
		return professor;
	}
	
	private List<Interest> getOrCreateInterests(List<Interest> interests) {
	    List<Interest> interestsList = new ArrayList<>();

	    for (Interest interest : interests) {
	        Optional<Interest> existingInterest = interestSkillService.findInterestByName(interest.getName());

	        Interest finalInterest = existingInterest.orElseGet(() -> interestSkillService.saveInterest(new Interest(interest.getName())));
	        interestsList.add(finalInterest);
	    }

	    return interestsList;
	}
}
