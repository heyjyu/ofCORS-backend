package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QuantityTest {
    @Test
    void lessThan() {
        Quantity quantity = new Quantity(100L);

        assertTrue(quantity.lessThan(new Quantity(200L)));
        assertFalse(quantity.lessThan(new Quantity(100L)));
        assertFalse(quantity.lessThan(new Quantity(10L)));
    }
}
