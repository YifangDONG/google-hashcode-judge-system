package google.hashcode.traffic2021.evaluation;

import java.util.List;

import google.hashcode.traffic2021.model.Car;
import google.hashcode.traffic2021.model.Problem;
import google.hashcode.traffic2021.model.Street;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class ProblemAdapterTest {

    @Test
    public void assertProblemA() {

        var content = List.of(
            List.of("6", "4", "5", "2", "1000"),
            List.of("2", "0", "rue-de-londres", "1"),
            List.of("0", "1", "rue-d-amsterdam", "1"),
            List.of("3", "1", "rue-d-athenes", "1"),
            List.of("2", "3", "rue-de-rome", "2"),
            List.of("1", "2", "rue-de-moscou", "3"),
            List.of("4", "rue-de-londres", "rue-d-amsterdam", "rue-de-moscou", "rue-de-rome"),
            List.of("3", "rue-d-athenes", "rue-de-moscou", "rue-de-londres")
        );

        var problemAdapter = new ProblemAdapter(content);
        var problem = problemAdapter.getProblem();

        var expected = new Problem(6, 4, 5, 2, 1000,
            List.of(
                new Street(2, 0, "rue-de-londres", 1),
                new Street(0, 1, "rue-d-amsterdam", 1),
                new Street(3, 1, "rue-d-athenes", 1),
                new Street(2, 3, "rue-de-rome", 2),
                new Street(1, 2, "rue-de-moscou", 3)
            ),
            List.of(
                new Car(0, 4, List.of("rue-de-londres", "rue-d-amsterdam", "rue-de-moscou", "rue-de-rome")),
                new Car(1, 3, List.of("rue-d-athenes", "rue-de-moscou", "rue-de-londres"))
            )
        );
        assertEquals(expected, problem);
    }
}