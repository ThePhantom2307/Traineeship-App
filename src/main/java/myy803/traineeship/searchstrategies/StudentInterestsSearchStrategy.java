package myy803.traineeship.searchstrategies;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service("studentInterestsSearchStrategy")
public class StudentInterestsSearchStrategy implements TraineeshipPositionSearchStrategy {

    @Autowired
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    @Override
    public List<TraineeshipPosition> searchPositions(Student student) {
        List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
        
        String interestsString = student.getInterests();
        Set<String> studentInterestsSet = parseCommaSeparatedString(interestsString);
        
        List<TraineeshipPosition> matchingPositions = availablePositions.stream()
            .filter(position -> {
                String topics = position.getTopics();
                Set<String> topicsSet = parseCommaSeparatedString(topics);
                return !Collections.disjoint(studentInterestsSet, topicsSet);
            })
            .collect(Collectors.toList());
        
        sortByCommonInterests(matchingPositions, studentInterestsSet);
        return matchingPositions;
    }
    
    private Set<String> parseCommaSeparatedString(String value) {
        return Arrays.stream(value.split(","))
                     .map(String::trim)
                     .map(String::toLowerCase)
                     .collect(Collectors.toSet());
    }
    
    private int countCommonInterests(Set<String> studentInterests, String topics) {
        Set<String> topicsSet = parseCommaSeparatedString(topics);
        int commonCount = 0;
        for (String interest : studentInterests) {
            if (topicsSet.contains(interest)) {
                commonCount++;
            }
        }
        return commonCount;
    }
    
    private void sortByCommonInterests(List<TraineeshipPosition> positions, Set<String> studentInterests) {
        positions.sort((p1, p2) -> {
            int commonCountP1 = countCommonInterests(studentInterests, p1.getTopics());
            int commonCountP2 = countCommonInterests(studentInterests, p2.getTopics());
            return Integer.compare(commonCountP2, commonCountP1);
        });
    }
}