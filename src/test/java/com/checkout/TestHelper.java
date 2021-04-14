package com.checkout;

import com.checkout.common.Currency;
import com.checkout.payments.*;
import com.checkout.tokens.CardTokenRequest;

import java.util.UUID;

public class TestHelper {
    public static PaymentRequest<CardSource> createCardPaymentRequest() {
        return createCardPaymentRequest(100L);
    }

    public static PaymentRequest<DLocalSource> createDLocalPaymentRequest() {
        return createDLocalPaymentRequest(100L);
    }

    public static PaymentRequest<CardSource> createCardPayoutRequest() {
        CardSource cardSource = new CardSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        cardSource.setFirstName("John");
        cardSource.setLastName("Doe");


        PaymentRequest<CardSource> request = PaymentRequest.fromDestination(cardSource, Currency.GBP, 100L);
        request.setReference(UUID.randomUUID().toString());
        Processing processing = Processing.builder().senderInformation(
                SenderInformation.builder()
                    .firstName("Jane")
                    .lastName("Doe-Doe")
                    .address("1 Random Ave.")
                    .city("New York")
                    .country("US")
                    .postalCode("12345")
                    .state("New York")
                    .accountNumber("DE1234567890")
                    .reference("U1234567890")
                    .sourceOfFunds("Credit")
                    .build()
        ).build();
        request.setProcessing(processing);

        return request;
    }

    public static PaymentRequest<CardSource> createCardPaymentRequest(Long amount) {
        CardSource cardSource = new CardSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        cardSource.setCvv(TestCardSource.VISA.getCvv());
        cardSource.setStored(false);

        CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        PaymentRequest<CardSource> request = PaymentRequest.fromSource(cardSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<DLocalSource> createDLocalPaymentRequest(Long amount) {
        DLocalSource dlocalSource = new DLocalSource(TestCardSource.VISA.getNumber(), TestCardSource.VISA.getExpiryMonth(), TestCardSource.VISA.getExpiryYear());
        dlocalSource.setCvv(TestCardSource.VISA.getCvv());
        dlocalSource.setStored(false);

        CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        PaymentRequest<DLocalSource> request = PaymentRequest.fromSource(dlocalSource, Currency.GBP, amount);
        request.setCapture(false);
        request.setCustomer(customer);
        request.setReference(UUID.randomUUID().toString());

        return request;
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(RequestSource alternativePaymentMethodRequestSource, String currency) {
        return createAlternativePaymentMethodRequest(alternativePaymentMethodRequestSource, currency, 100);
    }

    public static PaymentRequest<RequestSource> createAlternativePaymentMethodRequest(RequestSource alternativePaymentMethodRequestSource, String currency, long amount) {
        CustomerRequest customer = new CustomerRequest();
        customer.setEmail(generateRandomEmail());

        PaymentRequest<RequestSource> request = PaymentRequest.fromSource(alternativePaymentMethodRequestSource, currency, amount);
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
        PaymentRequest<TokenSource> request = PaymentRequest.fromSource(new TokenSource(token), Currency.GBP, 100L);
        request.setCapture(false);
        return request;
    }
}