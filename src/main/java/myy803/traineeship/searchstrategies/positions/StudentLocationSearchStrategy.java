package myy803.traineeship.searchstrategies.positions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service("studentLocationSearchStrategy")
public class StudentLocationSearchStrategy extends TraineeshipPositionSearchStrategyImpl{

    @Autowired
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    public List<TraineeshipPosition> searchPositions(Student student) {
        List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
        String preferredLocation = student.getPreferredLocation();
        String normalizedPreferredLocation = normalize(preferredLocation);
        List<TraineeshipPosition> preferredLocationMatched = getMatchingInterests(availablePositions, normalizedPreferredLocation);
        return preferredLocationMatched;
    }
    
    private List<TraineeshipPosition> getMatchingInterests(List<TraineeshipPosition> positions, String preferredLocation) {
    	List<TraineeshipPosition> results = new ArrayList<>();
    	
    	for (TraineeshipPosition position : positions) {
            String companyLocation = position.getCompany().getLocation();
            String normalizedCompanyLocation = normalize(companyLocation);

            boolean matches = normalizedCompanyLocation.contains(preferredLocation);
            if (matches) {
            	results.add(position);
            }
        }
    	
    	return results;
    }
    
    private String normalize(String location) {
        return location.trim().toLowerCase();
    }
}
