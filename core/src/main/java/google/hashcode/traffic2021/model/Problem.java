package google.hashcode.traffic2021.model;

import java.util.List;

public record Problem(int duration, int nIntersections, int nStreets, int nCars, int score,
                      List<Street> streets,
                      List<Car> cars) {
}
