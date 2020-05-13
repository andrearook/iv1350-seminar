package se.kth.iv1350.posWithExceptions.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.integration.ItemDTO;
import se.kth.iv1350.posWithExceptions.integration.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SaleTest {
    private ByteArrayOutputStream outputContent;
    private PrintStream originalSystemOut;

    @BeforeEach
    void setUp() {
        originalSystemOut = System.out;
        outputContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputContent));
    }

    @AfterEach
    void tearDown() {
        outputContent = null;
        System.setOut(originalSystemOut);
    }

    @Test
    void testAddItemAndFindItem() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        ItemDTO result = sale.findItem(itemID);
        assertEquals(item, result, "The found item was not the same item.");
    }

    @Test
    void testGetTotInclVatAndEndSale() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        Amount expectedResult = itemPrice.includingVatCost(vatRate);
        Amount result = sale.getTotInclVat();
        assertEquals(expectedResult, result, "The total cost including VAT was not correct");
    }

    @Test
    void testGetTotExclVat() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        Amount expectedResult = itemPrice;
        Amount result = sale.getTotExclVat();
        assertEquals(expectedResult, result, "The total cost excluding VAT was not correct");
    }

    @Test
    void testGetTotVat() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        Amount expectedResult = itemPrice.includingVatCost(vatRate).subtract(itemPrice);
        Amount result = sale.getTotVat();
        assertEquals(expectedResult, result, "The total VAT was not correct");
    }

    @Test
    void testFinishSaleChangeCalculation() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        Amount paidAmount = new Amount(30);
        CashPayment payment = new CashPayment(paidAmount);
        Printer printer = new Printer();

        Amount totInclVat = itemPrice.includingVatCost(vatRate);
        Amount expectedResult = payment.getPaidAmount().subtract(totInclVat);
        Amount result = sale.finishSale(payment, printer);

        assertEquals(expectedResult, result, "The change was not correct.");
    }

    @Test
    void testFinishSaleReceiptPrintout() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        Amount paidAmount = new Amount(30);
        CashPayment payment = new CashPayment(paidAmount);
        Printer printer = new Printer();

        sale.finishSale(payment, printer);

        String expectedItem = itemDescription;
        Amount totInclVat = itemPrice.includingVatCost(vatRate);
        String expectedTotInclVat = totInclVat.toString();
        Amount totExclVat = itemPrice;
        String expectedTotExclVat = itemPrice.toString();

        String result = outputContent.toString();

        assertTrue(result.contains(expectedItem), "Call to printReceipt from instance of Sale " +
                "contains wrong item.");
        assertTrue(result.contains(expectedTotInclVat), "Call to printReceipt from instance of Sale " +
                "contains wrong total cost including VAT.");
        assertTrue(result.contains(expectedTotExclVat), "Call to printReceipt from instance of Sale " +
                "contains wrong total cost.");
    }

    @Test
    void testGetSaleInfo() {
        Sale sale = new Sale();
        int itemID = 111;
        Amount itemPrice = new Amount(10);
        int vatRate = 6;
        String itemDescription = "Banana";
        ItemDTO item = new ItemDTO(itemID, itemPrice, vatRate, itemDescription);
        sale.addItem(item);

        SaleDTO result = sale.getSaleInfo();

        Amount expectedTotInclVat = sale.getTotInclVat();
        Amount resultTotInclVat = result.getTotInclVat();

        Amount expectedTotExclVat = sale.getTotExclVat();
        Amount resultTotExclVatVat = result.getTotExclVat();

        Amount expectedTotVat = sale.getTotInclVat().subtract(sale.getTotExclVat());
        Amount resultTotVat = result.getTotVat();

        assertEquals(expectedTotInclVat, resultTotInclVat, "The total cost including VAT was not correct.");
        assertEquals(expectedTotExclVat, resultTotExclVatVat, "The total cost was not correct.");
        assertEquals(expectedTotVat, resultTotVat, "The total VAT was not correct.");
    }

    @Test
    void testAddSingleSaleObserver() {
        Sale sale = new Sale();
        int numberOfTheOnlyObserver = 1;
        sale.addSaleObserver(new TestingObserver(numberOfTheOnlyObserver));

        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");
        sale.addItem(item);
        Amount totInclVat = sale.getTotInclVat();
        CashPayment payment = new CashPayment(totInclVat);
        Printer printer = new Printer();
        sale.finishSale(payment, printer);

        String result = outputContent.toString();
        assertTrue(result.contains("Observed by observer #1, the total cost including VAT is: " + totInclVat),
                "Observer did not print as expected.");
    }

    @Test
    void testAddMultipleSaleObservers() {
        Sale sale = new Sale();
        List<SaleObserver> observers = new ArrayList<>();

        int numberOfObservers = 3;
        for (int i = 0; i < numberOfObservers; i++) {
            int numberOfThisObserver = i + 1;
            observers.add(new TestingObserver(numberOfThisObserver));
        }
        sale.addSaleObservers(observers);

        int itemID = 111;
        ItemDTO item = new ItemDTO(itemID, new Amount(10), 6, "Banana");
        sale.addItem(item);
        Amount totInclVat = sale.getTotInclVat();
        CashPayment payment = new CashPayment(totInclVat);
        Printer printer = new Printer();
        sale.finishSale(payment, printer);

        String result = outputContent.toString();
        assertTrue(result.contains("Observed by observer #1, the total cost including VAT is: " + totInclVat),
                "Observer did not print as expected.");
        assertTrue(result.contains("Observed by observer #2, the total cost including VAT is: " + totInclVat),
                "Observer did not print as expected.");
        assertTrue(result.contains("Observed by observer #3, the total cost including VAT is: " + totInclVat),
                "Observer did not print as expected.");
    }

    private class TestingObserver implements SaleObserver {
        private int numberOfThisObserver;

        /**
         * Creates an instance with a specified number, to be able to test that multiple
         * <code>TestingObservers</code> are notified.
         *
         * @param numberOfThisObserver The specified number belonging to the observer.
         */
        public TestingObserver(int numberOfThisObserver) {
            this.numberOfThisObserver = numberOfThisObserver;
        }

        @Override
        public void newSale(SaleDTO sale) {
            System.out.println("Observed by observer #" + numberOfThisObserver +
                    ", the total cost including VAT is: " + sale.getTotInclVat());
        }
    }
}