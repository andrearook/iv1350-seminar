package se.kth.iv1350.pos.integration;

/**
 * This class creates the representatives of the external registries.
 */
public class SystemCreator {
    private Inventory inventory;
    private Accounting accounting;
    private SaleLog saleLog;

    /**
     * Creates an instance that is responsible to create the representatives of the external registries.
     */
    public SystemCreator() {
        inventory = new Inventory();
        accounting = new Accounting();
        saleLog = new SaleLog();
    }

    /**
     * Get the <code>Inventory</code> that shall be used by this application.
     *
     * @return the <code>Inventory</code>.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Get the <code>Accounting</code> that shall be used by this application.
     *
     * @return the <code>Accounting</code>.
     */
    public Accounting getAccounting() {
        return accounting;
    }

    /**
     * Get the <code>SaleLog</code> that shall be used by this application.
     *
     * @return the <code>SaleLog</code>.
     */
    public SaleLog getSaleLog() {
        return saleLog;
    }
}
