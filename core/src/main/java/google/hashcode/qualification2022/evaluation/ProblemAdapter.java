package google.hashcode.qualification2022.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import google.hashcode.qualification2022.model.Contributor;
import google.hashcode.qualification2022.model.Problem;
import google.hashcode.qualification2022.model.Project;
import google.hashcode.qualification2022.model.Skill;

class ProblemAdapter {

    private final List<List<String>> content;

    public ProblemAdapter(List<List<String>> content) {
        this.content = content;
    }

    public Problem getProblem() {
        return new Problem(nContributors(), getContributors(), nProjects(), getProjects());
    }

    private int nContributors() {
        return Integer.parseInt(content.get(0).get(0));
    }

    private int nProjects() {
        return Integer.parseInt(content.get(0).get(1));
    }

    private List<Contributor> getContributors() {
        var people = new ArrayList<Contributor>();
        int l = 1;
        for (int i = 0; i < nContributors(); i++) {
            var name = content.get(l).get(0);
            var nSkills = Integer.parseInt(content.get(l).get(1));
            var skills = new HashMap<String, Skill>();
            for (int j = 1; j < nSkills + 1; j++) {
                var type = content.get(l + j).get(0);
                var level = Integer.parseInt(content.get(l + j).get(1));
                skills.put(type, new Skill(type, level));
            }
            l = l + 1 + nSkills;
            people.add(new Contributor(name, skills));
        }
        return people;
    }

    private List<Project> getProjects() {
        var projects = new ArrayList<Project>();
        var l = 1 + getContributors()
            .stream()
            .map(contributor -> contributor.skills().size() + 1)
            .mapToInt(Integer::intValue)
            .sum();
        for (int i = 0; i < nProjects(); i++) {
            var projectDis = content.get(l);
            var name = projectDis.get(0);
            var duration = Integer.parseInt(projectDis.get(1));
            var score = Integer.parseInt(projectDis.get(2));
            var bestBeforeDays = Integer.parseInt(projectDis.get(3));
            var nSkills = Integer.parseInt(projectDis.get(4));
            var skills = new ArrayList<Skill>();
            l += 1;
            for (int j = 0; j < nSkills; j++) {
                var skillDis = content.get(l++);
                skills.add(new Skill(skillDis.get(0), Integer.parseInt(skillDis.get(1))));
            }
            projects.add(new Project(name, duration, score, bestBeforeDays, skills));
        }
        return projects;
    }
}
