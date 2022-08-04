package google.hashcode.teamwork2022.evaluation;

import java.util.List;
import java.util.Map;

import google.hashcode.teamwork2022.model.Contributor;
import google.hashcode.teamwork2022.model.Problem;
import google.hashcode.teamwork2022.model.Project;
import google.hashcode.teamwork2022.model.Skill;
import google.hashcode.utils.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProblemAdapterTest {

    @Test
    public void assertProblemA() {
        var content = IOHelper.read("src/main/resources/2022/qualification/a");
        var problemAdapter = new ProblemAdapter(content);
        var problem = problemAdapter.getProblem();

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
        var expected = new Problem(3, contributors, 3, projects);

        assertEquals(expected, problem);
    }
}