package se.kth.iv1350.posWithExceptions.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.util.TimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ErrorMessageHandlerTest {
    private ByteArrayOutputStream outputContent;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        originalSystemOut = System.out;
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @AfterEach
    void tearDown() {
        outputContent = null;
        System.setOut(originalSystemOut);
    }

    @Test
    void testShowErrorMessage() {
        ErrorMessageHandler errorMessageHandler = new ErrorMessageHandler();
        String errorMessage = "Test error message";
        errorMessageHandler.showErrorMessage(errorMessage);
        String expectedResult = TimeUtil.getLocalizedTimeNow() + " ERROR: " + errorMessage;
        String result = outputContent.toString();
        assertTrue(result.contains(expectedResult), "Wrong error printout.");
    }
}