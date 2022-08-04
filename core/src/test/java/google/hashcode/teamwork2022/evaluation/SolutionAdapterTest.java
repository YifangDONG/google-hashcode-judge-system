package google.hashcode.teamwork2022.evaluation;

import java.util.List;

import google.hashcode.teamwork2022.model.Assignment;
import google.hashcode.utils.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SolutionAdapterTest {

    @Test
    public void assertResultForProblemA() {
        var content = IOHelper.read("src/test/resources/2022/qualification/a");

        var solutionAdapter = new SolutionAdapter(content);
        var assignments = solutionAdapter.getAssignments();
        var expected = List.of(
            new Assignment("WebServer", List.of("Bob", "Anna")),
            new Assignment("Logging", List.of("Anna")),
            new Assignment("WebChat", List.of("Maria", "Bob"))
        );
        assertEquals(expected, assignments);
    }
}