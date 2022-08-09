package google.hashcode.qualification2022.evaluation;

import java.util.ArrayList;
import java.util.List;

import google.hashcode.qualification2022.model.Assignment;

class SolutionAdapter {
    private final List<List<String>> content;

    public SolutionAdapter(List<List<String>> content) {
        this.content = content;
    }

    public List<Assignment> getAssignments() {
        var assignments = new ArrayList<Assignment>();
        var nAssign = Integer.parseInt(content.get(0).get(0));
        for (int i = 1; i <= 2 * nAssign; i += 2) {
            assignments.add(new Assignment(content.subList(i, i + 2)));
        }
        return assignments;
    }
}
