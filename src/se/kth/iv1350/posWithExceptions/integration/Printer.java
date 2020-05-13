package se.kth.iv1350.posWithExceptions.integration;

import se.kth.iv1350.posWithExceptions.model.Receipt;

/**
 * Represents a class responsible for calls to a printer.
 */
public class Printer {

    /**
     * As there is no external printer this method will only print to <code>System.out</code>.
     *
     * @param receipt The receipt that is to be printed regarding the finished sale.
     */
    public void printReceipt(Receipt receipt) {
        System.out.println(receipt.receiptInStringFormat());
    }
}
