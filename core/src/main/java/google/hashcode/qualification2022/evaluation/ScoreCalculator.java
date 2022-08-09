package google.hashcode.qualification2022.evaluation;

import google.hashcode.IScoreCalculator;

import java.util.List;

public class ScoreCalculator implements IScoreCalculator {
    private final List<List<String>> problemContent;
    private final List<List<String>> solutionContent;

    public ScoreCalculator(List<List<String>> problemContent, List<List<String>> solutionContent) {
        this.problemContent = problemContent;
        this.solutionContent = solutionContent;
    }

    @Override
    public long getScore() {
        var problemAdapter = new ProblemAdapter(problemContent);
        var solutionAdapter = new SolutionAdapter(solutionContent);
        var evaluator = new Evaluator(problemAdapter.getProblem());
        return evaluator.getResultInside(solutionAdapter.getAssignments()).score();
    }
}
