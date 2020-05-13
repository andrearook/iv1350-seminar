package se.kth.iv1350.posWithExceptions.model;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.integration.ItemDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReceiptTest {

    @Test
    void testReceiptInStringFormat() {
        LocalDateTime timeOfSale = LocalDateTime.now();
        Amount totInclVat = new Amount(30);
        Amount totExclVat = new Amount(20);
        Amount totVat = new Amount(10);
        List<ItemDTO> itemsInSale = new ArrayList<>();

        itemsInSale.add(new ItemDTO(111, new Amount(10), 6, "Banana"));
        itemsInSale.add(new ItemDTO(222, new Amount(20), 6, "Orange"));

        SaleDTO finishedSale = new SaleDTO(timeOfSale, totInclVat, totExclVat, totVat, itemsInSale);

        Amount paidAmount = new Amount(40);
        Amount change = new Amount(10);

        Receipt receipt = new Receipt(finishedSale, paidAmount, change);

        String expectedFirstItem = "Banana";
        String expectedSecondItem = "Orange";
        String expectedResult = "Total price incl VAT: " + totInclVat + "\nTotal price excl VAT: " + totExclVat +
                "\nTotal amount of VAT: " + totVat + "\n\nAmount of payment: " + paidAmount + "\nAmount of change: " +
                change;

        String result = receipt.receiptInStringFormat();

        assertTrue(result.contains(expectedFirstItem), "Contains wrong item.");
        assertTrue(result.contains(expectedSecondItem), "Contains wrong item.");
        assertTrue(result.contains(expectedResult), "Contains wrong information.");

        assertTrue(result.contains(Integer.toString(timeOfSale.getYear())), "Wrong sale year.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getMonthValue())), "Wrong sale month.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getDayOfMonth())), "Wrong sale day.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getHour())), "Wrong sale hour.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getMinute())), "Wrong sale minute.");
    }
}