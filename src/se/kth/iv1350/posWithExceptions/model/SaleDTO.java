package se.kth.iv1350.posWithExceptions.model;

import se.kth.iv1350.posWithExceptions.integration.ItemDTO;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

/**
 * Holds information about one single sale.
 */
public final class SaleDTO {
    private final LocalDateTime timeOfSale;
    private final Amount totInclVat;
    private final Amount totExclVat;
    private final Amount totVat;
    private final List<ItemDTO> itemsInSale;

    /**
     * Creates an instance that represents one single sale.
     *
     * @param timeOfSale  The time of the sale.
     * @param totInclVat  The total price of the sale including VAT.
     * @param totExclVat  The total price of the sale.
     * @param itemsInSale The list of items in the sale.
     */
    public SaleDTO(LocalDateTime timeOfSale, Amount totInclVat, Amount totExclVat, Amount totVat,
                   List<ItemDTO> itemsInSale) {
        this.timeOfSale = timeOfSale;
        this.totInclVat = totInclVat;
        this.totExclVat = totExclVat;
        this.totVat = totVat;
        this.itemsInSale = Collections.unmodifiableList(itemsInSale);
    }

    /**
     * Get the time of the sale.
     *
     * @return the time of the sale.
     */
    public LocalDateTime getTimeOfSale() {
        return timeOfSale;
    }

    /**
     * Get the total price of the sale including VAT.
     *
     * @return the total price of the sale including VAT.
     */
    public Amount getTotInclVat() {
        return totInclVat;
    }

    /**
     * Get the total price of the sale.
     *
     * @return the total price of the sale.
     */
    public Amount getTotExclVat() {
        return totExclVat;
    }

    /**
     * Get the total price for the VAT of the sale.
     *
     * @return the total price for the VAT of the sale.
     */
    public Amount getTotVat() {
        return totVat;
    }

    /**
     * Get the list of items that was sold in the sale.
     *
     * @return the list of items that was sold in the sale.
     */
    public List<ItemDTO> getItemsInSale() {
        return itemsInSale;
    }
}
