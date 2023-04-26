package com.checkout;

import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cards.PhysicalCardDetailsResponse;
import com.checkout.issuing.cards.VirtualCardDetailsResponse;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.destination.PaymentResponseAlternativeDestination;
import com.checkout.payments.previous.response.destination.PaymentResponseCardDestination;
import com.checkout.payments.sender.PaymentCorporateSender;
import com.checkout.payments.sender.ResponseAlternativeSender;
import com.checkout.payments.sender.SenderType;
import org.junit.jupiter.api.Test;

import static com.checkout.TestHelper.getMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GsonSerializerTest {

    private final Serializer serializer = new GsonSerializer();

    @Test
    void shouldDeserialize_previous_getPaymentResponse_cardDestination() {

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
    void shouldDeserialize_previous_getPaymentResponse_alternativeDestination() {

        final GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/destination/alternative/get_payment_response.json"), GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getDestination());
        assertTrue(paymentResponse.getDestination() instanceof PaymentResponseAlternativeDestination);
        final PaymentResponseAlternativeDestination alternativeDestination = (PaymentResponseAlternativeDestination) paymentResponse.getDestination();
        assertFalse(alternativeDestination.keySet().isEmpty());

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_corporateSender() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/sender/corporate/get_payment_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getSender());
        assertTrue(paymentResponse.getSender() instanceof PaymentCorporateSender);
        assertEquals("company", ((PaymentCorporateSender) paymentResponse.getSender()).getCompanyName());
        assertEquals(SenderType.CORPORATE, paymentResponse.getSender().getType());

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_alternativeSender() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/sender/alternative/get_payment_response.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getSender());
        assertTrue(paymentResponse.getSender() instanceof ResponseAlternativeSender);
        assertEquals("company", ((ResponseAlternativeSender) paymentResponse.getSender()).get("company_name"));

    }

    @Test
    void shouldDeserialize_default_getPaymentResponse_with_wrong_format() {

        final com.checkout.payments.response.GetPaymentResponse paymentResponse = serializer.fromJson(getMock("/mocks/payments/response/get_payment_response_wrong_format.json"), com.checkout.payments.response.GetPaymentResponse.class);

        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getExpiresOn());
        assertEquals("2019-09-10T10:11:12Z", paymentResponse.getExpiresOn().toString());

    }

    @Test
    void shouldDeserializeCardHolderCardsResponse() {

        final CardholderCardsResponse cardholderCardsResponse = serializer.fromJson(getMock("/mocks/issuing/response/cardholders/get_cardholder_card_response.json"), CardholderCardsResponse.class);

        assertNotNull(cardholderCardsResponse);
        assertNotNull(cardholderCardsResponse.getCards());
        assertThat(cardholderCardsResponse.getCards(), hasSize(2));
        assertThat(cardholderCardsResponse.getCards().get(0), isA(VirtualCardDetailsResponse.class));
        assertThat(cardholderCardsResponse.getCards().get(1), isA(PhysicalCardDetailsResponse.class));
    }

}
