package google.hashcode.teamwork2022.model;

import java.util.List;

public record Problem(int nContributors, List<Contributor> contributors, int nProject, List<Project> projects) {
}
