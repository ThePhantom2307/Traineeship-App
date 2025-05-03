package myy803.traineeship.searchstrategies;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

public abstract class TraineeshipPositionSearchStrategyImpl implements TraineeshipPositionSearchStrategy {
    
    @Override
    public List<TraineeshipPosition> exectuteSearchForPositions(Student student) {
        List<TraineeshipPosition> positions = searchPositions(student);
        List<TraineeshipPosition> matchingPositions = filterPositionsBySkills(positions, student);
        return matchingPositions;
    }
    
    protected abstract List<TraineeshipPosition> searchPositions(Student student);

    private List<TraineeshipPosition> filterPositionsBySkills(List<TraineeshipPosition> positions, Student student) {
        String skillsString = student.getSkills();
        Set<String> studentSkills = parseCommaSeparatedString(skillsString);

        List<TraineeshipPosition> matchingPositions = new ArrayList<TraineeshipPosition>();

        for (TraineeshipPosition position : positions) {
            String required = position.getSkills();
            Set<String> requiredSkills = parseCommaSeparatedString(required);

            if (studentSkills.containsAll(requiredSkills)) {
                matchingPositions.add(position);
            }
        }

        return matchingPositions;
    }
    
    private Set<String> parseCommaSeparatedString(String value) {
        Set<String> result = new HashSet<>();
        if (value == null || value.isEmpty()) {
            return result;
        }
        String[] parts = value.split(",");
        for (String part : parts) {
            String normalized = part.trim().toLowerCase();
            if (!normalized.isEmpty()) {
                result.add(normalized);
            }
        }
        return result;
    }
}
