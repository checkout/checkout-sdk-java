package com.checkout.handlepaymentsandpayouts.flow.entities;

import com.checkout.GsonSerializer;
import com.checkout.handlepaymentsandpayouts.flow.requests.PaymentSessionCreateRequest;
import com.checkout.payments.hosted.HostedPaymentRequest;
import com.checkout.payments.links.PaymentLinkRequest;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerRetrySerializationTest {

    private final GsonSerializer serializer = new GsonSerializer();

    @Test
    void shouldSerializeSwaggerExample() {
        final String json = serializer.toJson(CustomerRetry.builder().maxAttempts(2).build());

        assertEquals("{\"max_attempts\":2}", json);
    }

    @Test
    void shouldSendDefaultFiveWhenNotSet() {
        assertEquals("{\"max_attempts\":5}", serializer.toJson(CustomerRetry.builder().build()));
    }

    @Test
    void shouldSerializeZeroAttempts() {
        final String json = serializer.toJson(CustomerRetry.builder().maxAttempts(0).build());

        assertEquals("{\"max_attempts\":0}", json);
    }

    @Test
    void shouldRoundTrip() {
        final CustomerRetry original = CustomerRetry.builder().maxAttempts(3).build();

        final CustomerRetry deserialized = serializer.fromJson(serializer.toJson(original), CustomerRetry.class);

        assertEquals(original, deserialized);
        assertEquals(3, deserialized.getMaxAttempts());
    }

    @Test
    void shouldDeserializeSwaggerExample() {
        final CustomerRetry retry = serializer.fromJson("{\"max_attempts\":2}", CustomerRetry.class);

        assertEquals(2, retry.getMaxAttempts());
    }

    @Test
    void shouldSendOnlyMaxAttemptsFromHostedPayments() {
        final HostedPaymentRequest request = HostedPaymentRequest.builder()
                .customerRetry(CustomerRetry.builder().maxAttempts(2).build())
                .build();

        final String json = serializer.toJson(request);

        assertCustomerRetryIsExactly(json);
    }

    @Test
    void shouldSendOnlyMaxAttemptsFromPaymentLinks() {
        final PaymentLinkRequest request = PaymentLinkRequest.builder()
                .customerRetry(CustomerRetry.builder().maxAttempts(2).build())
                .build();

        final String json = serializer.toJson(request);

        assertCustomerRetryIsExactly(json);
    }

    @Test
    void shouldSendOnlyMaxAttemptsFromPaymentSessions() {
        final PaymentSessionCreateRequest request = PaymentSessionCreateRequest.builder()
                .customerRetry(CustomerRetry.builder().maxAttempts(2).build())
                .build();

        final String json = serializer.toJson(request);

        assertCustomerRetryIsExactly(json);
    }

    private static void assertCustomerRetryIsExactly(final String json) {
        final JsonObject customerRetry = JsonParser.parseString(json).getAsJsonObject().getAsJsonObject("customer_retry");
        assertEquals("{\"max_attempts\":2}", customerRetry.toString());
    }
}
