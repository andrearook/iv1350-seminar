package se.kth.iv1350.posWithExceptions.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.integration.ItemDTO;
import se.kth.iv1350.posWithExceptions.integration.Printer;
import se.kth.iv1350.posWithExceptions.integration.SystemCreator;
import se.kth.iv1350.posWithExceptions.model.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;
    private SystemCreator creator;
    private ByteArrayOutputStream outputContent;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        originalSystemOut = System.out;
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
        Printer printer = new Printer();
        creator = new SystemCreator();
        controller = new Controller(creator, printer);
    }

    @AfterEach
    void tearDown() {
        outputContent = null;
        System.setOut(originalSystemOut);
        creator = null;
        controller = null;
    }

    @Test
    void testStartNewSaleAndEnterItem() {
        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");
        InfoToPresentDTO expectedResult = new InfoToPresentDTO(item.getItemDescription(), item.getItemPrice(),
                item.getItemPrice().includingVatCost(item.getVatRate()));
        try {
            controller.startNewSale();
            InfoToPresentDTO result = controller.enterItem(itemID);
            assertEquals(expectedResult.toString(), result.toString(), "The info returned by enterItem " +
                    "does not match expected result.");
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got an exception");
            exc.printStackTrace();
        }
    }

    @Test
    void testStartNewSaleTwice() {
        controller.startNewSale();
        try {
            controller.startNewSale();
            fail("Managed to start a sale when a sale was already ongoing.");
        } catch (IllegalStateException exc) {
            assertTrue(exc.getMessage().contains("Call to startNewSale before a sale had been finished"),
                    "Wrong exception message." + exc.getMessage());
        }
    }

    @Test
    void testEnterItemWithoutCallingStartNewSale() {
        int itemID = 111;
        try {
            controller.enterItem(itemID);
            fail("Managed to enter an item before a sale was started.");
        } catch (IllegalStateException exc) {
            assertTrue(exc.getMessage().contains("Call to enterItem before a sale had been started"),
                    "Wrong exception message." + exc.getMessage());
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got wrong exception");
            exc.printStackTrace();
        }
    }

    @Test
    void testEnterItemWithNonExistingItem() {
        int itemID = 555;
        try {
            controller.startNewSale();
            controller.enterItem(itemID);
            fail("Managed to enter an item that does not exist in inventory.");
        } catch (ItemNotFoundException exc) {
            assertTrue(exc.getMessage().contains(String.valueOf(itemID)),
                    "Wrong exception message." + exc.getMessage());
        } catch (OperationFailedException exc) {
            fail("Got wrong exception");
            exc.printStackTrace();
        }
    }

    @Test
    void testEnterItemOperationFailed() {
        int itemID = 666;
        try {
            controller.startNewSale();
            controller.enterItem(itemID);
            fail("Managed to enter an item when operation should fail.");
        } catch (OperationFailedException exc) {
            assertTrue(exc.getMessage().contains("Could not receive item information"),
                    "Wrong exception message." + exc.getMessage());
        } catch (ItemNotFoundException exc) {
            fail("Got wrong exception");
            exc.printStackTrace();
        }
    }

    @Test
    void testEndSale() {
        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");

        Amount expectedResult = item.getItemPrice().includingVatCost(item.getVatRate());
        controller.startNewSale();
        try {
            controller.enterItem(itemID);
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got an exception");
            exc.printStackTrace();
        }
        Amount result = controller.endSale();
        assertEquals(expectedResult, result, "End sale did not return the right total price including VAT.");
    }

    @Test
    void testEndSaleWithoutCallingStartNewSale() {
        try {
            controller.endSale();
            fail("Managed to end sale before a sale was started.");
        } catch (IllegalStateException exc) {
            assertTrue(exc.getMessage().contains("Call to endSale before a sale had been started"),
                    "Wrong exception message." + exc.getMessage());
        }
    }

    @Test
    void testEnterAmountPaidReturningChange() {
        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");

        controller.startNewSale();
        try {
            controller.enterItem(itemID);
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got an exception");
            exc.printStackTrace();
        }
        Amount totalCost = item.getItemPrice().includingVatCost(item.getVatRate());
        Amount extraThatWillResultInTheChange = new Amount(10);
        Amount paid = totalCost.add(extraThatWillResultInTheChange);

        Amount expectedResult = paid.subtract(totalCost);
        Amount result = controller.enterAmountPaid(paid);
        assertEquals(expectedResult, result, "The returned change was not correct.");
    }

    @Test
    void testEnterAmountPaidPrintingReceipt() {
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);

        controller.startNewSale();
        try {
            controller.enterItem(itemID);
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got an exception");
            exc.printStackTrace();
        }
        Amount totInclVat = item.getItemPrice().includingVatCost(item.getVatRate());
        Amount totExclVat = item.getItemPrice();
        Amount totVat = totInclVat.subtract(totExclVat);
        Amount paidAmount = totInclVat;
        Amount change = paidAmount.subtract(totInclVat);

        controller.enterAmountPaid(paidAmount);
        LocalDateTime timeOfSale = LocalDateTime.now();

        String expectedItem = itemDescription;
        String expectedResult = "Total price incl VAT: " + totInclVat + "\nTotal price excl VAT: " + totExclVat +
                "\nTotal amount of VAT: " + totVat + "\n\nAmount of payment: " + paidAmount + "\nAmount of change: " +
                change;
        String result = outputContent.toString();

        assertTrue(result.contains(expectedItem), "Contains wrong item.");
        assertTrue(result.contains(expectedResult), "Contains wrong information.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getYear())), "Wrong sale year.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getMonthValue())), "Wrong sale month.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getDayOfMonth())), "Wrong sale day.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getHour())), "Wrong sale hour.");
        assertTrue(result.contains(Integer.toString(timeOfSale.getMinute())), "Wrong sale minute.");
    }

    @Test
    void testEnterAmountPaidWithoutCallingStartNewSale() {
        Amount amountToPay = new Amount(10);
        try {
            controller.enterAmountPaid(amountToPay);
            fail("Managed to enter amount paid before a sale was started.");
        } catch (IllegalStateException exc) {
            assertTrue(exc.getMessage().contains("Call to enterAmountPaid before a sale had been started"),
                    "Wrong exception message." + exc.getMessage());
        }
    }

    @Test
    void testAddSaleObserver() {
        controller.addSaleObserver(new TestingObserver());
        controller.startNewSale();
        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");
        try {
            controller.enterItem(itemID);
        } catch (ItemNotFoundException | OperationFailedException exc) {
            fail("Got an exception");
            exc.printStackTrace();
        }
        Amount totInclVat = item.getItemPrice().includingVatCost(item.getVatRate());
        Amount paidAmount = totInclVat;

        controller.enterAmountPaid(paidAmount);
        String result = outputContent.toString();
        assertTrue(result.contains("Observed total cost including VAT is: " + totInclVat),
                "Observer did not print as expected.");
    }

    private class TestingObserver implements SaleObserver {
        @Override
        public void newSale(SaleDTO sale) {
            System.out.println("Observed total cost including VAT is: " + sale.getTotInclVat());
        }
    }
}