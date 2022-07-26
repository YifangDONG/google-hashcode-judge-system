package google.hashcode.teamwork2022.model;

import java.util.List;

public record Assignment(String projectName, List<String> contributors) {

    public Assignment(List<List<String>> content) {
        this(content.get(0).get(0), content.get(1));
    }
}
