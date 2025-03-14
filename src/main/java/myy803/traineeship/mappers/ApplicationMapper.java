package myy803.traineeship.mappers;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Application;
import myy803.traineeship.model.ApplicationStatus;

public interface ApplicationMapper extends JpaRepository<Application, Integer>{
	Optional<Application> findById(Integer applicationId);
	List<Application> findByStatus(ApplicationStatus status);
	Optional<Application> findByStudent_StudentId(Integer studentId);
}
