package com.checkout.payments.four.request;

import com.checkout.payments.four.request.source.PayoutRequestCurrencyAccountSource;
import com.checkout.payments.four.request.source.PayoutRequestSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentGovernmentSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.PaymentInstrumentSender;
import com.checkout.payments.four.sender.PaymentSender;

public final class Payouts {

    private Payouts() {
    }

    public static CurrencyAccountBuilder account(final PayoutRequestCurrencyAccountSource source) {
        return new CurrencyAccountBuilder(source);
    }

    public static class CurrencyAccountBuilder extends PayoutsBuilder {

        public CurrencyAccountBuilder(final PayoutRequestCurrencyAccountSource source) {
            super(source);
        }

    }

    public abstract static class PayoutsBuilder {

        private final PayoutRequestSource source;

        protected PayoutsBuilder(final PayoutRequestSource source) {
            this.source = source;
        }

        public PayoutRequest.PayoutRequestBuilder individualSender(final PaymentIndividualSender individualSender) {
            return builder(individualSender);
        }

        public PayoutRequest.PayoutRequestBuilder corporateSender(final PaymentCorporateSender corporateSender) {
            return builder(corporateSender);
        }

        public PayoutRequest.PayoutRequestBuilder instrumentSender(final PaymentInstrumentSender instrumentSender) {
            return builder(instrumentSender);
        }

        public PayoutRequest.PayoutRequestBuilder governmentSender(final PaymentGovernmentSender governmentSender) {
            return builder(governmentSender);
        }

        private PayoutRequest.PayoutRequestBuilder builder(final PaymentSender sender) {
            return new PayoutRequest.PayoutRequestBuilder().sender(sender).source(source);
        }

    }

}
