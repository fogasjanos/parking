package eu.fogas.parking;

import eu.fogas.util.TestUtil;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.fail;

class AppTest {
    private static final String LOG4J2_CONFIGURATION_FILE = "log4j2.configurationFile";
    private static ByteArrayOutputStream newOut;
    private static PrintStream oldOut;
    private static InputStream oldIn;
    private static String oldProperty;

    @BeforeAll
    public static void beforeAll() {
        oldOut = System.out;
        oldIn = System.in;
        oldProperty = System.getProperty(LOG4J2_CONFIGURATION_FILE);
        newOut = new ByteArrayOutputStream();

        System.setOut(new PrintStream(newOut));
        System.setProperty(LOG4J2_CONFIGURATION_FILE, "log4j2-app.yaml");
    }

    @AfterAll
    public static void afterAll() {
        System.setOut(oldOut);
        System.setIn(oldIn);
        if (oldProperty == null) {
            System.clearProperty(LOG4J2_CONFIGURATION_FILE);
        } else {
            System.setProperty(LOG4J2_CONFIGURATION_FILE, oldProperty);
        }
    }

    @Test
    public void appShouldReturnExpectedOutput_whenGettingPreparedInput() {
        try (InputStream is = TestUtil.readFileAsStream("sample-input.txt")) {
            System.setIn(is);

            new App().start();

            String expected = TestUtil.readFileAsString("sample-output.txt");

            TestUtil.assertEqualsIgnoreLineEndings(expected, newOut.toString(StandardCharsets.UTF_8));
        } catch (IOException | URISyntaxException e) {
            fail(e.getMessage());
        }
    }
}
