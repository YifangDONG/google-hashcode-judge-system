package google.hashcode.shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class IOHelper {

    public static List<List<String>> read(String filePath) {
        try {
            return Files.lines(Path.of(filePath))
                .filter(line -> !line.isBlank())
                .map(line -> List.of(line.split(" ")))
                .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
