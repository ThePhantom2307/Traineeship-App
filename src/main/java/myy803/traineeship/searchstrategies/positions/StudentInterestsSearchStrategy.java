package myy803.traineeship.searchstrategies.positions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.TraineeshipPositionDAO;
import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;

@Service("studentInterestsSearchStrategy")
public class StudentInterestsSearchStrategy extends TraineeshipPositionSearchStrategyImpl {

    @Autowired
    private TraineeshipPositionDAO traineeshipPositionDAO;
    
    protected List<TraineeshipPosition> searchPositions(Student student) {
        List<TraineeshipPosition> availablePositions = traineeshipPositionDAO.findByIsAssigned(false);
        String interestsString = student.getInterests();
        Set<String> studentInterestsSet = parseCommaSeparatedString(interestsString);
        List<TraineeshipPosition> matchingPositions = getMatchingPositions(availablePositions, studentInterestsSet);
        sortByCommonInterests(matchingPositions, studentInterestsSet);

        return matchingPositions;
    }
    
    private List<TraineeshipPosition> getMatchingPositions(List<TraineeshipPosition> positions, Set<String> studentInterestsSet) {
    	List<TraineeshipPosition> matchingPositions = new ArrayList<>();
        for (TraineeshipPosition position : positions) {
            String topics = position.getTopics();
            Set<String> topicsSet = parseCommaSeparatedString(topics);
            boolean hasCommon = !Collections.disjoint(studentInterestsSet, topicsSet);
            if (hasCommon) {
                matchingPositions.add(position);
            }
        }
        
        return matchingPositions;
    }

    private int countCommonInterests(Set<String> studentInterests, Set<String> topicsSet) {
        int count = 0;
        for (String interest : studentInterests) {
            if (topicsSet.contains(interest)) {
                count++;
            }
        }
        return count;
    }

    private void sortByCommonInterests(List<TraineeshipPosition> positions, Set<String> studentInterests) {
        Collections.sort(positions, (p1, p2) -> {
            int count1 = countCommonInterests(studentInterests, parseCommaSeparatedString(p1.getTopics()));
            int count2 = countCommonInterests(studentInterests, parseCommaSeparatedString(p2.getTopics()));
            return Integer.compare(count2, count1);
        });
    }
}
