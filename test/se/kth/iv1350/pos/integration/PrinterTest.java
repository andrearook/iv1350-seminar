package se.kth.iv1350.pos.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.pos.model.Amount;
import se.kth.iv1350.pos.model.Receipt;
import se.kth.iv1350.pos.model.SaleDTO;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PrinterTest {
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
    void testPrintReceipt() {
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
        Printer printer = new Printer();
        printer.printReceipt(receipt);

        String expectedFirstItem = "Banana";
        String expectedSecondItem = "Orange";
        String expectedResult = "Total price incl VAT: " + totInclVat + "\nTotal price excl VAT: " + totExclVat +
                "\nTotal amount of VAT: " + totVat + "\n\nAmount of payment: " + paidAmount + "\nAmount of change: " +
                change;

        String result = outputContent.toString();

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