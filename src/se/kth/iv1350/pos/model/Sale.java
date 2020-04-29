package se.kth.iv1350.pos.model;

import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.Printer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This represents one single sale made by one single customer and payed with one single payment.
 */
public class Sale {
    private LocalDateTime timeOfSale;
    private List<ItemDTO> itemsInSale = new ArrayList<>();

    /**
     * Creates a new instance and saves the time of the sale.
     */
    public Sale() {
        timeOfSale = LocalDateTime.now();
    }

    /**
     * Adds the specified item to the current sale.
     *
     * @param itemToAdd The specified item that will be added to the current sale.
     */
    public void addItem(ItemDTO itemToAdd) {
        itemsInSale.add(itemToAdd);
    }

    /**
     * Evaluates if a specified item is already added to the current sale.
     *
     * @param otherItemID The item identifier that will be used to evaluate if an item with the same identifier is
     *                    already added in the current sale.
     * @return if an item with the same identifier is found in the sale a copy of this item as an <code>ItemDTO</code>
     *         will be returned, otherwise <code>null</code> is returned.
     */
    public ItemDTO findItem(int otherItemID) {
        for (ItemDTO itemInSale : itemsInSale) {
            if (itemInSale.getItemID() == otherItemID) {
                return itemInSale.getCopy();
            }
        }
        return null;
    }

    /**
     * Calculates the total price of the items in the current sale including the cost for the VAT of every item.
     *
     * @return an <code>Amount</code> representing the total price of the current sale including the cost for the VAT.
     */
    public Amount getTotInclVat() {
        Amount totInclVat = new Amount();
        Amount itemPrice;
        Amount itemPriceInclVatCost;

        for (ItemDTO item : itemsInSale) {
            itemPrice = item.getItemPrice();
            itemPriceInclVatCost = itemPrice.includingVatCost(item.getVatRate());

            totInclVat = totInclVat.add(itemPriceInclVatCost);
        }
        return totInclVat;
    }

    /**
     * Calculates the total price of the items in the current sale.
     *
     * @return an <code>Amount</code> representing the total price of the current sale.
     */
    public Amount getTotExclVat() {
        Amount totExclVat = new Amount();
        Amount itemPrice;

        for (ItemDTO item : itemsInSale) {
            itemPrice = item.getItemPrice();
            totExclVat = totExclVat.add(itemPrice);
        }
        return totExclVat;
    }

    /**
     * Calculates the total VAT cost of the current sale.
     *
     * @return an <code>Amount</code> representing the total VAT cost of the current sale.
     */
    public Amount getTotVat() {
        return getTotInclVat().subtract(getTotExclVat());
    }

    /**
     * Ends the current sale and gives information about the total price of the entire sale including VAT.
     *
     * @return an <code>Amount</code> representing the total price of the current sale including the cost for the VAT.
     */
    public Amount endSale() {
        return getTotInclVat();
    }

    /**
     * Finishes the current sale by calculating the change and printing the receipt.
     *
     * @param payment The payment received from the customer used to pay for the sale and to calculate the change.
     * @param printer The representative of the printer used to print the receipt on.
     * @return the amount of change that shall be given to the customer.
     */
    public Amount finishSale(CashPayment payment, Printer printer) {
        Amount paidAmount = payment.getPaidAmount();
        Amount change = paidAmount.subtract(getTotInclVat());

        Receipt receipt = new Receipt(getSaleInfo(), paidAmount, change);
        printer.printReceipt(receipt);

        return change;
    }

    /**
     * Creates a SaleDTO representing the current sale.
     *
     * @return a SaleDTO with information about the current sale.
     */
    public SaleDTO getSaleInfo() {
        return new SaleDTO(timeOfSale, getTotInclVat(), getTotExclVat(), getTotVat(), itemsInSale);
    }
}
