package myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Professor;

public interface ProfessorMapper extends JpaRepository<Professor, Integer>{
	Optional<Professor> findById(Integer professorId);
}
