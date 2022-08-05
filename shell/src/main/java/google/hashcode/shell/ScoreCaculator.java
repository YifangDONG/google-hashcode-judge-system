package google.hashcode.shell;

import java.util.concurrent.Callable;

import google.hashcode.DataSet;
import google.hashcode.ProblemContentLoader;
import google.hashcode.Round;
import google.hashcode.teamwork2022.evaluation.Evaluator;
import google.hashcode.teamwork2022.evaluation.ProblemAdapter;
import google.hashcode.teamwork2022.evaluation.SolutionAdapter;
import picocli.CommandLine;

@CommandLine.Command(name = "evaluate", mixinStandardHelpOptions = true,
    description = "calculate score of google hashcode competition")
public class ScoreCaculator implements Callable<Integer> {

    @CommandLine.Option(names = {"-y", "-year"}, description = "")
    private int year;

    @CommandLine.Option(names = {"-r", "-round"}, description = "")
    private String round;

    @CommandLine.Option(names = {"-d", "-dataset"}, description = "")
    private String dataset;

    @CommandLine.Option(names = {"-f", "-file"}, description = "")
    private String solutionFilePath;

    @Override
    public Integer call() throws Exception {
        var solution = IOHelper.read(solutionFilePath);
        var problem = ProblemContentLoader.load(year, Round.valueOf(round), DataSet.valueOf(dataset));
        var problemAdapter = new ProblemAdapter(problem);
        var solutionAdapter = new SolutionAdapter(solution);
        var evaluator = new Evaluator(problemAdapter.getProblem());
        var resultInside = evaluator.getResultInside(solutionAdapter.getAssignments());
        System.out.println("score: " + resultInside.score());
        return 0;
    }
}
