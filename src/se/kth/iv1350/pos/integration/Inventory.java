package se.kth.iv1350.pos.integration;

import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.SaleDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains all calls to the data storage of the inventory.
 */
public class Inventory {
    private List<ItemDTO> items = new ArrayList<>();

    /**
     * Creates a new instance.
     */
    Inventory() {
        addItems();
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
     * @return an <code>ItemDTO</code> with information about the item. <code>null</code> if there is no item with the
     *         specified identifier.
     */
    public ItemDTO getItemInfo(int itemID) {
        for (ItemDTO item : items){
            if (item.getItemID() == itemID){
                return item.getCopy();
            }
        }
        return null;
    }

    /**
     * As there is no external database for inventory this method will do nothing for now.
     * @param saleInfo the <code>SaleDTO</code> containing information about the sale.
     */
    public void updateInventory(SaleDTO saleInfo) {

    }
}
