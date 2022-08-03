package google.hashcode.traffic2021.evaluation;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import google.hashcode.traffic2021.model.Car;
import google.hashcode.traffic2021.model.Problem;
import google.hashcode.traffic2021.model.Street;

public class ProblemAdapter {

    private final List<List<String>> content;

    public ProblemAdapter(List<List<String>> content) {
        this.content = content;
    }

    public Problem getProblem() {
        return new Problem(duration(), nIntersections(), nStreets(), nCars(), score(), streets(), cars());
    }

    private int duration() {
        return Integer.parseInt(content.get(0).get(0));
    }

    private int nIntersections() {
        return Integer.parseInt(content.get(0).get(1));
    }

    private int nStreets() {
        return Integer.parseInt(content.get(0).get(2));
    }

    private int nCars() {
        return Integer.parseInt(content.get(0).get(3));
    }

    private int score() {
        return Integer.parseInt(content.get(0).get(4));
    }

    private List<Street> streets() {
        return content.subList(1, 1 + nStreets())
            .stream()
            .map(Street::new)
            .collect(Collectors.toList());
    }

    private List<Car> cars() {
        AtomicInteger id = new AtomicInteger(0);
        return content.subList(1 + nStreets(), 1 + nStreets() + nCars())
            .stream()
            .map(car -> new Car(id.getAndIncrement(), car))
            .collect(Collectors.toList());
    }

}
