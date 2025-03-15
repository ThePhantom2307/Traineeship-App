package myy803.traineeship.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.traineeship.model.Professor;

@Repository
public interface ProfessorDAO extends JpaRepository<Professor, String>{
	Optional<Professor> findByUsername(String username);
}
