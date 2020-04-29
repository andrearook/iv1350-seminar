package se.kth.iv1350.pos.model;

/**
 * This class represents a payment made by cash.
 */
public class CashPayment {
    private Amount paidAmount;

    /**
     * Creates an instance representing the paid amount of cash.
     *
     * @param paidAmount The paid amount of cash.
     */
    public CashPayment(Amount paidAmount) {
        this.paidAmount = paidAmount;
    }

    /**
     * Get the paid <code>Amount</code> of cash.
     *
     * @return The paid <code>Amount</code> of cash.
     */
    public Amount getPaidAmount() {
        return paidAmount;
    }
}
