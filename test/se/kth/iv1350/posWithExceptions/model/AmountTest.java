package se.kth.iv1350.posWithExceptions.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AmountTest {
    private Amount amountNoArgConstr;
    private Amount amountValueFive;

    @BeforeEach
    void setUp() {
        amountNoArgConstr = new Amount();
        amountValueFive = new Amount(5);
    }

    @AfterEach
    void tearDown() {
        amountNoArgConstr = null;
        amountValueFive = null;
    }

    @Test
    void testNotEqualsNull() {
        Object otherObject = null;
        boolean expectedResult = false;
        boolean result = amountNoArgConstr.equals(otherObject);
        assertEquals(expectedResult, result,
                "Amount instance is equal to null.");
    }

    @Test
    void testNotEqualsToOtherTypeOfObject() {
        Object otherObject = new Object();
        boolean expectedResult = false;
        boolean result = amountNoArgConstr.equals(otherObject);
        assertEquals(expectedResult, result,
                "Amount instance is equal to instance of java.lang.Object.");
    }

    @Test
    void testNotEqualsAmountAndNoValueAmount() {
        boolean expectedResult = false;
        boolean result = amountValueFive.equals(amountNoArgConstr);
        assertEquals(expectedResult, result,
                "Amount instance is equal to an Amount with another value.");
    }

    @Test
    void testNotEquals() {
        Amount otherAmount = new Amount(3);
        boolean expectedResult = false;
        boolean result = amountValueFive.equals(otherAmount);
        assertEquals(expectedResult, result,
                "Amount instance is equal to an Amount with another value.");
    }

    @Test
    void testEquals() {
        Amount otherAmount = new Amount(5);
        boolean expectedResult = true;
        boolean result = amountValueFive.equals(otherAmount);
        assertEquals(expectedResult, result,
                "Amount instance is not equal to an Amount with the same value.");
    }

    @Test
    void testEqualsNoArgConstr() {
        Amount otherAmount = new Amount(0);
        boolean expectedResult = true;
        boolean result = amountNoArgConstr.equals(otherAmount);
        assertEquals(expectedResult, result,
                "Amount instance is not equal to an Amount with the same value.");
    }

    @Test
    void testAdd() {
        double amountOfFirstOperand = 10;
        double amountOfSecondOperand = 5;
        Amount firstOperand = new Amount(amountOfFirstOperand);
        Amount secondOperand = new Amount(amountOfSecondOperand);
        Amount expectedResult = new Amount(amountOfFirstOperand + amountOfSecondOperand);
        Amount result = firstOperand.add(secondOperand);
        assertEquals(expectedResult, result, "Wrong addition result.");
    }

    @Test
    void testAddZeroResult() {
        double amountOfFirstOperand = 10;
        double amountOfSecondOperand = -10;
        Amount firstOperand = new Amount(amountOfFirstOperand);
        Amount secondOperand = new Amount(amountOfSecondOperand);
        Amount expectedResult = new Amount(amountOfFirstOperand + amountOfSecondOperand);
        Amount result = firstOperand.add(secondOperand);
        assertEquals(expectedResult, result, "Wrong addition result.");
    }

    @Test
    void testSubtract() {
        double amountOfFirstOperand = 10;
        double amountOfSecondOperand = 5;
        Amount firstOperand = new Amount(amountOfFirstOperand);
        Amount secondOperand = new Amount(amountOfSecondOperand);
        Amount expectedResult = new Amount(amountOfFirstOperand - amountOfSecondOperand);
        Amount result = firstOperand.subtract(secondOperand);
        assertEquals(expectedResult, result, "Wrong subtraction result.");
    }

    @Test
    void testSubtractZeroResult() {
        double amountOfFirstOperand = 10;
        double amountOfSecondOperand = 10;
        Amount firstOperand = new Amount(amountOfFirstOperand);
        Amount secondOperand = new Amount(amountOfSecondOperand);
        Amount expectedResult = new Amount(amountOfFirstOperand - amountOfSecondOperand);
        Amount result = firstOperand.subtract(secondOperand);
        assertEquals(expectedResult, result, "Wrong subtraction result.");
    }

    @Test
    void testSubtractNegativeResult() {
        double amountOfFirstOperand = 5;
        double amountOfSecondOperand = 10;
        Amount firstOperand = new Amount(amountOfFirstOperand);
        Amount secondOperand = new Amount(amountOfSecondOperand);
        Amount expectedResult = new Amount(amountOfFirstOperand - amountOfSecondOperand);
        Amount result = firstOperand.subtract(secondOperand);
        assertEquals(expectedResult, result, "Wrong subtraction result.");
    }

    @Test
    void testIncludingVatCost() {
        double amountOfOperand = 10;
        int vat = 10;
        Amount operand = new Amount(amountOfOperand);
        Amount expectedResult = new Amount(amountOfOperand + amountOfOperand * vat / 100);
        Amount result = operand.includingVatCost(vat);
        assertEquals(expectedResult, result, "Wrong inclusion of VAT.");
    }

    @Test
    void testIncludingNegativeVatCost() {
        double amountOfOperand = 10;
        int vat = -10;
        Amount operand = new Amount(amountOfOperand);
        Amount expectedResult = new Amount(amountOfOperand + amountOfOperand * vat / 100);
        Amount result = operand.includingVatCost(vat);
        assertEquals(expectedResult, result, "Wrong inclusion of VAT.");
    }

    @Test
    void testIncludingZeroVatCost() {
        double amountOfOperand = 10;
        int vat = 0;
        Amount operand = new Amount(amountOfOperand);
        Amount expectedResult = new Amount(amountOfOperand + amountOfOperand * vat / 100);
        Amount result = operand.includingVatCost(vat);
        assertEquals(expectedResult, result, "Wrong inclusion of VAT.");
    }

    @Test
    void testToStringPositiveAmount() {
        double amountOfOperand = 10;
        Amount operand = new Amount(amountOfOperand);
        String expectedResult = String.format("%.2f", amountOfOperand);
        String result = operand.toString();
        assertEquals(expectedResult, result, "Wrong toString result.");
    }

    @Test
    void testToStringNegativeAmount() {
        double amountOfOperand = -10;
        Amount operand = new Amount(amountOfOperand);
        String expectedResult = String.format("%.2f", amountOfOperand);
        String result = operand.toString();
        assertEquals(expectedResult, result, "Wrong toString result.");
    }

    @Test
    void testToStringZeroAmount() {
        double amountOfOperand = 0;
        Amount operand = new Amount(amountOfOperand);
        String expectedResult = String.format("%.2f", amountOfOperand);
        String result = operand.toString();
        assertEquals(expectedResult, result, "Wrong toString result.");
    }
}