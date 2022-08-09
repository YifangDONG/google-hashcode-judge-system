package google.hashcode.qualification2021.model;

import java.util.List;

public record Car(int id, int nPaths, List<String> streetNames) {
    public Car(int id, List<String> car) {
        this(id, Integer.parseInt(car.get(0)), car.subList(1, car.size()));
    }
}
