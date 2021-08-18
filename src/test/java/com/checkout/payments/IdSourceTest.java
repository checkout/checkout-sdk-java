package com.checkout.payments;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdSourceTest {

    @Test
    public void given_id_missing_should_throw_exception() {
        assertThrows(IllegalArgumentException.class, () -> new IdSource(null));
    }

    @Test
    public void can_create_id_source() {
        final IdSource source = new IdSource("src_xxx");
        source.setCvv("0757");
        assertEquals("src_xxx", source.getId());
        assertEquals("0757", source.getCvv());
    }

}