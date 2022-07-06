package com.checkout.payments.four.request;

import com.checkout.payments.four.request.destination.PaymentRequestCardDestination;
import com.checkout.payments.four.request.destination.PaymentRequestIdDestination;
import com.checkout.payments.four.request.destination.PaymentRequestTokenDestination;
import com.checkout.payments.four.request.source.PayoutRequestCurrencyAccountSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentGovernmentSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.PaymentInstrumentSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayoutsTest {

    @Test
    void shouldBuildPayoutRequest_card_individualSender() {
        final PayoutRequestCurrencyAccountSource source = PayoutRequestCurrencyAccountSource.builder().build();
        final PaymentIndividualSender sender = PaymentIndividualSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPayoutRequest_card_corporateSender() {
        final PayoutRequestCurrencyAccountSource source = PayoutRequestCurrencyAccountSource.builder().build();
        final PaymentCorporateSender sender = PaymentCorporateSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPayoutRequest_card_instrumentSender() {
        final PayoutRequestCurrencyAccountSource source = PayoutRequestCurrencyAccountSource.builder().build();
        final PaymentInstrumentSender sender = PaymentInstrumentSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPayoutRequest_card_governmentSender() {
        final PayoutRequestCurrencyAccountSource source = PayoutRequestCurrencyAccountSource.builder().build();
        final PaymentGovernmentSender sender = PaymentGovernmentSender.builder().build();
        final PaymentRequestCardDestination cardDestination = PaymentRequestCardDestination.builder().build();
        final PaymentRequestIdDestination idDestination = PaymentRequestIdDestination.builder().build();
        final PaymentRequestTokenDestination tokenDestination = PaymentRequestTokenDestination.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).governmentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

}