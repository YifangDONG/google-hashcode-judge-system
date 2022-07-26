package google.hashcode.teamwork2022.evaluation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import google.hashcode.teamwork2022.model.Contributor;
import google.hashcode.teamwork2022.model.Problem;
import google.hashcode.teamwork2022.model.Project;

public class ProblemUtil {

    private final Problem problem;

    public ProblemUtil(Problem problem) {
        this.problem = problem;
    }

    public Map<String, Contributor> contributors() {
        return problem.contributors().stream().collect(Collectors.toMap(Contributor::name, Function.identity()));
    }

    public Map<String, Project> projects() {
        return problem.projects().stream().collect(Collectors.toMap(Project::name, Function.identity()));
    }

    public Table<String, String, Integer> skillContributorLevel() {
        Table<String, String, Integer> table = HashBasedTable.create();
        var contributors = problem.contributors();
        for (Contributor p : contributors) {
            p.skills().forEach((type, skill) -> table.put(type, p.name(), skill.level()));
        }
        return table;
    }

    public Map<String, String> skillMaster() {
        var skillMaster = new HashMap<String, String>();
        var contributors = contributors();
        var skillToContributors = skillToPersons();
        for (Map.Entry<String, List<String>> skillPersons : skillToContributors.entrySet()) {
            var persons = skillPersons.getValue();
            var skillName = skillPersons.getKey();
            var master = persons
                .stream()
                .map(contributors::get)
                .sorted(Comparator.<Contributor>comparingInt(contributor -> contributor.skills().get(skillName).level()).reversed())
                .toList()
                .get(0)
                .name();
            skillMaster.put(skillName, master);
        }
        return skillMaster;
    }

    public Map<String, List<String>> skillToPersons() {
        var skillToPerson = new HashMap<String, List<String>>();
        var contributors = problem.contributors();
        for (Contributor p : contributors) {
            var skills = p.skills().keySet();
            for (String skill : skills) {
                var updated = skillToPerson.getOrDefault(skill, new ArrayList<>());
                updated.add(p.name());
                skillToPerson.put(skill, updated);
            }
        }
        return skillToPerson;
    }
}
