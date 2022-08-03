package google.hashcode.traffic2021.evaluation;

import java.util.List;

import static org.junit.Assert.*;
import org.junit.Test;

public class EvaluatorTest {

    @Test
    public void assertProblemASampleResult() {
        var problem = List.of(
            List.of("6", "4", "5", "2", "1000"),
            List.of("2", "0", "rue-de-londres", "1"),
            List.of("0", "1", "rue-d-amsterdam", "1"),
            List.of("3", "1", "rue-d-athenes", "1"),
            List.of("2", "3", "rue-de-rome", "2"),
            List.of("1", "2", "rue-de-moscou", "3"),
            List.of("4", "rue-de-londres", "rue-d-amsterdam", "rue-de-moscou", "rue-de-rome"),
            List.of("3", "rue-d-athenes", "rue-de-moscou", "rue-de-londres")
        );

        var result = List.of(
            List.of("3"),
            List.of("1"),
            List.of("2"),
            List.of("rue-d-athenes", "2"),
            List.of("rue-d-amsterdam", "1"),
            List.of("0"),
            List.of("1"),
            List.of("rue-de-londres", "2"),
            List.of("2"),
            List.of("1"),
            List.of("rue-de-moscou", "1")
        );

        ProblemAdapter problemAdapter = new ProblemAdapter(problem);
        ResultAdapter resultAdapter = new ResultAdapter(result);
        Evaluator evaluator = new Evaluator(problemAdapter.getProblem());
        long score = evaluator.result(resultAdapter.getSchedules());
        assertEquals(1002, score);
    }
}