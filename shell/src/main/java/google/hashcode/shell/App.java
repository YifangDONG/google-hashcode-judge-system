package google.hashcode.shell;

import google.hashcode.ProblemContentLoader;
import picocli.CommandLine;

public class App {

    public static void main(String[] args) {
        var problemContentLoader = new ProblemContentLoader();
        int exitCode = new CommandLine(new ScoreCalculator(problemContentLoader.getSupportedProblem()))
            .execute(args);
        System.exit(exitCode);
    }
}
