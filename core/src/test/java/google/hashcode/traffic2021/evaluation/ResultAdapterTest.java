package google.hashcode.traffic2021.evaluation;

import java.util.List;

import google.hashcode.traffic2021.model.Schedule;
import static org.junit.Assert.*;
import org.junit.Test;

public class ResultAdapterTest {

    @Test
    public void assertResultForProblemA() {
        var content = List.of(
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
        var resultAdapter = new ResultAdapter(content);
        var schedules = resultAdapter.getSchedules();
        var expected = List.of(
            new Schedule(1, List.of(
                new Schedule.StreetLights("rue-d-athenes", 2),
                new Schedule.StreetLights("rue-d-amsterdam", 1)
                )),
            new Schedule(0, List.of(
                new Schedule.StreetLights("rue-de-londres", 2)
            )),
            new Schedule(2, List.of(
                new Schedule.StreetLights("rue-de-moscou", 1)
            ))
        );

        assertEquals(expected, schedules);
    }
}