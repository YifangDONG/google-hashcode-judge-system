package google.hashcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProblemContentLoader {

    private enum DataSet {
        a, b, c, d, e, f
    }

    private enum Round {
        practice, qualification, worldFinal
    }

    public ProblemContentLoader() {
    }

    public List<ProblemMeta> getSupportedProblem() {
        return List.of(
            new ProblemMeta(2021, Round.qualification.name(), Set.of(DataSet.a.name(), DataSet.b.name(), DataSet.c.name(), DataSet.d.name(), DataSet.e.name(), DataSet.f.name())),
            new ProblemMeta(2022, Round.qualification.name(), Set.of(DataSet.a.name(), DataSet.b.name(), DataSet.c.name(), DataSet.d.name(), DataSet.e.name(), DataSet.f.name())));
    }

    public List<List<String>> load(int year, String round, String dataSet) {
        return read(String.format("%d/%s/%s", year, round, dataSet));
    }

    private List<List<String>> read(String filePath) {
        try {
            var resource = this.getClass().getClassLoader().getResource(filePath);
            var file = new File(resource.toURI());
            return Files.lines(file.toPath()).filter(line -> !line.isBlank()).map(line -> List.of(line.split(" "))).collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
