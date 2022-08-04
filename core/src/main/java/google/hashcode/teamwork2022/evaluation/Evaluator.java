package google.hashcode.teamwork2022.evaluation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Table;
import google.hashcode.teamwork2022.model.Assignment;
import google.hashcode.teamwork2022.model.Problem;
import google.hashcode.teamwork2022.model.Skill;

public class Evaluator {

    private final ProblemUtil util;

    public Evaluator(Problem problem) {
        this.util = new ProblemUtil(problem);
    }

    public SolutionInside getResultInside(List<Assignment> assignments) {
        int score = 0;
        int nProjectFullScore = 0;
        int nZeroScoreProject = 0;
        int beMentoredTimes = 0;
        int increaseSkillTimes = 0;
        var contributorWorkingDays = new HashMap<String, List<WorkingDuration>>();

        var skillContributorLevel = util.skillContributorLevel();
        var contributors = util.contributors().keySet();
        var nameToProject = util.projects();
        Map<String, Integer> contributorAvailableDay = initContributorAvailableDay(contributors);
        var contributed = new HashSet<String>();

        for (Assignment assignment : assignments) {
            checkContributorUnique(assignment);
            var assignedContributors = assignment.contributors();
            contributed.addAll(assignedContributors);

            var project = nameToProject.get(assignment.projectName());
            var needSkills = project.skills();
            beMentoredTimes += skillCheck(assignedContributors, needSkills, skillContributorLevel);

            var startDay = getStartDay(assignedContributors, contributorAvailableDay);

            var days = project.duration();
            var endDays = startDay + days;

            for (String contributor : assignedContributors) {
                var workingDays = contributorWorkingDays.getOrDefault(contributor, new ArrayList<>());
                workingDays.add(new WorkingDuration(startDay, endDays));
                contributorWorkingDays.put(contributor, workingDays);
            }

            // update the contributor available days
            updateContributorAvailableDay(contributorAvailableDay, assignedContributors, endDays);

            // update skill level
            increaseSkillTimes += updateSkill(assignedContributors, needSkills, skillContributorLevel);

            // add score
            var thisReward = Math.max(0, project.reward() - Math.max(0, endDays - project.bestBeforeDays()));
            score += thisReward;
            if (thisReward == project.reward()) {
                nProjectFullScore++;
            } else if (thisReward == 0) {
                nZeroScoreProject++;
            }
        }

        return new SolutionInside(
            score,
            assignments.size(),
            nProjectFullScore,
            nZeroScoreProject,
            beMentoredTimes,
            increaseSkillTimes,
            waitingAverage(contributorWorkingDays),
            contributed.size());
    }

    private static Map<String, Integer> initContributorAvailableDay(Set<String> contributorNames) {
        Map<String, Integer> freeDays = new HashMap<>();
        for (String name : contributorNames) {
            freeDays.put(name, 0);
        }
        return freeDays;
    }

    private static int updateSkill(List<String> assignedContributors, List<Skill> skillsNeeds,
        Table<String, String, Integer> skillContributorLevel) {
        int increaseSkillTimes = 0;
        var size = assignedContributors.size();
        for (int i = 0; i < size; i++) {
            var skill = skillsNeeds.get(i);
            var contributor = assignedContributors.get(i);
            int level = Optional.ofNullable(skillContributorLevel.get(skill.name(), contributor)).orElse(0);
            if (level == skill.level() || level == skill.level() - 1) {
                skillContributorLevel.put(skill.name(), contributor, level + 1);
                increaseSkillTimes++;
            }
        }
        return increaseSkillTimes;
    }

    private static void checkContributorUnique(Assignment assignment) {
        var assignedContributors = assignment.contributors();
        if (assignedContributors.size() != new HashSet<>(assignedContributors).size()) {
            throw new IllegalArgumentException(String.format("project %s has assign one person to more than one task",
                assignment.projectName()));
        }
    }

    private static int skillCheck(List<String> assignedContributors, List<Skill> needSkills,
        Table<String, String, Integer> skillContributorLevel) {

        var assigned = new HashSet<>(assignedContributors);
        if (assignedContributors.size() != needSkills.size()) {
            throw new IllegalArgumentException("contributors size not match");
        }
        var mentorSkills = new HashSet<String>();
        for (Skill skill : needSkills) {
            var canMentor = skillContributorLevel.row(skill.name())
                .entrySet().stream()
                .anyMatch(contributorToSkillLevel -> assigned.contains(contributorToSkillLevel.getKey())
                                                     && contributorToSkillLevel.getValue() >= skill.level());
            if (canMentor) {
                mentorSkills.add(skill.name());
            }
        }
        int mentorTimes = 0;
        for (int i = 0; i < assignedContributors.size(); i++) {
            var skill = needSkills.get(i);
            var contributor = assignedContributors.get(i);
            int currentLevel = Optional.ofNullable(skillContributorLevel.get(skill.name(), contributor)).orElse(0);

            if (currentLevel >= skill.level()) {
                // do nothing
            } else if (currentLevel + 1 == skill.level()) {
                // mentor
                if (mentorSkills.contains(skill.name())) {
                    mentorTimes++;
                } else {
                    throw new IllegalArgumentException("cannot mentor");
                }
            } else {
                throw new IllegalArgumentException("skill not match");
            }
        }
        return mentorTimes;
    }

    private static int getStartDay(List<String> assignedContributors, Map<String, Integer> contributorFreeDays) {
        return maxDays(assignedContributors, contributorFreeDays);
    }

    private static int maxDays(List<String> assignedContributors, Map<String, Integer> contributorFreeDays) {
        return assignedContributors.stream()
            .map(contributorFreeDays::get)
            .mapToInt(Integer::intValue)
            .max()
            .getAsInt();
    }

    private static void updateContributorAvailableDay(Map<String, Integer> contributorFreeDays, List<String> assignedContributors,
        int endDays) {
        for (String contributor : assignedContributors) {
            contributorFreeDays.put(contributor, endDays);
        }
    }

    private static double waitingAverage(HashMap<String, List<WorkingDuration>> waitingTime) {
        int sum = 0;
        int n = 0;
        for (Map.Entry<String, List<WorkingDuration>> e : waitingTime.entrySet()) {
            var waiting = e.getValue();
            // waiting to start the first project
            sum += waiting.get(0).start();
            n++;
            for (int i = 1; i < waiting.size(); i++) {
                // waiting to start the following project
                sum += waiting.get(i).start() - waiting.get(i - 1).end();
                n++;
            }
        }
        return 1.0 * sum / n;
    }

    private record WorkingDuration(int start, int end) {
    }
}
