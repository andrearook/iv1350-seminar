package se.kth.iv1350.pos.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.Printer;
import se.kth.iv1350.pos.integration.SystemCreator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ViewTest {
    private View viewToTest;
    private ByteArrayOutputStream outputBuffer;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        SystemCreator creator = new SystemCreator();
        Printer printer = new Printer();
        Controller controller = new Controller(creator, printer);
        viewToTest = new View(controller);
        outputBuffer = new ByteArrayOutputStream();
        PrintStream inMemorySystemOut = new PrintStream(outputBuffer);
        originalSystemOut = System.out;
        System.setOut(inMemorySystemOut);
    }

    @AfterEach
    void tearDown() {
        viewToTest = null;
        outputBuffer = null;
        System.setOut(originalSystemOut);
    }

    @Test
    void testRunFakeExecution() {
        viewToTest.runFakeExecution();
        String printout = outputBuffer.toString();
        String expectedOutput = "started";
        assertTrue(printout.contains(expectedOutput), "User interface did not start correctly.");
    }
}