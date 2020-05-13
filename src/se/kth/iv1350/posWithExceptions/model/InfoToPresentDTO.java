package se.kth.iv1350.posWithExceptions.model;

/**
 * This class represents the information that shall be presented after each item has been registered.
 */
public final class InfoToPresentDTO {
    private final String itemDescription;
    private final Amount itemPrice;
    private final Amount totalPriceInclVat;

    /**
     * Creates a new instance.
     *
     * @param itemDescription   The description of the item, what it is.
     * @param itemPrice         The price of the item.
     * @param totalPriceInclVat The total price for the sale including VAT.
     */
    public InfoToPresentDTO(String itemDescription, Amount itemPrice, Amount totalPriceInclVat) {
        this.itemDescription = itemDescription;
        this.itemPrice = itemPrice;
        this.totalPriceInclVat = totalPriceInclVat;
    }

    /**
     * Get the information to present as a formatted <code>String</code>.
     *
     * @return the information to present as a <code>String</code>.
     */
    @Override
    public String toString() {
        String info = "Item description: " + itemDescription + "\nItem price: " + itemPrice +
                "\nTotal price including VAT: " + totalPriceInclVat + "\n";
        return info;
    }
}
