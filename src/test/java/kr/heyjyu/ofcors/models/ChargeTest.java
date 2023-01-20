package kr.heyjyu.ofcors.models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChargeTest {
    @Test
    void totalAmount() {
        Charge charge = new Charge(new UserId(1L), new Quantity(1L), new Price(110L));
    }
}
