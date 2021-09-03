package com.checkout.payments;

import com.checkout.CheckoutArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class IdSourceTest {

    @Test
    void shouldThrowOnMissingId() {
        assertThrows(CheckoutArgumentException.class, () -> new IdSource(null));
    }

    @Test
    void shouldCreateIdSource() {
        final IdSource source = new IdSource("src_xxx");
        source.setCvv("0757");
        assertEquals("src_xxx", source.getId());
        assertEquals("0757", source.getCvv());
    }

}