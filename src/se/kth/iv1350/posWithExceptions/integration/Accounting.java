package se.kth.iv1350.posWithExceptions.integration;

import se.kth.iv1350.posWithExceptions.model.SaleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all calls to the data storage for accounting.
 */
public class Accounting {
    private List<SaleDTO> sales = new ArrayList<>();

    /**
     * Creates a new instance.
     */
    Accounting() {
    }

    /**
     * As there is no external database for accounting this method will just store the <code>SaleDTO</code>.
     *
     * @param saleInfo The <code>SaleDTO</code> containing information about the sale.
     */
    public void updateAccounting(SaleDTO saleInfo) {
        sales.add(saleInfo);
    }
}
