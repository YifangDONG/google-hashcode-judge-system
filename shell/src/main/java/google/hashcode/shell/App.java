package google.hashcode.shell;

import google.hashcode.ProblemContentLoader;
import google.hashcode.ProblemMeta;
import picocli.CommandLine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class App {

    public static void main(String[] args) {
        var problemContentLoader = new ProblemContentLoader();
        var supportedProblem = adaptSupportProblem(problemContentLoader.getSupportedProblem());
        int exitCode = new CommandLine(new ScoreCalculator(supportedProblem))
            .execute(args);
        System.exit(exitCode);
    }

    private static Map<Integer, Map<String, Set<String>>> adaptSupportProblem(List<ProblemMeta> problems) {
        var supportedProblems = new HashMap<Integer, Map<String, Set<String>>>();
        for (ProblemMeta problem : problems) {
            supportedProblems.putIfAbsent(problem.year(), new HashMap<>());
            supportedProblems.get(problem.year()).put(problem.round(), problem.datasets());
        }
        return supportedProblems;
    }
}
