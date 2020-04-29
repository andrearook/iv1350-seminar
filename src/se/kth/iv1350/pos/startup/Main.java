package se.kth.iv1350.pos.startup;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.integration.Printer;
import se.kth.iv1350.pos.integration.SystemCreator;
import se.kth.iv1350.pos.view.View;

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
        SystemCreator creator = new SystemCreator();
        Printer printer = new Printer();
        Controller controller = new Controller(creator, printer);
        View view = new View(controller);
        view.runFakeExecution();
    }
}
