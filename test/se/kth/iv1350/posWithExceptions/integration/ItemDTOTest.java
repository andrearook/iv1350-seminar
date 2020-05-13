package se.kth.iv1350.posWithExceptions.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.kth.iv1350.posWithExceptions.model.Amount;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ItemDTOTest {
    private ItemDTO firstItem;
    private ItemDTO secondItem;

    @BeforeEach
    void setUp() {
        firstItem = new ItemDTO(111, new Amount(10), 6, "Banana");
        secondItem = new ItemDTO(222, new Amount(20), 6, "Orange");
    }

    @AfterEach
    void tearDown() {
        firstItem = null;
        secondItem = null;
    }

    @Test
    void testNotEqualsNull() {
        Object otherObject = null;
        boolean expectedResult = false;
        boolean result = firstItem.equals(otherObject);
        assertEquals(expectedResult, result, "The ItemDTO instance is equal to null.");
    }

    @Test
    void testNotEqualsOtherTypeOfObject() {
        Object otherObject = new Object();
        boolean expectedResult = false;
        boolean result = firstItem.equals(otherObject);
        assertEquals(expectedResult, result, "The ItemDTO instance is equal to instance of java.lang.Object.");
    }

    @Test
    void testNotEquals() {
        boolean expectedResult = false;
        boolean result = firstItem.equals(secondItem);
        assertEquals(expectedResult, result, "The ItemDTO instance is equal to an ItemDTO instance with " +
                "different attributes.");
    }

    @Test
    void testEquals() {
        ItemDTO otherItem = new ItemDTO(111, new Amount(10), 6, "Banana");
        boolean expectedResult = true;
        boolean result = firstItem.equals(otherItem);
        assertEquals(expectedResult, result, "The ItemDTO instance is not equal to an ItemDTO instance " +
                "with the same attributes.");
    }

    @Test
    void testGetCopy() {
        ItemDTO copy = firstItem.getCopy();
        boolean expectedResult = true;
        boolean result = firstItem.equals(copy);
        assertEquals(expectedResult, result, "The copy does not have the same attributes as the ItemDTO instance");
    }

    @Test
    void testToString() {
        String expectedItemDescription = firstItem.getItemDescription();
        String expectedItemPrice = firstItem.getItemPrice().toString();

        String result = firstItem.toString();

        assertTrue(result.contains(expectedItemDescription), "Contains wrong description.");
        assertTrue(result.contains(expectedItemPrice), "Contains wrong price.");
    }
}