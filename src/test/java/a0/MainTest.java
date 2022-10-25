package a0;

import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainTest {
    private static final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private static final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final PrintStream originalOut = System.out;
    private static final PrintStream originalErr = System.err;

    @BeforeAll
    static void setup() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterAll
    static void teardown() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    /**
     * Tests that Main.main prints "Hello World!" to stdout.
     */
    @Test
    void testMain() {
        Main.main(null);
        assertEquals("Hello World!", outContent.toString());
    }

    /**
     * Tests that instantiating our class doesn't throw an exception.
     */
    @Test
    void testInstantiate() {
        new Main();
    }
}
