package myy803.traineeship.searchstrategies.positions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service("studentInterestsAndLocationSearchStrategy")
public class StudentInterestsAndLocationSearchStrategy extends TraineeshipPositionSearchStrategyImpl {

    @Autowired
    private StudentLocationSearchStrategy locationStrategy;

    @Autowired
    private StudentInterestsSearchStrategy interestsStrategy;

    @Override
    protected List<TraineeshipPosition> searchPositions(Student student) {
        List<TraineeshipPosition> locationMatches = locationStrategy.searchPositions(student);
        List<TraineeshipPosition> interestMatches = interestsStrategy.searchPositions(student);        
        interestMatches.retainAll(locationMatches);
        return interestMatches;
    }
}
