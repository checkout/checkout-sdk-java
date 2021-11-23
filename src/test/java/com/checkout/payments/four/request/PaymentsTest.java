package com.checkout.payments.four.request;

import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.request.source.RequestNetworkTokenSource;
import com.checkout.payments.four.request.source.RequestTokenSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.PaymentInstrumentSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentsTest {

    @Test
    void shouldBuildPaymentRequest_card_individualSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final PaymentIndividualSender sender = PaymentIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_card_corporateSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final PaymentCorporateSender sender = PaymentCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_card_instrumentSender() {
        final RequestCardSource source = RequestCardSource.builder().build();
        final PaymentInstrumentSender sender = PaymentInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.card(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_individualSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final PaymentIndividualSender sender = PaymentIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_corporateSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final PaymentCorporateSender sender = PaymentCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_token_instrumentSender() {
        final RequestTokenSource source = RequestTokenSource.builder().build();
        final PaymentInstrumentSender sender = PaymentInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.token(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_individualSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final PaymentIndividualSender sender = PaymentIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_corporateSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final PaymentCorporateSender sender = PaymentCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_id_instrumentSender() {
        final RequestIdSource source = RequestIdSource.builder().build();
        final PaymentInstrumentSender sender = PaymentInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.id(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_individualSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final PaymentIndividualSender sender = PaymentIndividualSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_corporateSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final PaymentCorporateSender sender = PaymentCorporateSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPaymentRequest_networkToken_instrumentSender() {
        final RequestNetworkTokenSource source = RequestNetworkTokenSource.builder().build();
        final PaymentInstrumentSender sender = PaymentInstrumentSender.builder().build();
        final PaymentRequest paymentRequest = Payments.networkTokenSource(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

}