package se.kth.iv1350.posWithExceptions.model;

import org.junit.jupiter.api.Test;

class CashRegisterTest {

    @Test
    void testUpdateBalance() {
        Amount money = new Amount(100);
        CashPayment cash = new CashPayment(money);
        Amount change = new Amount(50);
        CashRegister register = new CashRegister();
        register.updateBalance(cash, change);
    }
}