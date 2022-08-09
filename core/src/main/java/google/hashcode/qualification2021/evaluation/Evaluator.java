package google.hashcode.qualification2021.evaluation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import google.hashcode.qualification2021.model.Car;
import google.hashcode.qualification2021.model.Problem;
import google.hashcode.qualification2021.model.Schedule;
import google.hashcode.qualification2021.model.Street;

class Evaluator {
    /*
    for every second:
        for every car:
            if car isn't arrive destination:
                if carArriveIntersection and firstInQueue and noCarBeforePassInThisSecond and greenLight:
                    car pass intersection and go to next street
                    update status
     */
    private final Problem problem;

    public Evaluator(Problem problem) {
        this.problem = problem;
    }

    public long result(List<Schedule> schedules) {
        //immutable----
        var streetToGreenLightCycle = SolutionAdapter.getStreetToGreenLightCycle(schedules);
        var cars = getCarIdToCar(problem.cars());
        var streetLength = getStreetLength(problem.streets());
        //--immutable

        var streetQueue = getInitialStreetQueue(problem.cars());
        Map<Integer, Integer> carToPassIntersectionTime = new HashMap<>();
        // when car arrive to destination, it will be removed from this list
        var carInStreet = getInitialCarInStreet(problem.cars());

        int totalTime = problem.duration();
        int nCars = problem.nCars();

        long score = 0;
        long bonus = 0;

        for (int t = 0; t < totalTime; t++) {
            Set<String> streetHasCarPass = new HashSet<>();
            for (int c = 0; c < nCars; c++) {
                if (carInStreet.containsKey(c)) {

                    int streetId = carInStreet.get(c);
                    String street = cars.get(c).streetNames().get(streetId);

                    boolean carInLastStreet = streetId + 1 == cars.get(c).nPaths();
                    if (carInLastStreet) {
                        if (carToPassIntersectionTime.get(c) + streetLength.get(street) <= t) {
                            score += problem.score();
                            bonus += totalTime - t;
                            carInStreet.remove(c);
                        }

                    } else {

                        boolean inIntersection =
                            streetId == 0 || carToPassIntersectionTime.get(c) + streetLength.get(street) <= t;
                        boolean firstInQueue =
                            streetQueue.get(street).peekFirst() == c
                            && !streetHasCarPass.contains(street);
                        boolean isGreenLight = SolutionAdapter.isGreenLight(t, street, streetToGreenLightCycle);

                        if (inIntersection && firstInQueue && isGreenLight) {
                            streetHasCarPass.add(street);
                            carToPassIntersectionTime.put(c, t);
                            streetQueue.get(street).pollFirst(); // remove from current street queue

                            int nextStreetId = carInStreet.get(c) + 1;
                            boolean arriveLastStreet = nextStreetId + 1 == cars.get(c).nPaths();
                            if (!arriveLastStreet) {
                                String nextStreet = cars.get(c).streetNames().get(nextStreetId);
                                streetQueue.putIfAbsent(nextStreet, new ArrayDeque<>());
                                streetQueue.get(nextStreet).addLast(c); // add to next street queue
                            }
                            carInStreet.put(c, nextStreetId);
                        }
                    }
                }
            }
        }
        return score + bonus;
    }

    private static Map<String, Deque<Integer>> getInitialStreetQueue(List<Car> cars) {
        Map<String, Deque<Integer>> carInStreet = new HashMap<>();
        for (Car car : cars) {
            String street = car.streetNames().get(0);
            carInStreet.putIfAbsent(street, new ArrayDeque<>());
            carInStreet.get(street).addLast(car.id());
        }
        return carInStreet;
    }

    private static Map<String, Integer> getStreetLength(List<Street> streets) {
        return streets.stream()
            .collect(Collectors.toMap(Street::name, Street::length));
    }

    private Map<Integer, Car> getCarIdToCar(List<Car> cars) {
        return cars.stream()
            .collect(Collectors.toMap(Car::id, Function.identity()));
    }

    private Map<Integer, Integer> getInitialCarInStreet(List<Car> cars) {
        Map<Integer, Integer> carInStreet = new HashMap<>();
        for (Car car : cars) {
            carInStreet.put(car.id(), 0);
        }
        return carInStreet;
    }
}
