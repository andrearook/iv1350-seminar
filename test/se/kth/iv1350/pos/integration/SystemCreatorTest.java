package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.model.Sale;

import static org.junit.jupiter.api.Assertions.*;

class SystemCreatorTest {

    @Test
    void testCreateInventory() {
        SystemCreator systemCreator = new SystemCreator();
        Inventory inventory = systemCreator.getInventory();
        assertTrue(inventory instanceof Inventory, "The Inventory was not created.");
    }

    @Test
    void testCreateAccounting() {
        SystemCreator systemCreator = new SystemCreator();
        Accounting accounting = systemCreator.getAccounting();
        assertTrue(accounting instanceof Accounting, "The Accounting was not created.");
    }

    @Test
    void testCreateSaleLog() {
        SystemCreator systemCreator = new SystemCreator();
        SaleLog saleLog = systemCreator.getSaleLog();
        assertTrue(saleLog instanceof SaleLog, "The SaleLog was not created.");
    }
}