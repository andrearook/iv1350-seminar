package se.kth.iv1350.posWithExceptions.startup;

import se.kth.iv1350.posWithExceptions.controller.Controller;
import se.kth.iv1350.posWithExceptions.integration.Printer;
import se.kth.iv1350.posWithExceptions.integration.SystemCreator;
import se.kth.iv1350.posWithExceptions.view.View;

import java.io.IOException;

/**
 * This is where the application starts, by the main method.
 */
public class Main {

    /**
     * This is the main method which will start the application.
     *
     * @param args The application does not take any command line parameters.
     */
    public static void main(String[] args) {
        try {
            SystemCreator creator = new SystemCreator();
            Printer printer = new Printer();
            Controller controller = new Controller(creator, printer);
            View view = new View(controller);
            view.runFakeExecution();
        } catch (IOException exc) {
            System.out.println("Unable to run the application.");
            exc.printStackTrace();
        }
    }
}
