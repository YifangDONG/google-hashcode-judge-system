package utils;

import java.util.List;

import google.hashcode.shell.IOHelper;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class IOHelperTest {

    @Test
    public void givenAFilePath_whenReadingContent_thenReturnContentFilterOutEmptyLine() {
        List<List<String>> content = IOHelper.read("src/test/resources/io.text");
        List<List<String>> expected = List.of(List.of("this"), List.of("is", "a", "test"));
        assertEquals(expected, content);
    }
}