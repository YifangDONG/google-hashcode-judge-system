package google.hashcode.qualification2021.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import google.hashcode.qualification2021.model.Schedule;

class SolutionAdapter {
    private final List<List<String>> content;

    public SolutionAdapter(List<List<String>> content) {
        this.content = content;
    }

    public static boolean isGreenLight(int time, String street,
        Map<String, Schedule.GreenLightsCycle> streetToGreenLightCycle) {
        var greenLightsCycle = streetToGreenLightCycle.get(street);
        if (greenLightsCycle == null) {
            return false;
        } else {
            int mod = time % greenLightsCycle.total();
            return mod >= greenLightsCycle.start()
                   && mod < greenLightsCycle.end();
        }
    }

    public List<Schedule> getSchedules() {
        List<Schedule> schedules = new ArrayList<>();
        int i = 1;
        while (i < content.size()) {
            int intersection = Integer.parseInt(content.get(i++).get(0));
            int nStreets = Integer.parseInt(content.get(i++).get(0));
            List<Schedule.StreetLights> streetLights = content.subList(i, i + nStreets)
                .stream()
                .map(Schedule.StreetLights::new)
                .collect(Collectors.toList());
            schedules.add(new Schedule(intersection, streetLights));
            i += nStreets;
        }
        return schedules;
    }

    public static Map<String, Schedule.GreenLightsCycle> getStreetToGreenLightCycle(List<Schedule> schedules) {
        Map<String, Schedule.GreenLightsCycle> streetToGreenLightCycle = new HashMap<>();
        for (Schedule schedule : schedules) {
            int total = schedule.streetLights().stream().mapToInt(Schedule.StreetLights::greenLightDuration).sum();
            int start = 0;
            for (Schedule.StreetLights streetLights : schedule.streetLights()) {
                if (streetLights.greenLightDuration() != 0) {
                    streetToGreenLightCycle.put(streetLights.street(),
                        new Schedule.GreenLightsCycle(start, start + streetLights.greenLightDuration(), total));
                    start += streetLights.greenLightDuration();
                }
            }
        }
        return streetToGreenLightCycle;
    }

}
