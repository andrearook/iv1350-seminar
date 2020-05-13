package se.kth.iv1350.posWithExceptions.model;

/**
 * An observer interface for receiving a notification when a sale has been completed.
 * Any class interested in such a notification implements this interface and receives
 * information about the completed sale.
 */
public interface SaleObserver {

    /**
     * Called when a sale has been completed.
     *
     * @param sale A <code>SaleDTO</code> representing the completed sale.
     */
    void newSale(SaleDTO sale);
}
