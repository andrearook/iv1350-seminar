package se.kth.iv1350.pos.model;

/**
 * This class is used for representing an amount of money.
 */
public final class Amount {
    private final double amount;

    /**
     * Creates an instance with value 0.
     */
    public Amount() {
        this.amount = 0;
    }

    /**
     * Creates an instance representing the specified amount of money.
     *
     * @param amount The specified amount of money the instance shall represent.
     */
    public Amount(double amount) {
        this.amount = amount;
    }

    /**
     * Adds the value of the parameter to the instance and creates a new <code>Amount</code> representing the result.
     *
     * @param amountToAdd The <code>Amount</code> that will be added.
     * @return an <code>Amount</code> representing the result of the addition.
     */
    public Amount add(Amount amountToAdd) {
        return new Amount(amount + amountToAdd.amount);
    }

    /**
     * Subtracts the value of the parameter from the instance and creates a new <code>Amount</code> representing
     * the result.
     *
     * @param amountToRemove The <code>Amount</code> that will be subtracted from the instance.
     * @return an <code>Amount</code> representing the result of the subtraction.
     */
    public Amount subtract(Amount amountToRemove) {
        return new Amount(amount - amountToRemove.amount);
    }

    /**
     * Adds the value that the VAT rate will result in and creates a new <code>Amount</code> representing the
     * addition of the instance and the cost based on the VAT rate.
     *
     * @param vatRate The percentage of VAT that shall be applied on the <code>Amount</code>.
     * @return an <code>Amount</code> representing the result of the addition of the instance and the VAT cost.
     */
    public Amount includingVatCost(int vatRate) {
        return new Amount(amount + amount * vatRate / 100);
    }

    /**
     * Evaluates if two <code>Amount</code> objects represent the same value.
     *
     * @param otherObject The object to compare to.
     * @return <code>true</code> if they represent the same value, <code>false</code> if they don't.
     */
    @Override
    public boolean equals(Object otherObject) {
        if (otherObject == null || !(otherObject instanceof Amount)) {
            return false;
        }
        Amount otherAmount = (Amount) otherObject;
        return amount == otherAmount.amount;
    }

    /**
     * Returns the value of the amount as a <code>String</code>.
     *
     * @return the amount as a <code>String</code>.
     */
    @Override
    public String toString() {
        return String.format("%.2f", amount);
    }
}
