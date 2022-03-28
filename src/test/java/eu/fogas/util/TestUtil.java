package eu.fogas.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
public class TestUtil {

    public static void assertEqualsIgnoreLineEndings(String expected, String actual) {
        assertEquals(replaceAllLineEndings(expected), replaceAllLineEndings(actual));
    }

    public static String replaceAllLineEndings(String text) {
        return replaceAllLineEndings(text, System.lineSeparator());
    }

    public static String replaceAllLineEndings(String text, String newLineSeparator) {
        if (text == null) {
            return null;
        }
        return text.replaceAll("(\r\n|\r|\n)", newLineSeparator);
    }

    public static String readFileAsString(String fileName) throws IOException, URISyntaxException {
        Path path = Path.of(TestUtil.class.getClassLoader().getResource(fileName).toURI());
        return Files.readString(path);
    }

    public static InputStream readFileAsStream(String fileName) {
        return TestUtil.class.getClassLoader().getResourceAsStream(fileName);
    }
}
