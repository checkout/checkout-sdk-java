package com.checkout.payments.beta.payout;

import com.checkout.payments.beta.payout.source.PayoutRequestSource;
import com.checkout.payments.beta.payout.source.RequestCurrencyAccountSource;
import com.checkout.payments.beta.sender.RequestCorporateSender;
import com.checkout.payments.beta.sender.RequestIndividualSender;
import com.checkout.payments.beta.sender.RequestInstrumentSender;
import com.checkout.payments.beta.sender.RequestSender;

public final class Payouts {

    private Payouts() {
    }

    public static CurrencyAccountBuilder account(final RequestCurrencyAccountSource source) {
        return new CurrencyAccountBuilder(source);
    }

    public static class CurrencyAccountBuilder extends PayoutsBuilder {

        public CurrencyAccountBuilder(final RequestCurrencyAccountSource source) {
            super(source);
        }

    }

    public abstract static class PayoutsBuilder {

        private final PayoutRequestSource source;

        protected PayoutsBuilder(final PayoutRequestSource source) {
            this.source = source;
        }

        public PayoutRequest.PayoutRequestBuilder individualSender(final RequestIndividualSender individualSender) {
            return builder(individualSender);
        }

        public PayoutRequest.PayoutRequestBuilder corporateSender(final RequestCorporateSender corporateSender) {
            return builder(corporateSender);
        }

        public PayoutRequest.PayoutRequestBuilder instrumentSender(final RequestInstrumentSender instrumentSender) {
            return builder(instrumentSender);
        }

        private PayoutRequest.PayoutRequestBuilder builder(final RequestSender sender) {
            return new PayoutRequest.PayoutRequestBuilder().sender(sender).source(source);
        }

    }

}
