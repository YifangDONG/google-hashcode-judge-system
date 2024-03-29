package google.hashcode.qualification2021.evaluation;

import google.hashcode.utils.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void assertProblemASampleResult() {
        var problem = IOHelper.read("src/main/resources/2021/qualification/a");
        var solution = IOHelper.read("src/test/resources/2021/qualification/a");
        var problemAdapter = new ProblemAdapter(problem);
        var resultAdapter = new SolutionAdapter(solution);
        var evaluator = new Evaluator(problemAdapter.getProblem());
        var score = evaluator.result(resultAdapter.getSchedules());
        assertEquals(1002, score);
    }

    @Test
    public void assertProblemBResult() {
        var problem = IOHelper.read("src/main/resources/2021/qualification/b");
        var solution = IOHelper.read("src/test/resources/2021/qualification/b");
        var problemAdapter = new ProblemAdapter(problem);
        var resultAdapter = new SolutionAdapter(solution);
        var evaluator = new Evaluator(problemAdapter.getProblem());
        var score = evaluator.result(resultAdapter.getSchedules());
        assertEquals(4_566_479, score);
    }
}