package se.kth.iv1350.pos.controller;

import se.kth.iv1350.pos.integration.*;
import se.kth.iv1350.pos.model.*;

/**
 * This is the only controller in the application, all calls to the model and integration layers pass through an
 * instance of this class.
 */
public class Controller {
    private Inventory inventory;
    private Accounting accounting;
    private SaleLog saleLog;
    private Printer printer;
    private CashRegister cashRegister;
    private Sale sale;

    /**
     * Creates an instance of the only controller in the application, all calls to the model and integration layers
     * will pass through method calls in this controller.
     *
     * @param creator The system creator that is used to access representatives of external systems.
     * @param printer The representative of the receipt printer.
     */
    public Controller(SystemCreator creator, Printer printer) {
        inventory = creator.getInventory();
        accounting = creator.getAccounting();
        saleLog = creator.getSaleLog();
        this.printer = printer;
        cashRegister = new CashRegister();
    }

    /**
     * Starts a new sale. This method must be called first in the sales process.
     */
    public void startNewSale() {
        sale = new Sale();
    }

    /**
     * Enter an item to add to the current sale.
     *
     * @param itemID This is the identifier for the item to be added.
     * @return an <code>InfoToPresentDTO </code> containing the information to present regarding the added item and
     * the current total price including VAT of the sale.
     */
    public InfoToPresentDTO enterItem(int itemID) {
        ItemDTO hasItem = sale.findItem(itemID);
        InfoToPresentDTO info;

        if (hasItem != null) {
            sale.addItem(hasItem);
            info = new InfoToPresentDTO(hasItem.getItemDescription(), hasItem.getItemPrice(),
                    sale.getTotInclVat());
        }
        else {
            ItemDTO itemInfo = inventory.getItemInfo(itemID);
            sale.addItem(itemInfo);
            info = new InfoToPresentDTO(itemInfo.getItemDescription(), itemInfo.getItemPrice(),
                    sale.getTotInclVat());
        }
        return info;
    }

    /**
     * This method is to be called when all items has been added, to end the sale.
     *
     * @return an <code>Amount</code> representing the total price of the sale, including VAT.
     */
    public Amount endSale() {
        return sale.endSale();
    }

    /**
     * This method will register the <code>Amount</code> of money the customer paid and by that the sale can be
     * considered completed. The return value will be the <code>Amount</code> of change.
     *
     * @param paidAmount This is the <code>Amount</code> of money the customer paid.
     * @return the <code>Amount</code> of change for the customer.
     */
    public Amount enterAmountPaid(Amount paidAmount) {
        CashPayment payment = new CashPayment(paidAmount);
        Amount change = sale.finishSale(payment, printer);

        cashRegister.updateBalance(payment, change);

        SaleDTO saleInfo = sale.getSaleInfo();
        saleLog.logFinishedSale(saleInfo);
        inventory.updateInventory(saleInfo);
        accounting.updateAccounting(saleInfo);

        return change;
    }
}
