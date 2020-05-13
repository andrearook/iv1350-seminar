package se.kth.iv1350.posWithExceptions.startup;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class MainTest {
    private Main mainToTest;
    private ByteArrayOutputStream outputBuffer;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        mainToTest = new Main();
        outputBuffer = new ByteArrayOutputStream();
        PrintStream inMemorySystemOut = new PrintStream(outputBuffer);
        originalSystemOut = System.out;
        System.setOut(inMemorySystemOut);
    }

    @AfterEach
    void tearDown() {
        mainToTest = null;
        outputBuffer = null;
        System.setOut(originalSystemOut);
    }

    @Test
    void testUserInterfaceHasStarted() {
        String[] args = null;
        Main.main(args);
        String output = outputBuffer.toString();
        String expectedOutput = "started";
        assertTrue(output.contains(expectedOutput), "User interface did not start correctly.");
    }
}