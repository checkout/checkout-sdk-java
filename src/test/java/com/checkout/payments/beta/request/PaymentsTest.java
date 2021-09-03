package com.checkout.payments.beta.request;

import com.checkout.payments.beta.request.source.RequestCardSource;
import com.checkout.payments.beta.request.source.RequestIdSource;
import com.checkout.payments.beta.request.source.RequestNetworkTokenSource;
import com.checkout.payments.beta.request.source.RequestTokenSource;
import com.checkout.payments.beta.sender.RequestCorporateSender;
import com.checkout.payments.beta.sender.RequestIndividualSender;
import com.checkout.payments.beta.sender.RequestInstrumentSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentsTest {

    @Test
    void shouldBuildPaymentRequest_card_individualSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final RequestIndividualSender sender = RequestIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_card_corporateSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final RequestCorporateSender sender = RequestCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_card_instrumentSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final RequestInstrumentSender sender = RequestInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_individualSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final RequestIndividualSender sender = RequestIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_corporateSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final RequestCorporateSender sender = RequestCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_instrumentSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final RequestInstrumentSender sender = RequestInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_individualSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final RequestIndividualSender sender = RequestIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_corporateSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final RequestCorporateSender sender = RequestCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_instrumentSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final RequestInstrumentSender sender = RequestInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_individualSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final RequestIndividualSender sender = RequestIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_corporateSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final RequestCorporateSender sender = RequestCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_instrumentSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final RequestInstrumentSender sender = RequestInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

}