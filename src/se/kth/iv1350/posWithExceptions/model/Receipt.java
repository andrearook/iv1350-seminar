package se.kth.iv1350.posWithExceptions.model;

import se.kth.iv1350.posWithExceptions.integration.ItemDTO;

import java.util.List;

/**
 * This class represents a receipt of a finished sale.
 */
public final class Receipt {
    private final SaleDTO sale;
    private final Amount paidAmount;
    private final Amount change;

    /**
     * Creates a new instance.
     *
     * @param finishedSale The SaleDTO with relevant information about the finished sale.
     */
    public Receipt(SaleDTO finishedSale, Amount paidAmount, Amount change) {
        sale = finishedSale;
        this.paidAmount = paidAmount;
        this.change = change;
    }

    /**
     * Creates the formatted receipt as a <code>String</code>.
     *
     * @return the receipt as a <code>String</code>.
     */
    public String receiptInStringFormat() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("-------- RECEIPT --------\n");
        stringBuilder.append("Time of sale: \n");
        stringBuilder.append(sale.getTimeOfSale());
        stringBuilder.append("\n\n");

        stringBuilder.append("Items in sale: \n");
        List<ItemDTO> itemsInSale = sale.getItemsInSale();
        for (ItemDTO item : itemsInSale) {
            stringBuilder.append(item);
            stringBuilder.append("\n");
        }

        stringBuilder.append("\nTotal price incl VAT: ");
        stringBuilder.append(sale.getTotInclVat());

        stringBuilder.append("\nTotal price excl VAT: ");
        stringBuilder.append(sale.getTotExclVat());

        stringBuilder.append("\nTotal amount of VAT: ");
        stringBuilder.append(sale.getTotVat());

        stringBuilder.append("\n\nAmount of payment: ");
        stringBuilder.append(paidAmount);

        stringBuilder.append("\nAmount of change: ");
        stringBuilder.append(change);
        stringBuilder.append("\n-------------------------");

        return stringBuilder.toString();
    }
}
