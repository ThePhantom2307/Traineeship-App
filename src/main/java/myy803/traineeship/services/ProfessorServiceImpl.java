package myy803.traineeship.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.model.Professor;

@Service
public class ProfessorServiceImpl implements ProfessorService{
	
	@Autowired
	private ProfessorDAO professorDAO;
	
	@Override
	public void saveProfessor(Professor professor) {
		professorDAO.save(professor);
	}
	
	@Override
	public Boolean isProfessorExists(String username) {
		Optional<Professor> student = professorDAO.findByUsername(username);
		if (student.isPresent()) {
			return true;
		}
		return false;
	}
	
	@Override
	public Professor getProfessor(String username) {
		Optional<Professor> optProfessor = professorDAO.findByUsername(username);
	    Professor professor;
	    
	    if (optProfessor.isPresent()) {
	    	professor = optProfessor.get();
	    } else {
	    	professor = new Professor();
	    	professor.setUsername(username);
	        professor.setFullname("");
	        professor.setInterests("");
	    }
	    
		return professor;
	}

	// IMPORTANT: this method is used to display professors in process of assign a supervisor, when search strategy
	// 			  will be created this method will never be used
	@Override
	public List<Professor> getAllProfessors() {
		List<Professor> professors = professorDAO.findAll();
		List<Professor> professorsWithProfile = new ArrayList<Professor>();
		for (Professor professor: professors) {
			professorsWithProfile.add(professor);
		}
		return professorsWithProfile;
	}
}
