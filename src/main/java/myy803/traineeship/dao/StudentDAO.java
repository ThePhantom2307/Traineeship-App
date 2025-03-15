package myy803.traineeship.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import myy803.traineeship.model.Student;

@Repository
public interface StudentDAO extends JpaRepository<Student, String> {
    Optional<Student> findByUsername(String username);
}
