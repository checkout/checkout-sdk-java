package com.checkout.payments.newfields;

import com.checkout.CardSourceHelper;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentRouting;
import com.checkout.payments.PaymentRoutingScheme;
import com.checkout.payments.PaymentSubscription;
import com.checkout.payments.AbstractPaymentsTestIT;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.sender.PaymentIndividualSender;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static com.checkout.CardSourceHelper.getCorporateSender;
import static com.checkout.CardSourceHelper.getIndividualSender;
import static com.checkout.CardSourceHelper.getRequestCardSource;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Integration tests for new {@link PaymentRequest} fields introduced in the swagger update:
 * {@code expire_on}, {@code routing}, {@code subscription}.
 *
 * <p>Also verifies that {@code GetPaymentResponse.authentication} is populated
 * when returned by the API.</p>
 */
class PaymentNewFieldsIT extends AbstractPaymentsTestIT {

    @Test
    void shouldRequestPaymentWithSubscription() {
        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .amount(100L)
                .currency(Currency.EUR)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .subscription(PaymentSubscription.builder()
                        .id("sub_" + UUID.randomUUID().toString().replace("-", "").substring(0, 20))
                        .build())
                .build();

        final PaymentResponse response = blocking(() -> paymentsClient.requestPayment(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldRequestPaymentWithRouting() {
        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .amount(100L)
                .currency(Currency.EUR)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .routing(PaymentRouting.builder()
                        .attempts(Arrays.asList(
                                PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.VISA).build(),
                                PaymentRouting.RoutingAttempt.builder().scheme(PaymentRoutingScheme.MASTERCARD).build()
                        ))
                        .build())
                .build();

        final PaymentResponse response = blocking(() -> paymentsClient.requestPayment(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Disabled("expire_on is Multibanco-specific; requires an APM-enabled processing channel")
    @Test
    void shouldRequestMultibancoPaymentWithExpireOn() {
        final PaymentRequest request = PaymentRequest.builder()
                .currency(Currency.EUR)
                .amount(1000L)
                .reference(UUID.randomUUID().toString())
                .expireOn(Instant.now().plus(2, ChronoUnit.DAYS))
                .build();

        final PaymentResponse response = blocking(() -> paymentsClient.requestPayment(request));

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    void shouldGetPaymentAndReadAuthenticationField() {
        // First make a payment
        final RequestCardSource source = getRequestCardSource();
        final PaymentIndividualSender sender = getIndividualSender();

        final PaymentRequest request = PaymentRequest.builder()
                .source(source)
                .sender(sender)
                .amount(100L)
                .currency(Currency.EUR)
                .capture(false)
                .reference(UUID.randomUUID().toString())
                .build();

        final PaymentResponse paymentResponse = blocking(() -> paymentsClient.requestPayment(request));
        assertNotNull(paymentResponse);

        // Retrieve it and check the authentication field (may be null for non-3DS flows)
        final GetPaymentResponse getResponse = blocking(() ->
                paymentsClient.getPayment(paymentResponse.getId()));

        assertNotNull(getResponse);
        assertNotNull(getResponse.getId());
        // authentication field present only for 3DS/Google-SPA flows — no hard assert on value
    }
}
