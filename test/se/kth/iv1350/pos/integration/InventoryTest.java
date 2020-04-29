package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.SaleDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {

    @Test
    void testGetItemInfoValidItem() {
        Inventory inventory = new Inventory();

        ItemDTO expectedResult = new ItemDTO(111, new Amount(10), 6, "Banana");
        ItemDTO result = inventory.getItemInfo(111);

        assertEquals(expectedResult, result, "The item info from inventory does not match.");
    }

    @Test
    void testGetItemInfoNotValidItem() {
        Inventory inventory = new Inventory();
        ItemDTO result = inventory.getItemInfo(555);
        assertNull(result, "The result was not null, as it should when a specified item is not available.");
    }

    @Test
    void testUpdateInventory() {
        LocalDateTime timeOfSale = LocalDateTime.now();
        Amount totInclVat = new Amount(30);
        Amount totExclVat = new Amount(20);
        Amount totVat = new Amount(10);
        List<ItemDTO> itemsInSale = new ArrayList<>();

        itemsInSale.add(new ItemDTO(111, new Amount(10), 6, "Banana"));
        itemsInSale.add(new ItemDTO(222, new Amount(20), 6, "Orange"));

        SaleDTO finishedSale = new SaleDTO(timeOfSale, totInclVat, totExclVat, totVat, itemsInSale);

        Inventory inventory = new Inventory();
        inventory.updateInventory(finishedSale);
    }
}