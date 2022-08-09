package google.hashcode.shell;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import google.hashcode.ProblemContentLoader;
import picocli.CommandLine;

@CommandLine.Command(name = "score", mixinStandardHelpOptions = true,
        description = "This is a tool to calculate the score of google hashcode competition")
public class ScoreCalculator implements Callable<Integer> {

    private final Map<Integer, Map<String, Set<String>>> supportedProblem;

    @CommandLine.Option(names = {"-y", "-year"}, description = "set competition year", required = true)
    private int year;
    @CommandLine.Option(names = {"-r", "-round"}, description = "set competition round", required = true)
    private String round;
    @CommandLine.Option(names = {"-d", "-dataset"}, description = "set competition dataset", required = true)
    private String dataset;
    @CommandLine.Option(names = {"-f", "-file"}, description = "set the solution's file path", required = true)
    private String solutionFilePath;

    public ScoreCalculator(Map<Integer, Map<String, Set<String>>> supportedProblem) {
        this.supportedProblem = supportedProblem;
    }

    @Override
    public Integer call() {
        if (supportedProblem.get(year) == null) {
            System.out.printf("doesn't support year %d \n current supported years are: %s",
                    year, supportedProblem.keySet()
            );
            return -1;
        } else if (supportedProblem.get(year).get(round) == null) {
            System.out.printf("doesn't support round %s in year %d \n current supported round are: %s",
                    round, year, supportedProblem.get(year).keySet()
            );
            return -1;
        } else if (!supportedProblem.get(year).get(round).contains(dataset)) {
            System.out.printf("doesn't support dataset %s in %s \n current supported datasets are: %s",
                    dataset, year + round, supportedProblem.get(year).get(round)
            );
            return -1;
        } else {
            var problemContentLoader = new ProblemContentLoader();
            var solution = IOHelper.read(solutionFilePath);
            var problem = problemContentLoader.load(year, round, dataset);

            try {
                Class<?> clazz = Class.forName("google.hashcode." + round + year + ".evaluation.ScoreCalculator");
                Object scoreCalculator = clazz
                        .getConstructor(List.class, List.class)
                        .newInstance(problem, solution);
                var score = clazz.getDeclaredMethod("getScore").invoke(scoreCalculator);
                System.out.println("score: " + score);
                return 0;
            } catch (ClassNotFoundException | InvocationTargetException | InstantiationException |
                     IllegalAccessException | NoSuchMethodException e) {
                return -1;
            }
        }
    }
}
