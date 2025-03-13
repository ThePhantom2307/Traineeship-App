package myy803.traineeship.services;

import myy803.traineeship.dto.ProfessorDto;
import myy803.traineeship.model.Professor;

public interface IntProfessorService {
	ProfessorDto getOrCreateProfessorDto(Integer userId);
	Professor getOrCreateProfessor(ProfessorDto professorDto);
	void saveProfessor(Professor professor);
}
