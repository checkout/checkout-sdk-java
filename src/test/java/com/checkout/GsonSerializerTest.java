package com.checkout;

import com.checkout.financial.FinancialActionsQueryResponse;
import com.checkout.issuing.cardholders.CardholderCardsResponse;
import com.checkout.issuing.cards.responses.PhysicalCardDetailsResponse;
import com.checkout.issuing.cards.responses.VirtualCardDetailsResponse;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.destination.PaymentResponseAlternativeDestination;
import com.checkout.payments.previous.response.destination.PaymentResponseCardDestination;
import com.checkout.payments.sender.PaymentCorporateSender;
import com.checkout.payments.sender.ResponseAlternativeSender;
import com.checkout.payments.sender.SenderType;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static com.checkout.TestHelper.getMock;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isA;
import static org.junit.jupiter.api.Assertions.*;

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

        final CardholderCardsResponse cardholderCardsResponse = serializer.fromJson(getMock("/mocks/issuing/response/get_cardholder_card_response.json"), CardholderCardsResponse.class);

        assertNotNull(cardholderCardsResponse);
        assertNotNull(cardholderCardsResponse.getCards());
        assertThat(cardholderCardsResponse.getCards(), hasSize(2));
        assertThat(cardholderCardsResponse.getCards().get(0), isA(VirtualCardDetailsResponse.class));
        assertThat(cardholderCardsResponse.getCards().get(1), isA(PhysicalCardDetailsResponse.class));
    }

    @Test
    void shouldGetFinancial() {

        final FinancialActionsQueryResponse actionsQueryResponse = serializer.fromJson(getMock("/mocks/financial/response/get_financial_actions_response.json"), FinancialActionsQueryResponse.class);

        assertNotNull(actionsQueryResponse);
        assertNotNull(actionsQueryResponse.getData());
        assertThat(actionsQueryResponse.getData(), hasSize(1));
        assertNotNull(actionsQueryResponse.getData().get(0).getProcessedOn());
        assertNotNull(actionsQueryResponse.getData().get(0).getRequestedOn());
    }

    @Test
    void shouldDeserializeMultipleDateFormats() {
        Instant instant = Instant.parse("2021-06-08T12:25:01Z");
        PaymentResponse paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.000Z\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01Z\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01+00:00\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01+0000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01+00\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
        assertEquals(instant, paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698039\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698030\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698100\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4698000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4690000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4600000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.4000000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());

        paymentResponse = serializer.fromJson("{\"processed_on\":\"2021-06-08T12:25:01.0000000\"}", PaymentResponse.class);
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getProcessedOn());
    }

}
