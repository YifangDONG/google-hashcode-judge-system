package google.hashcode.qualification2022.model;

import java.util.Map;

public record Contributor(String name, Map<String, Skill> skills) {
}
