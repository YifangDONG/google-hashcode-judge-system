package google.hashcode.teamwork2022.evaluation;

public record ResultInside(long score,
                           int nAssignments,
                           int nProjectFullScore,
                           int nZeroScoreProject,
                           int beMentoredTimes,
                           int increaseSkillTimes,
                           double waitingAverage,
                           int nContributors) {
}
