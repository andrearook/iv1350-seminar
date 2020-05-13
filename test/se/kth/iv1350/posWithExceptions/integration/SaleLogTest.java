package se.kth.iv1350.posWithExceptions.integration;

import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.model.Amount;
import se.kth.iv1350.posWithExceptions.model.SaleDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class SaleLogTest {

    @Test
    void logFinishedSale() {
        LocalDateTime timeOfSale = LocalDateTime.now();
        Amount totInclVat = new Amount(30);
        Amount totExclVat = new Amount(20);
        Amount totVat = new Amount(10);
        List<ItemDTO> itemsInSale = new ArrayList<>();

        itemsInSale.add(new ItemDTO(111, new Amount(10), 6, "Banana"));
        itemsInSale.add(new ItemDTO(222, new Amount(20), 6, "Orange"));

        SaleDTO finishedSale = new SaleDTO(timeOfSale, totInclVat, totExclVat, totVat, itemsInSale);

        SaleLog saleLog = new SaleLog();
        saleLog.logFinishedSale(finishedSale);
    }
}