package com.checkout.payments.four.request;

import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.request.source.RequestNetworkTokenSource;
import com.checkout.payments.four.request.source.RequestSource;
import com.checkout.payments.four.request.source.RequestTokenSource;
import com.checkout.payments.four.sender.RequestCorporateSender;
import com.checkout.payments.four.sender.RequestIndividualSender;
import com.checkout.payments.four.sender.RequestInstrumentSender;
import com.checkout.payments.four.sender.RequestSender;

public final class Payments {

    private Payments() {
    }

    public static CardPaymentBuilder card(final RequestCardSource source) {
        return new CardPaymentBuilder(source);
    }

    public static TokenPaymentBuilder token(final RequestTokenSource source) {
        return new TokenPaymentBuilder(source);
    }

    public static IdSourcePaymentBuilder id(final RequestIdSource source) {
        return new IdSourcePaymentBuilder(source);
    }

    public static NetworkTokenPaymentBuilder networkTokenSource(final RequestNetworkTokenSource source) {
        return new NetworkTokenPaymentBuilder(source);
    }

    public static class CardPaymentBuilder extends PaymentsBuilder {

        public CardPaymentBuilder(final RequestCardSource source) {
            super(source);
        }
    }

    public static class TokenPaymentBuilder extends PaymentsBuilder {

        public TokenPaymentBuilder(final RequestTokenSource source) {
            super(source);
        }

    }

    public static class IdSourcePaymentBuilder extends PaymentsBuilder {

        public IdSourcePaymentBuilder(final RequestIdSource source) {
            super(source);
        }

    }

    public static class NetworkTokenPaymentBuilder extends PaymentsBuilder {

        public NetworkTokenPaymentBuilder(final RequestNetworkTokenSource source) {
            super(source);
        }

    }

    public abstract static class PaymentsBuilder {

        private final RequestSource source;

        protected PaymentsBuilder(final RequestSource source) {
            this.source = source;
        }

        public <S extends RequestSender> PaymentRequest.PaymentRequestBuilder fromSender(final S sender) {
            return builder(sender);
        }

        public PaymentRequest.PaymentRequestBuilder individualSender(final RequestIndividualSender individualSender) {
            return builder(individualSender);
        }

        public PaymentRequest.PaymentRequestBuilder corporateSender(final RequestCorporateSender corporateSender) {
            return builder(corporateSender);
        }

        public PaymentRequest.PaymentRequestBuilder instrumentSender(final RequestInstrumentSender instrumentSender) {
            return builder(instrumentSender);
        }

        private PaymentRequest.PaymentRequestBuilder builder(final RequestSender sender) {
            return new PaymentRequest.PaymentRequestBuilder().sender(sender).source(source);
        }

    }

}
