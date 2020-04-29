package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.integration.ItemDTO;
import se.kth.iv1350.pos.integration.Printer;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

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
}