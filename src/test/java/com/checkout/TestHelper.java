package com.checkout;

import com.checkout.common.Currency;
import com.checkout.payments.*;
import com.checkout.tokens.CardTokenRequest;

import java.util.UUID;

public class TestHelper {
    public static PaymentRequest<CardSource> createCardPaymentRequest() {
        return createCardPaymentRequest(100);
    }

    public static PaymentRequest<CardSource> createCardPaymentRequest(Integer amount) {
        CardSource cardSource = new CardSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        cardSource.setCvv(TestCardSource.VISA.getCvv());

        CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        PaymentRequest<CardSource> request = new PaymentRequest<>(cardSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(RequestSource alternativePaymentMethodRequestSource, String currency) {
        return createAlternativePaymentMethodRequest(alternativePaymentMethodRequestSource, currency, 100);
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(RequestSource alternativePaymentMethodRequestSource, String currency, int amount) {
        CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        PaymentRequest<RequestSource> request = new PaymentRequest<>(alternativePaymentMethodRequestSource, currency, amount);
        request.setCapture(false);
        request.setReference(UUID.randomUUID().toString());
        request.setCustomer(customer);

        return request;
    }

    public static CardTokenRequest createCardTokenRequest() {
        CardTokenRequest request = new CardTokenRequest(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        request.setCvv(TestCardSource.VISA.getCvv());
        return request;
    }

    public static String generateRandomEmail() {
        return UUID.randomUUID().toString() + "@checkout-sdk-java.com";
    }

    public static PaymentRequest<TokenSource> createTokenPaymentRequest(String token) {
        PaymentRequest<TokenSource> request = new PaymentRequest<>(new TokenSource(token), Currency.GBP, 100);
        request.setCapture(false);
        return request;
    }
}