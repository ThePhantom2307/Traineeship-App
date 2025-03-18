package myy803.traineeship.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myy803.traineeship.model.Evaluation;
import myy803.traineeship.model.EvaluationType;
import myy803.traineeship.model.TraineeshipPosition;

@Repository
public interface EvaluationDAO extends JpaRepository<Evaluation, Integer>{
	Optional<Evaluation> findByEvaluationTypeAndTraineeshipPosition(EvaluationType evaluationType, TraineeshipPosition position);
}
