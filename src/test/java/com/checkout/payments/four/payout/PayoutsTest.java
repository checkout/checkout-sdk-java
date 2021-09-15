package com.checkout.payments.four.payout;

import com.checkout.payments.four.payout.source.RequestCurrencyAccountSource;
import com.checkout.payments.four.sender.RequestCorporateSender;
import com.checkout.payments.four.sender.RequestIndividualSender;
import com.checkout.payments.four.sender.RequestInstrumentSender;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PayoutsTest {

    @Test
    void shouldBuildPayoutRequest_card_individualSender() {
        final RequestCurrencyAccountSource source = RequestCurrencyAccountSource.builder().build();
        final RequestIndividualSender sender = RequestIndividualSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).individualSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPayoutRequest_card_corporateSender() {
        final RequestCurrencyAccountSource source = RequestCurrencyAccountSource.builder().build();
        final RequestCorporateSender sender = RequestCorporateSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).corporateSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

    @Test
    void shouldBuildPayoutRequest_card_instrumentSender() {
        final RequestCurrencyAccountSource source = RequestCurrencyAccountSource.builder().build();
        final RequestInstrumentSender sender = RequestInstrumentSender.builder().build();
        final PayoutRequest paymentRequest = Payouts.account(source).instrumentSender(sender).build();
        assertEquals(source, paymentRequest.getSource());
        assertEquals(sender, paymentRequest.getSender());
    }

}