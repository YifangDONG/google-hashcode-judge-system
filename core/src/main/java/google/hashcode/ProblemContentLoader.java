package google.hashcode;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

public class ProblemContentLoader {

    public static List<List<String>> load(int year, Round round, DataSet dataSet) {
        return read(String.format("%d/%s/%s", year, round, dataSet));
    }

    private static List<List<String>> read(String filePath) {
        try {
            var resource = ProblemContentLoader.class.getClassLoader().getResource(filePath);
            var file = new File(resource.toURI());
            return Files.lines(file.toPath())
                .filter(line -> !line.isBlank())
                .map(line -> List.of(line.split(" ")))
                .collect(Collectors.toList());
        } catch (IOException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
