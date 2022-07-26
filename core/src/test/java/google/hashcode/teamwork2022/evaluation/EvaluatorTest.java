package google.hashcode.teamwork2022.evaluation;

import java.util.List;
import java.util.Map;

import google.hashcode.teamwork2022.model.Assignment;
import google.hashcode.teamwork2022.model.Contributor;
import google.hashcode.teamwork2022.model.Problem;
import google.hashcode.teamwork2022.model.Project;
import google.hashcode.teamwork2022.model.Skill;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void assertProblemASampleResult() {
        var contributors = List.of(
            new Contributor("Anna", Map.of("C++", new Skill("C++", 2))),
            new Contributor("Bob", Map.of("HTML", new Skill("HTML", 5), "CSS", new Skill("CSS", 5))),
            new Contributor("Maria", Map.of("Python", new Skill("Python", 3)))
        );
        var projects = List.of(
            new Project("Logging", 5, 10, 5, List.of(new Skill("C++", 3))),
            new Project("WebServer", 7, 10, 7, List.of(new Skill("HTML", 3), new Skill("C++", 2))),
            new Project("WebChat", 10, 20, 20, List.of(new Skill("Python", 3), new Skill("HTML", 3)))
        );
        var problem = new Problem(3, contributors, 3, projects);

        var assignments = List.of(
            new Assignment("WebServer", List.of("Bob", "Anna")),
            new Assignment("Logging", List.of("Anna")),
            new Assignment("WebChat", List.of("Maria", "Bob"))
        );

        var evaluator = new Evaluator(problem);
        var resultInside = evaluator.getResultInside(assignments);
        assertEquals(33, resultInside.score());
    }
}