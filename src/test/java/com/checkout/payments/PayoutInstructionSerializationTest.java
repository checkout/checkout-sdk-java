package com.checkout.payments;

import com.checkout.GsonSerializer;
import com.checkout.payments.response.PayoutResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * Covers {@code instruction.funds_transfer_type} on the card payout response, added by the
 * 2026-08-05 spec. The field was already modelled on the payout *request*; this is the response
 * side, which had only {@code value_date}.
 */
class PayoutInstructionSerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldDeserializeFundsTransferTypeOnThePayoutInstruction() {
        final String json = "{\"id\":\"pay_1\",\"status\":\"Accepted\",\"reference\":\"ORD-1\","
                + "\"instruction\":{\"value_date\":\"2026-08-05T10:00:00Z\","
                + "\"funds_transfer_type\":\"AA\"}}";

        final PayoutResponse response = serializer.fromJson(json, PayoutResponse.class);

        assertNotNull(response.getInstruction());
        assertEquals("AA", response.getInstruction().getFundsTransferType());
        assertNotNull(response.getInstruction().getValueDate());
    }

    /**
     * The scheme does not always categorise the client, so the field has to survive being absent
     * rather than defaulting to something that reads as a real categorisation.
     */
    @Test
    void shouldLeaveFundsTransferTypeNullWhenAbsent() {
        final String json = "{\"id\":\"pay_1\",\"status\":\"Accepted\","
                + "\"instruction\":{\"value_date\":\"2026-08-05T10:00:00Z\"}}";

        final PayoutResponse response = serializer.fromJson(json, PayoutResponse.class);

        assertNotNull(response.getInstruction());
        assertNull(response.getInstruction().getFundsTransferType());
    }
}
