package se.kth.iv1350.posWithExceptions.controller;

import se.kth.iv1350.posWithExceptions.integration.*;
import se.kth.iv1350.posWithExceptions.model.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<SaleObserver> saleObservers = new ArrayList<>();

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
     *
     * @throws IllegalStateException if this method is called during an ongoing sale. The sale is ended when
     *                               <code>enterAmountPaid</code> is called.
     */
    public void startNewSale() {
        if (sale != null) {
            throw new IllegalStateException("Call to startNewSale before a sale had been finished.");
        }
        sale = new Sale();
        sale.addSaleObservers(saleObservers);
    }

    /**
     * Enter an item to add to the current sale.
     *
     * @param itemID This is the identifier for the item to be added.
     * @return an <code>InfoToPresentDTO</code> containing the information to present regarding the added item and
     * the current total price including VAT of the sale.
     * @throws ItemNotFoundException    if an item with the specified identifier could not be found in the inventory.
     * @throws OperationFailedException if unable to enter an item due to another reason than that the identifier could
     *                                  not be found in the inventory.
     * @throws IllegalStateException    if this method is called before <code>startNewSale</code>.
     */
    public InfoToPresentDTO enterItem(int itemID) throws ItemNotFoundException, OperationFailedException {
        if (sale == null) {
            throw new IllegalStateException("Call to enterItem before a sale had been started.");
        }
        ItemDTO item = sale.findItem(itemID);
        if (item == null) {
            item = fetchItem(itemID);
        }
        return handleItem(item);
    }

    private ItemDTO fetchItem(int itemID) throws ItemNotFoundException, OperationFailedException {
        try {
            ItemDTO itemInfo = inventory.getItemInfo(itemID);
            if (itemInfo == null) {
                throw new ItemNotFoundException(itemID);
            }
            return itemInfo;

        } catch (NoDatabaseConnectionException exc) {
            throw new OperationFailedException("Could not receive item information.", exc);
        }
    }

    private InfoToPresentDTO handleItem(ItemDTO item) {
        sale.addItem(item);
        return new InfoToPresentDTO(item.getItemDescription(), item.getItemPrice(), sale.getTotInclVat());
    }

    /**
     * This method is to be called when all items has been added, to end the sale.
     *
     * @return an <code>Amount</code> representing the total price of the sale, including VAT.
     * @throws IllegalStateException if this method is called before <code>startNewSale</code>.
     */
    public Amount endSale() {
        if (sale == null) {
            throw new IllegalStateException("Call to endSale before a sale had been started.");
        }
        return sale.endSale();
    }

    /**
     * This method will register the <code>Amount</code> of money the customer paid and by that the sale can be
     * considered completed. The return value will be the <code>Amount</code> of change.
     *
     * @param paidAmount The <code>Amount</code> of money the customer paid.
     * @return the <code>Amount</code> of change for the customer.
     * @throws IllegalStateException if this method is called before <code>startNewSale</code>.
     */
    public Amount enterAmountPaid(Amount paidAmount) {
        if (sale == null) {
            throw new IllegalStateException("Call to enterAmountPaid before a sale had been started.");
        }
        CashPayment payment = new CashPayment(paidAmount);
        Amount change = sale.finishSale(payment, printer);

        cashRegister.updateBalance(payment, change);

        SaleDTO saleInfo = sale.getSaleInfo();
        saleLog.logFinishedSale(saleInfo);
        inventory.updateInventory(saleInfo);
        accounting.updateAccounting(saleInfo);

        sale = null;

        return change;
    }

    /**
     * This method will add a new <code>SaleObserver</code> to the Controller, which will then be added to
     * the next <code>Sale</code> that is started.
     *
     * @param saleObserver The <code>SaleObserver</code> to add.
     */
    public void addSaleObserver(SaleObserver saleObserver) {
        saleObservers.add(saleObserver);
    }
}
