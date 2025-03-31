package myy803.traineeship.searchstrategies;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service("studentLocationSearchStrategy")
public class StudentLocationSearchStrategy implements TraineeshipPositionSearchStrategy {

    @Autowired
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    @Override
    public List<TraineeshipPosition> searchPositions(Student student) {
        List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
        String normalizedPreferredLocation = normalize(student.getPreferredLocation());
        return availablePositions.stream()
            .filter(position -> locationMatches(position, normalizedPreferredLocation))
            .collect(Collectors.toList());
    }
    
    private String normalize(String location) {
        return location.trim().toLowerCase();
    }
    
    private boolean locationMatches(TraineeshipPosition position, String normalizedPreferredLocation) {
        String normalizedCompanyLocation = normalize(position.getCompany().getLocation());
        return normalizedCompanyLocation.contains(normalizedPreferredLocation);
    }
}