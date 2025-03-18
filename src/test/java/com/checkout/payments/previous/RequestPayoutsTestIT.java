package com.checkout.payments.previous;

import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.TestCardSource;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.common.Phone;
import com.checkout.payments.PaymentStatus;
import com.checkout.payments.previous.request.PayoutRequest;
import com.checkout.payments.previous.request.destination.PaymentRequestCardDestination;
import com.checkout.payments.previous.response.PaymentResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Disabled("unavailable")
class RequestPayoutsTestIT extends SandboxTestFixture {

    RequestPayoutsTestIT() {
        super(PlatformType.PREVIOUS);
    }

    @Test
    @Disabled("unavailable")
    void shouldRequestPayout() throws Exception {

        final PaymentRequestCardDestination paymentCardDestination = PaymentRequestCardDestination.builder()
                .billingAddress(Address.builder()
                        .addressLine1("CheckoutSdk.com")
                        .addressLine2("90 Tottenham Court Road")
                        .city("London")
                        .state("London")
                        .zip("W1T 4TJ")
                        .country(CountryCode.GB)
                        .build())
                .expiryMonth(TestCardSource.VISA.getExpiryMonth())
                .expiryYear(TestCardSource.VISA.getExpiryYear())
                .number(TestCardSource.VISA.getNumber())
                .firstName(TestCardSource.VISA.getName())
                .lastName("Integration")
                .name(TestCardSource.VISA.getName())
                .phone(Phone.builder()
                        .countryCode("44")
                        .number("020 222333")
                        .build())
                .build();

        final Map<String, Object> processing = new HashMap<>();
        processing.put("purpose", "education");
        final PayoutRequest payoutRequest = PayoutRequest.builder()
                .destination(paymentCardDestination)
                .capture(false)
                .currency(Currency.GBP)
                .amount(10L)
                .reference(UUID.randomUUID().toString())
                .processing(processing)
                .build();

        final PaymentResponse paymentResponse = previousApi.paymentsClient().requestPayout(payoutRequest).get();
        assertNotNull(paymentResponse);
        assertNotNull(paymentResponse.getId());
        assertEquals(PaymentStatus.PENDING, paymentResponse.getStatus());

    }
}