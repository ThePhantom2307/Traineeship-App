package myy803.traineeship.services;

import java.util.List;

import myy803.traineeship.model.Professor;

public interface ProfessorService {
	void saveProfessor(Professor professor);
	Boolean doesProfessorExists(String username);
	Professor getProfessor(String username);
	List<Professor> searchAndRetrieveMatchingSupervisors(Integer positionId, String searchOption);
}
