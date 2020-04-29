package se.kth.iv1350.pos.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.integration.Printer;
import se.kth.iv1350.pos.integration.SystemCreator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private SystemCreator creator;
    private ByteArrayOutputStream outputContent;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        originalSystemOut = System.out;
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        Printer printer = new Printer();
        creator = new SystemCreator();
        controller = new Controller(creator, printer);
    }

    @AfterEach
    void tearDown() {
        outputContent = null;
        System.setOut(originalSystemOut);
        creator = null;
        controller = null;
    }

    @Test
    void testStartNewSale() {
    }

    @Test
    void testEnterItem() {
    }

    @Test
    void testEndSale() {
    }

    @Test
    void testEnterAmountPaid() {
    }
}