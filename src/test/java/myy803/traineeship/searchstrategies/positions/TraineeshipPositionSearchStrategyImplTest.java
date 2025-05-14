package myy803.traineeship.searchstrategies.positions;

import myy803.traineeship.model.Student;
import myy803.traineeship.model.TraineeshipPosition;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TraineeshipPositionSearchStrategyImplTest {

    // Dummy class to expose protected methods
    static class DummySearchStrategy extends TraineeshipPositionSearchStrategyImpl {
        @Override
        protected List<TraineeshipPosition> searchPositions(Student student) {
            return new ArrayList<>(); // Not used here
        }

        public Set<String> callParseCommaSeparatedString(String input) {
            return parseCommaSeparatedString(input);
        }

        public List<TraineeshipPosition> callFilterPositionsBySkills(List<TraineeshipPosition> positions, Student student) {
            return super.filterPositionsBySkills(positions, student);
        }
    }

    DummySearchStrategy strategy = new DummySearchStrategy();

    // === TEST 1: parseCommaSeparatedString ===
    @Test
    public void testParseCommaSeparatedString() {
        // Setup
        String input = " Java , Spring ,  SQL ";

        // Exercise
        Set<String> result = strategy.callParseCommaSeparatedString(input);

        // Verify
        assertEquals(Set.of("java", "spring", "sql"), result);
    }

    // === TEST 2: parseCommaSeparatedString - empty ===
    @Test
    public void testParseCommaSeparatedString_Empty() {
        Set<String> result = strategy.callParseCommaSeparatedString("");
        assertTrue(result.isEmpty());

        result = strategy.callParseCommaSeparatedString(null);
        assertTrue(result.isEmpty());
    }

    // === TEST 3: filterPositionsBySkills ===
    @Test
    public void testFilterPositionsBySkills() {
        // Setup
        Student student = new Student();
        student.setSkills("java,spring");

        TraineeshipPosition p1 = new TraineeshipPosition();
        p1.setSkills("java"); // ✅ match

        TraineeshipPosition p2 = new TraineeshipPosition();
        p2.setSkills("java,spring"); // ✅ match

        TraineeshipPosition p3 = new TraineeshipPosition();
        p3.setSkills("java,sql"); // ❌ not match

        List<TraineeshipPosition> all = List.of(p1, p2, p3);

        // Exercise
        List<TraineeshipPosition> result = strategy.callFilterPositionsBySkills(all, student);

        // Verify
        assertEquals(2, result.size());
        assertTrue(result.contains(p1));
        assertTrue(result.contains(p2));
        assertFalse(result.contains(p3));
    }
}
