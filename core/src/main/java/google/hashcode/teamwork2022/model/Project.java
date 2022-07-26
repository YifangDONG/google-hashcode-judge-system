package google.hashcode.teamwork2022.model;

import java.util.List;

public record Project(String name, int duration, int reward, int bestBeforeDays, List<Skill> skills) {
}
