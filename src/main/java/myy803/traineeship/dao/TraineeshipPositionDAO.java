package myy803.traineeship.dao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.traineeship.model.Company;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

@Repository
public interface TraineeshipPositionDAO extends JpaRepository<TraineeshipPosition, Integer>{
	Optional<TraineeshipPosition> findById(Integer id);
	List<TraineeshipPosition> findByCompany(Company company);
	List<TraineeshipPosition> findByIsAssigned(Boolean isAssigned);
	List<TraineeshipPosition> findByCompanyAndIsAssigned(Company company, Boolean isAssigned);
	List<TraineeshipPosition> findBySupervisorAndIsAssigned(Professor professor, Boolean isAssigned);
}
