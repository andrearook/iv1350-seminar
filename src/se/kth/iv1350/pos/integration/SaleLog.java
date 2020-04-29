package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.SaleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all calls to the data storage where all the sales are stored.
 */
public class SaleLog {
    List<SaleDTO> sales = new ArrayList<>();

    SaleLog() {
    }

    /**
     * As there is no external database for logging of sales this method will just store the <code>SaleDTO</code>.
     *
     * @param saleInfo the <code>SaleDTO</code> containing information about the sale.
     */
    public void logFinishedSale(SaleDTO saleInfo) {
        sales.add(saleInfo);
    }
}
