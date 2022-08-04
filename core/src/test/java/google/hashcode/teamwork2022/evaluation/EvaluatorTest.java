package google.hashcode.teamwork2022.evaluation;

import java.util.List;

import google.hashcode.utils.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void assertProblemASampleResult() {
        var problem = IOHelper.read("src/main/resources/2022/qualification/a");
        var solution = IOHelper.read("src/test/resources/2022/qualification/a");
        var problemAdapter = new ProblemAdapter(problem);
        var solutionAdapter = new SolutionAdapter(solution);
        var evaluator = new Evaluator(problemAdapter.getProblem());
        var resultInside = evaluator.getResultInside(solutionAdapter.getAssignments());
        assertEquals(33, resultInside.score());
    }
}