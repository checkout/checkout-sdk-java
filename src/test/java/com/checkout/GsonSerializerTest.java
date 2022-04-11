package com.checkout;

import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.destination.PaymentResponseAlternativeDestination;
import com.checkout.payments.response.destination.PaymentResponseCardDestination;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.getMock;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GsonSerializerTest {

    private final Serializer serializer = new GsonSerializer();

    @Test
    void shouldDeserialize_default_getPaymentResponse_cardDestination() {

        final GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/destination/card/get_payment_response.json"), GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getDestination());
        assertTrue(paymentResponse.getDestination() instanceof PaymentResponseCardDestination);
        final PaymentResponseCardDestination cardDestination = (PaymentResponseCardDestination) paymentResponse.getDestination();
        assertNotNull(cardDestination.getExpiryMonth());
        assertNotNull(cardDestination.getExpiryYear());
        assertNotNull(cardDestination.getName());
        assertNotNull(cardDestination.getFingerprint());
        assertNotNull(cardDestination.getCardType());
        assertNotNull(cardDestination.getCardCategory());
        assertNotNull(cardDestination.getProductId());
        assertNotNull(cardDestination.getProductType());

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_alternativeDestination() {

        final GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/destination/alternative/get_payment_response.json"), GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getDestination());
        assertTrue(paymentResponse.getDestination() instanceof PaymentResponseAlternativeDestination);
        final PaymentResponseAlternativeDestination alternativeDestination = (PaymentResponseAlternativeDestination) paymentResponse.getDestination();
        assertFalse(alternativeDestination.keySet().isEmpty());

    }

}
