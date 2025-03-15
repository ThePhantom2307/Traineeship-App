package myy803.traineeship.services;

import myy803.traineeship.model.Professor;
import myy803.traineeship.model.User;

public interface IntProfessorService {
	void saveProfessor(Professor professor);
	Boolean isProfessorExists(String username);
	Professor getProfessor(User user);
}
