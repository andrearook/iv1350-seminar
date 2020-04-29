package se.kth.iv1350.pos.model;

/**
 * This class represents a cash register.
 */
public class CashRegister {
    private Amount balance;

    /**
     * Creates an instance.
     */
    public CashRegister() {
        balance = new Amount();
    }

    /**
     * Updates the balance held by the <code>CashRegister</code>.
     *
     * @param payment The payment received from the customer.
     * @param change The change that the customer was given.
     */
    public void updateBalance(CashPayment payment, Amount change){
        Amount amountToAdd = payment.getPaidAmount().subtract(change);
        balance = balance.add(amountToAdd);
    }
}
