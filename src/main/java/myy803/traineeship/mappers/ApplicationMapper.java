package myy803.traineeship.mappers;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import myy803.traineeship.model.Application;

public interface ApplicationMapper extends JpaRepository<Application, Integer>{
	Optional<Application> findById(Integer applicationId);
}
