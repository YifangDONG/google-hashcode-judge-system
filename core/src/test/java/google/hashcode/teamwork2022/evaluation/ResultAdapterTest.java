package google.hashcode.teamwork2022.evaluation;

import java.util.List;

import google.hashcode.teamwork2022.model.Assignment;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ResultAdapterTest {

    @Test
    public void assertResultForProblemA() {

        List<List<String>> content = List.of(
            List.of("3"),
            List.of("WebServer"),
            List.of("Bob", "Anna"),
            List.of("Logging"),
            List.of("Anna"),
            List.of("WebChat"),
            List.of("Maria", "Bob")
        );

        var resultAdapter = new ResultAdapter(content);
        var assignments = resultAdapter.getAssignments();
        var expected = List.of(
            new Assignment("WebServer", List.of("Bob", "Anna")),
            new Assignment("Logging", List.of("Anna")),
            new Assignment("WebChat", List.of("Maria", "Bob"))
        );
        assertEquals(expected, assignments);
    }
}