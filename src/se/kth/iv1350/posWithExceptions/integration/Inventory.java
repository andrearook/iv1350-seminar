package se.kth.iv1350.posWithExceptions.integration;

import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * A Singleton containing all calls to the data storage of the inventory.
 */
public class Inventory {
    private static final Inventory INSTANCE = new Inventory();
    private List<ItemDTO> items = new ArrayList<>();

    private Inventory() {
        addItems();
    }

    /**
     * Returns the only instance of this singleton.
     *
     * @return the only existing instance of the inventory representative.
     */
    public static Inventory getInstance() {
        return INSTANCE;
    }

    private void addItems() {
        items.add(new ItemDTO(111, new Amount(10), 6, "Banana"));
        items.add(new ItemDTO(222, new Amount(20), 6, "Orange"));
        items.add(new ItemDTO(333, new Amount(30), 12, "Avocado"));
        items.add(new ItemDTO(444, new Amount(40), 25, "Pineapple"));
    }

    /**
     * Search for an item in the inventory specified by the item identifier. A copy of the <code>ItemDTO</code>
     * representing the item is created and returned.
     *
     * @param itemID The item identifier.
     * @return an <code>ItemDTO</code> with information about the item, <code>null</code> when an item can not be found.
     * @throws NoDatabaseConnectionException when ItemID 666 is parameter, simulating a failure to connect the database.
     */
    public ItemDTO getItemInfo(int itemID) {
        if (itemID == 666) {
            throw new NoDatabaseConnectionException("Can not connect to database.");
        }
        for (ItemDTO item : items) {
            if (item.getItemID() == itemID) {
                return item.getCopy();
            }
        }
        return null;
    }

    /**
     * As there is no external database for inventory this method will do nothing for now.
     *
     * @param saleInfo The <code>SaleDTO</code> containing information about the sale.
     */
    public void updateInventory(SaleDTO saleInfo) {

    }
}
