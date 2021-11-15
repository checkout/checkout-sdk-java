package com.checkout.payments.four.request;

import com.checkout.common.Currency;
import com.checkout.payments.four.request.source.AbstractRequestSource;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.request.source.RequestNetworkTokenSource;
import com.checkout.payments.four.request.source.RequestTokenSource;
import com.checkout.payments.four.request.source.apm.RequestIdealSource;
import com.checkout.payments.four.request.source.apm.RequestSofortSource;
import com.checkout.payments.four.sender.PaymentCorporateSender;
import com.checkout.payments.four.sender.PaymentIndividualSender;
import com.checkout.payments.four.sender.PaymentInstrumentSender;
import com.checkout.payments.four.sender.PaymentSender;

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

    public static IdealPaymentBuilder ideal(final RequestIdealSource source,
                                            final Currency currency,
                                            final Long amount) {
        return new IdealPaymentBuilder(source, currency, amount);
    }

    public static SofortPaymentBuilder sofort(final Currency currency,
                                              final Long amount) {
        return new SofortPaymentBuilder(currency, amount);
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

    public static class IdealPaymentBuilder extends PaymentsBuilder {

        private final Currency currency;
        private final Long amount;

        public IdealPaymentBuilder(final RequestIdealSource source,
                                   final Currency currency,
                                   final Long amount) {
            super(source);
            this.currency = currency;
            this.amount = amount;
        }

        @Override
        protected PaymentRequest.PaymentRequestBuilder builder(final PaymentSender sender) {
            return super.builder(sender).currency(this.currency).amount(amount);
        }

    }

    public static class SofortPaymentBuilder extends PaymentsBuilder {

        private final Currency currency;
        private final Long amount;

        public SofortPaymentBuilder(final Currency currency,
                                    final Long amount) {
            super(new RequestSofortSource());
            this.currency = currency;
            this.amount = amount;
        }

        @Override
        protected PaymentRequest.PaymentRequestBuilder builder(final PaymentSender sender) {
            return super.builder(sender).currency(currency).amount(amount);
        }

    }

    public abstract static class PaymentsBuilder {

        private final AbstractRequestSource source;

        protected PaymentsBuilder(final AbstractRequestSource source) {
            this.source = source;
        }

        public <S extends PaymentSender> PaymentRequest.PaymentRequestBuilder fromSender(final S sender) {
            return builder(sender);
        }

        public PaymentRequest.PaymentRequestBuilder individualSender(final PaymentIndividualSender individualSender) {
            return builder(individualSender);
        }

        public PaymentRequest.PaymentRequestBuilder corporateSender(final PaymentCorporateSender corporateSender) {
            return builder(corporateSender);
        }

        public PaymentRequest.PaymentRequestBuilder instrumentSender(final PaymentInstrumentSender instrumentSender) {
            return builder(instrumentSender);
        }

        protected PaymentRequest.PaymentRequestBuilder builder(final PaymentSender sender) {
            return new PaymentRequest.PaymentRequestBuilder().sender(sender).source(source);
        }

    }

}
