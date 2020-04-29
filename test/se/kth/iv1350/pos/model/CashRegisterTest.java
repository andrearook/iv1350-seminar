package se.kth.iv1350.pos.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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