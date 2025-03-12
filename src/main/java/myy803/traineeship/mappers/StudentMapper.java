package myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Student;

public interface StudentMapper extends JpaRepository<Student, Integer> {
    Optional<Student> findById(Integer student_id);
}
