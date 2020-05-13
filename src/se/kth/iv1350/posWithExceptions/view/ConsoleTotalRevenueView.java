package se.kth.iv1350.posWithExceptions.view;

import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;
import se.kth.iv1350.posWithExceptions.util.TimeUtil;

/**
 * Represents a display in the console showing the total revenue made since the application started.
 */
public class ConsoleTotalRevenueView extends TotalRevenueView {

    /**
     * Presents the current total revenue as a printout, representing a display, to the console.
     *
     * @param sale          The <code>SaleDTO</code> representing the finished sale.
     * @param numberOfSales The number of sales made since the application started.
     * @param totalRevenue  The total revenue, excluding VAT, made since the application started.
     */
    @Override
    protected void presentCurrentRevenue(SaleDTO sale, int numberOfSales, Amount totalRevenue) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\n- TOTAL REVENUE DISPLAY -");
        stringBuilder.append("\nTotal sales made: ");
        stringBuilder.append(numberOfSales);
        stringBuilder.append("\nLast sale completed: ");
        stringBuilder.append(TimeUtil.formatTime(sale.getTimeOfSale()));
        stringBuilder.append("\nTotal revenue made: ");
        stringBuilder.append(totalRevenue);
        stringBuilder.append("\n-------------------------");
        System.out.println(stringBuilder);
    }
}
