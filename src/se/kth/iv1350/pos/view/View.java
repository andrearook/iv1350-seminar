package se.kth.iv1350.pos.view;

import se.kth.iv1350.pos.controller.Controller;
import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.InfoToPresentDTO;

/**
 * This is the placeholder for the real view. It contains a hardcoded execution with calls to all the system operations
 * in the controller.
 */
public class View {
    private Controller controller;

    /**
     * Creates a new instance, that uses the specified controller for calls to other layers in the application.
     *
     * @param controller The controller to use for calls to other layers in the application.
     */
    public View(Controller controller) {
        this.controller = controller;
    }

    /**
     * Performs a hardcoded execution of a sale, by calling all system operations in the controller.
     */
    public void runFakeExecution() {
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

        Amount totalPrice = controller.endSale();
        System.out.println("The sale has been ended.\nTotal price including VAT is: " + totalPrice + "\n");

        System.out.println("- RECEIPT IS PRINTED -\n");
        Amount extraFromCustomer = new Amount((20));
        Amount paidAmount = totalPrice.add(extraFromCustomer);
        Amount change = controller.enterAmountPaid(paidAmount);
        System.out.println("\n- END OF RECEIPT -");

        System.out.println("\nChange to give to customer: " + change);
    }
}
