package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.Amount;

/**
 * Holds information about one single item.
 */
public final class ItemDTO {
    private final int itemID;
    private final Amount itemPrice;
    private final int vatRate;
    private final String itemDescription;

    /**
     * Creates an instance that represents one single item.
     *
     * @param itemID          The item's identifier.
     * @param itemPrice       The item's specific price.
     * @param vatRate         The item's VAT rate.
     * @param itemDescription The item's description, what it is.
     */
    public ItemDTO(int itemID, Amount itemPrice, int vatRate, String itemDescription) {
        this.itemID = itemID;
        this.itemPrice = itemPrice;
        this.vatRate = vatRate;
        this.itemDescription = itemDescription;
    }

    /**
     * Get the item's identifier.
     *
     * @return the item's identifier.
     */
    public int getItemID() {
        return itemID;
    }

    /**
     * Get the price of the item.
     *
     * @return the price of the item.
     */
    public Amount getItemPrice() {
        return itemPrice;
    }

    /**
     * Get the VAT rate of the item.
     *
     * @return the VAT rate of the item.
     */
    public int getVatRate() {
        return vatRate;
    }

    /**
     * Get the description of the item, what it is.
     *
     * @return the description of the item.
     */
    public String getItemDescription() {
        return itemDescription;
    }

    /**
     * Create a copy of the specific item.
     *
     * @return an <code>ItemDTO</code> that is a copy of the specific item.
     */
    public ItemDTO getCopy() {
        return new ItemDTO(this.getItemID(), this.getItemPrice(), this.getVatRate(),
                this.getItemDescription());
    }

    /**
     * Evaluates if two <code>ItemDTO</code> objects represent the same item.
     *
     * @param otherObject The object to compare to.
     * @return <code>true</code> if they represent the same item, <code>false</code> if they don't.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || !(otherObject instanceof ItemDTO)) {
            return false;
        }
        ItemDTO otherItem = (ItemDTO) otherObject;

        if (itemID != otherItem.getItemID()) {
            return false;
        }
        if (!itemPrice.equals(otherItem.getItemPrice())) {
            return false;
        }
        if (vatRate != otherItem.getVatRate()) {
            return false;
        }
        if (!itemDescription.equals(otherItem.getItemDescription())) {
            return false;
        }
        return true;
    }

    /**
     * Get the description and price of the item as a <code>String</code>.
     *
     * @return a <code>String</code> with the description and price of the item.
     */
    @Override
    public String toString() {
        String item = itemDescription + " " + itemPrice.toString() + ":-";
        return item;
    }
}
