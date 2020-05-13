package se.kth.iv1350.posWithExceptions.view;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.integration.ItemDTO;
import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;
import se.kth.iv1350.posWithExceptions.util.TimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ConsoleTotalRevenueViewTest {
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
    void testNewRental() {
        ConsoleTotalRevenueView instance = new ConsoleTotalRevenueView();
        SaleDTO firstSale = createSaleDTO();
        instance.newSale(firstSale);
        String expectedResultFirstCall = "\nTotal sales made: " + 1 +
                "\nLast sale completed: " + TimeUtil.formatTime(firstSale.getTimeOfSale()) +
                "\nTotal revenue made: " + firstSale.getTotExclVat();
        String resultFirstCall = outputContent.toString();
        assertTrue(resultFirstCall.contains(expectedResultFirstCall), "Wrong printout");

        SaleDTO secondSale = createSaleDTO();
        instance.newSale(secondSale);
        String expectedResultSecondCall = "\nTotal sales made: " + 2 +
                "\nLast sale completed: " + TimeUtil.formatTime(secondSale.getTimeOfSale()) +
                "\nTotal revenue made: " + firstSale.getTotExclVat().add(secondSale.getTotExclVat());
        String resultSecondCall = outputContent.toString();
        assertTrue(resultSecondCall.contains(expectedResultSecondCall), "Wrong printout");
    }

    private SaleDTO createSaleDTO() {
        LocalDateTime timeOfSale = LocalDateTime.now();
        List<ItemDTO> itemsInSale = new ArrayList<>();
        ItemDTO item = new ItemDTO(111, new Amount(10), 6, "Banana");
        itemsInSale.add(item);
        Amount totInclVat = item.getItemPrice().includingVatCost(item.getVatRate());
        Amount totExclVat = item.getItemPrice();
        Amount totVat = totInclVat.subtract(totExclVat);
        return new SaleDTO(timeOfSale, totInclVat, totExclVat, totVat, itemsInSale);
    }
}