package google.hashcode.traffic2021.evaluation;

import java.util.List;

import google.hashcode.traffic2021.model.Schedule;
import google.hashcode.utils.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class SolutionAdapterTest {

    @Test
    public void assertResultForProblemA() {
        var content = IOHelper.read("src/test/resources/2021/qualification/a");

        var resultAdapter = new SolutionAdapter(content);
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