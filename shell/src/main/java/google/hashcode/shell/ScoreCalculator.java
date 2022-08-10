package google.hashcode.shell;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

import google.hashcode.ProblemContentLoader;
import google.hashcode.ProblemMeta;
import picocli.CommandLine;

@CommandLine.Command(name = "score", mixinStandardHelpOptions = true,
        description = "This is a tool to calculate the score of google hashcode competition")
public class ScoreCalculator implements Callable<Integer> {

    private final List<ProblemMeta> supportedProblem;
    @CommandLine.ArgGroup(exclusive = false, heading = "To evaluate your solution%n")
    EvaluationArgs evaluationArgs;

    @CommandLine.ArgGroup(exclusive = false, heading = "To get information about the calculator%n")
    InformationArgs informationArgs;

    public ScoreCalculator(List<ProblemMeta> supportedProblem) {
        this.supportedProblem = supportedProblem;
    }

    private static Map<Integer, Map<String, Set<String>>> adaptSupportProblem(List<ProblemMeta> problems) {
        var supportedProblems = new HashMap<Integer, Map<String, Set<String>>>();
        for (ProblemMeta problem : problems) {
            supportedProblems.putIfAbsent(problem.year(), new HashMap<>());
            supportedProblems.get(problem.year()).put(problem.round(), problem.datasets());
        }
        return supportedProblems;
    }

    @Override
    public Integer call() {
        if (informationArgs.showSupportProblem) {
            System.out.println("The following contest are supported:");
            supportedProblem.forEach(problemMeta ->
                    System.out.printf("-year=%s -round=%s -dataset=[%s]\n",
                            problemMeta.year(),
                            problemMeta.round(),
                            String.join(" | ", problemMeta.datasets())));
            return 0;
        }

        var indexedSupportedProblem = adaptSupportProblem(supportedProblem);
        int year = evaluationArgs.year;
        String round = evaluationArgs.round;
        String dataset = evaluationArgs.dataset;
        String solutionFilePath = evaluationArgs.solutionFilePath;

        if (indexedSupportedProblem.get(year) == null) {
            System.out.printf("doesn't support year %d \n current supported years are: %s",
                    year, indexedSupportedProblem.keySet()
            );
            return -1;
        } else if (indexedSupportedProblem.get(year).get(round) == null) {
            System.out.printf("doesn't support round %s in year %d \n current supported round are: %s",
                    round, year, indexedSupportedProblem.get(year).keySet()
            );
            return -1;
        } else if (!indexedSupportedProblem.get(year).get(round).contains(dataset)) {
            System.out.printf("doesn't support dataset %s in %s \n current supported datasets are: %s",
                    dataset, year + round, indexedSupportedProblem.get(year).get(round)
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

    static class EvaluationArgs {
        @CommandLine.Option(names = {"-y", "-year"}, description = "set competition year", required = true)
        private int year;
        @CommandLine.Option(names = {"-r", "-round"}, description = "set competition round", required = true)
        private String round;
        @CommandLine.Option(names = {"-d", "-dataset"}, description = "set competition dataset", required = true)
        private String dataset;
        @CommandLine.Option(names = {"-f", "-file"}, description = "set the solution's file path", required = true)
        private String solutionFilePath;
    }

    static class InformationArgs {
        @CommandLine.Option(names = {"-l", "-list"}, description = "list the supported competitions", required = true)
        private boolean showSupportProblem;
    }
}
