package myy803.traineeship.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.User;

public class ProfessorService implements IntProfessorService{
	
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
	public Professor getProfessor(User user) {
		String username = user.getUsername();
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
}
