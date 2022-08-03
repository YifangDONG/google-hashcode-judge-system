package google.hashcode.traffic2021.model;

import java.util.List;

public record Schedule(int intersection, List<StreetLights> streetLights) {

    public record StreetLights(String street, int greenLightDuration) {
        public StreetLights(List<String> content) {
            this(content.get(0), Integer.parseInt(content.get(1)));
        }
    }

    public record GreenLightsCycle(int start, int end, int total) {}
}
