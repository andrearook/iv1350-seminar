package se.kth.iv1350.posWithExceptions.view;

import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;
import se.kth.iv1350.posWithExceptions.model.SaleObserver;

/**
 * This class shows the total revenue made since the application started.
 * It implements the interface <code>SaleObserver</code> and will only be invoked, it will
 * never call the current Sale or the Controller to get information.
 */
public abstract class TotalRevenueView implements SaleObserver {
    private int numberOfSales;
    private Amount totalRevenue;

    /**
     * Creates a new instance with the number of sales set to zero, and the <code>Amount</code>
     * representing the total revenue set to zero.
     */
    protected TotalRevenueView() {
        numberOfSales = 0;
        totalRevenue = new Amount();
    }

    /**
     * Called by the observed class when a new sale has been completed.
     *
     * @param sale A <code>SaleDTO</code> representing the completed sale.
     */
    @Override
    public void newSale(SaleDTO sale) {
        addNewSale(sale);
        presentCurrentRevenue(sale, numberOfSales, totalRevenue);
    }

    private void addNewSale(SaleDTO sale) {
        numberOfSales++;
        totalRevenue = totalRevenue.add(sale.getTotExclVat());
    }

    /**
     * Presents the current total revenue made.
     *
     * @param sale          The <code>SaleDTO</code> representing the finished sale.
     * @param numberOfSales The number of sales made since the application started.
     * @param totalRevenue  The total revenue, excluding VAT, made since the application started.
     */
    protected abstract void presentCurrentRevenue(SaleDTO sale, int numberOfSales, Amount totalRevenue);
}
