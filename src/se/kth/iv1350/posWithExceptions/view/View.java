package se.kth.iv1350.posWithExceptions.view;

import se.kth.iv1350.posWithExceptions.controller.Controller;
import se.kth.iv1350.posWithExceptions.controller.OperationFailedException;
import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.InfoToPresentDTO;
import se.kth.iv1350.posWithExceptions.model.ItemNotFoundException;
import se.kth.iv1350.posWithExceptions.util.Logger;

import java.io.IOException;

/**
 * This is the placeholder for the real view. It contains a hardcoded execution with calls to all the system operations
 * in the controller.
 */
public class View {
    private Controller controller;
    private ErrorMessageHandler errorMessageHandler = new ErrorMessageHandler();
    private Logger logger;

    /**
     * Creates a new instance, that uses the specified controller for calls to other layers in the application.
     *
     * @param controller The controller to use for calls to other layers in the application.
     * @throws IOException When unable to create <code>FileTotalRevenueView</code>.
     */
    public View(Controller controller) throws IOException {
        this.controller = controller;
        this.logger = new Logger();
        controller.addSaleObserver(new ConsoleTotalRevenueView());
        controller.addSaleObserver(new FileTotalRevenueView());
    }

    /**
     * Performs a hardcoded execution of a sale, by calling all system operations in the controller.
     */
    public void runFakeExecution() {
        try {
            controller.startNewSale();
            System.out.println("A new sale has been started. \n");

            int itemIDFirstItem = 111;
            InfoToPresentDTO infoFirstItem = controller.enterItem(itemIDFirstItem);
            System.out.println("An item has been added.");
            System.out.println(infoFirstItem);

            int itemIDSecondItem = 111;
            InfoToPresentDTO infoSecondItem = controller.enterItem(itemIDSecondItem);
            System.out.println("An item has been added.");
            System.out.println(infoSecondItem);

            int itemIDThirdItem = 222;
            InfoToPresentDTO infoThirdItem = controller.enterItem(itemIDThirdItem);
            System.out.println("An item has been added.");
            System.out.println(infoThirdItem);

            try {
                int nonExistingID = 555;
                System.out.println("Trying to add an identifier that does not exist in inventory, " +
                        "should lead to an error.");
                controller.enterItem(nonExistingID);
                errorMessageHandler.showErrorMessage("Managed to add an item with invalid identifier.");
            } catch (ItemNotFoundException exc) {
                handleException("Item with specified identifier " + exc.getNotFoundIdentifier() +
                        " could not be found.", exc, false);
            } catch (OperationFailedException exc) {
                handleException("Wrong exception was thrown when identifier could not be found.", exc, true);
            }

            try {
                int errorGeneratingID = 666;
                System.out.println("\nTrying to add an item when the database is down, " +
                        "should lead to an error.");
                controller.enterItem(errorGeneratingID);
                errorMessageHandler.showErrorMessage("Managed to add an item when database is down.");
            } catch (OperationFailedException exc) {
                handleException("Failed to add item. Database is down.", exc, true);
            } catch (ItemNotFoundException exc) {
                handleException("Wrong exception was thrown when database was down.", exc, true);
            }

            Amount totalPrice = controller.endSale();
            System.out.println("\nThe sale has been ended.\nTotal price including VAT is: " + totalPrice + "\n");

            System.out.println("- RECEIPT IS PRINTED -\n");
            Amount extraFromCustomer = new Amount((20));
            Amount paidAmount = totalPrice.add(extraFromCustomer);
            Amount change = controller.enterAmountPaid(paidAmount);

            System.out.println("\nChange to give to customer: " + change);

            System.out.println("\n\nRunning a second sale.");
            controller.startNewSale();
            System.out.println("A new sale has been started. \n");

            itemIDFirstItem = 444;
            infoFirstItem = controller.enterItem(itemIDFirstItem);
            System.out.println("An item has been added.");
            System.out.println(infoFirstItem);

            itemIDSecondItem = 333;
            infoSecondItem = controller.enterItem(itemIDSecondItem);
            System.out.println("An item has been added.");
            System.out.println(infoSecondItem);

            totalPrice = controller.endSale();
            System.out.println("\nThe sale has been ended.\nTotal price including VAT is: " + totalPrice + "\n");

            System.out.println("- RECEIPT IS PRINTED -\n");
            extraFromCustomer = new Amount((15));
            paidAmount = totalPrice.add(extraFromCustomer);
            change = controller.enterAmountPaid(paidAmount);

            System.out.println("\nChange to give to customer: " + change);

        } catch (ItemNotFoundException exc) {
            handleException("The item could not be found.", exc, false);
        } catch (Exception exc) {
            handleException("Failed to complete the sale. Please try again.", exc, true);
        }
    }

    private void handleException(String uiMessage, Exception exc, boolean shouldLog) {
        errorMessageHandler.showErrorMessage(uiMessage);
        if (shouldLog) {
            logger.logException(exc);
        }
    }
}
