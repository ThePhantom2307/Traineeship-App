package myy803.traineeship.searchstrategies.supervisors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import myy803.traineeship.dao.ProfessorDAO;
import myy803.traineeship.model.Professor;
import myy803.traineeship.model.TraineeshipPosition;

@Service("supervisorInterestsSearchStrategy")
public class SupervisorInterestsSearchStrategy extends SupervisorSearchStrategyImpl {

    @Autowired
    private ProfessorDAO professorDAO;

    @Override
    protected List<Professor> searchSupervisors(TraineeshipPosition position) {
        List<Professor> allProfessors = professorDAO.findAll();
        String topics = position.getTopics();
        Set<String> positionTopics = parseCommaSeparatedString(topics);
        List<Professor> matchingProfessors = getMatchingProfessors(allProfessors, positionTopics);
        return matchingProfessors;
    }
    
    private List<Professor> getMatchingProfessors(List<Professor> allProfessors, Set<String> positionTopics) {
    	List<Professor> matchingProfessors = new ArrayList<>();
    	
        for (Professor prof : allProfessors) {
            String interests = prof.getInterests();
            Set<String> profInterests = parseCommaSeparatedString(interests);
            
            if (profInterests.containsAll(positionTopics)) {
                matchingProfessors.add(prof);
            }
        }
        
        return matchingProfessors;
    }
    
    private Set<String> parseCommaSeparatedString(String value) {
        Set<String> result = new HashSet<>();
        if (value == null || value.isEmpty()) {
            return result;
        }
        
        for (String part : value.split(",")) {
            String token = part.trim().toLowerCase();
            if (!token.isEmpty()) {
                result.add(token);
            }
        }
        
        return result;
    }
}
