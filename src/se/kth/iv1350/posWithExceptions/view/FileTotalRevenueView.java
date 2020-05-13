package se.kth.iv1350.posWithExceptions.view;

import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;
import se.kth.iv1350.posWithExceptions.util.TimeUtil;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Creates a file showing the total revenue made since the application started.
 */
public class FileTotalRevenueView extends TotalRevenueView {
    private static final String REVENUE_FILE_NAME = "totalRevenueView.txt";
    private PrintWriter revenueFile;

    /**
     * Creates a new instance that will be able to write to the file.
     *
     * @throws IOException when unable to create the file.
     */
    public FileTotalRevenueView() throws IOException {
        revenueFile = new PrintWriter(new FileWriter(REVENUE_FILE_NAME), true);
    }

    /**
     * Presents the current total revenue in a file format.
     *
     * @param sale          The <code>SaleDTO</code> representing the finished sale.
     * @param numberOfSales The number of sales made since the application started.
     * @param totalRevenue  The total revenue, excluding VAT, made since the application started.
     */
    @Override
    protected void presentCurrentRevenue(SaleDTO sale, int numberOfSales, Amount totalRevenue) {
        String infoToPresent = TimeUtil.getLocalizedTimeNow() + "\nNumber of sales made: " + numberOfSales +
                "\nLast completed sale's revenue: " + sale.getTotExclVat() +
                "\nTotal revenue made: " + totalRevenue + "\n";
        revenueFile.println(infoToPresent);
    }
}
