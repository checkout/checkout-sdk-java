package com.checkout.agenticcommerce;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.agenticcommerce.request.AllowanceReason;
import com.checkout.agenticcommerce.request.CardFundingType;
import com.checkout.agenticcommerce.request.CardNumberType;
import com.checkout.agenticcommerce.request.DelegatePaymentAllowance;
import com.checkout.agenticcommerce.request.DelegatePaymentBillingAddress;
import com.checkout.agenticcommerce.request.DelegatePaymentHeaders;
import com.checkout.agenticcommerce.request.DelegatePaymentMethod;
import com.checkout.agenticcommerce.request.DelegatePaymentRequest;
import com.checkout.agenticcommerce.request.RiskSignal;
import com.checkout.agenticcommerce.response.DelegatePaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AgenticCommerceClientImplTest {

    private AgenticCommerceClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new AgenticCommerceClientImpl(apiClient, configuration);
    }

    @Test
    void shouldDelegatePayment() throws ExecutionException, InterruptedException {
        final DelegatePaymentRequest request = buildDelegatePaymentRequest();
        final DelegatePaymentHeaders headers = buildHeaders();
        final DelegatePaymentResponse response = mock(DelegatePaymentResponse.class);

        when(apiClient.postAsync(
                eq("agentic_commerce/delegate_payment"),
                eq(authorization),
                eq(DelegatePaymentResponse.class),
                eq(request),
                isNull(),
                eq(headers)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<DelegatePaymentResponse> future = client.delegatePayment(request, headers);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldDelegatePaymentSync() {
        final DelegatePaymentRequest request = buildDelegatePaymentRequest();
        final DelegatePaymentHeaders headers = buildHeaders();
        final DelegatePaymentResponse response = mock(DelegatePaymentResponse.class);

        when(apiClient.post(
                eq("agentic_commerce/delegate_payment"),
                eq(authorization),
                eq(DelegatePaymentResponse.class),
                eq(request),
                isNull(),
                eq(headers)))
                .thenReturn(response);

        final DelegatePaymentResponse result = client.delegatePaymentSync(request, headers);

        assertNotNull(result);
        assertEquals(response, result);
    }

    @Test
    void shouldDelegatePaymentWithMinimalRequest() throws ExecutionException, InterruptedException {
        final DelegatePaymentRequest request = buildMinimalDelegatePaymentRequest();
        final DelegatePaymentHeaders headers = buildHeaders();
        final DelegatePaymentResponse response = mock(DelegatePaymentResponse.class);

        when(apiClient.postAsync(
                eq("agentic_commerce/delegate_payment"),
                eq(authorization),
                eq(DelegatePaymentResponse.class),
                eq(request),
                isNull(),
                any(DelegatePaymentHeaders.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final DelegatePaymentResponse result = client.delegatePayment(request, headers).get();

        assertNotNull(result);
        assertEquals(response, result);
    }

    // --- builders ---

    static DelegatePaymentHeaders buildHeaders() {
        return DelegatePaymentHeaders.builder()
                .signature("sha256=abc123def456")
                .timestamp("2026-04-08T12:00:00Z")
                .apiVersion("2025-01-01")
                .build();
    }

    static DelegatePaymentRequest buildDelegatePaymentRequest() {
        final DelegatePaymentMethod paymentMethod = DelegatePaymentMethod.builder()
                .type("card")
                .cardNumberType(CardNumberType.FPAN)
                .number("4242424242424242")
                .expMonth("11")
                .expYear("2026")
                .name("Jane Doe")
                .cvc("123")
                .iin("424242")
                .displayCardFundingType(CardFundingType.CREDIT)
                .displayBrand("Visa")
                .displayLast4("4242")
                .checksPerformed(Arrays.asList("avs", "cvv"))
                .metadata(Collections.singletonMap("issuing_bank", "test"))
                .build();

        final DelegatePaymentAllowance allowance = DelegatePaymentAllowance.builder()
                .reason(AllowanceReason.ONE_TIME)
                .maxAmount(10000L)
                .currency("USD")
                .merchantId("cli_vkuhvk4vjn2edkps7dfsq6emqm")
                .checkoutSessionId("1PQrsT")
                .expiresAt(Instant.parse("2026-10-09T07:20:50.52Z"))
                .build();

        final DelegatePaymentBillingAddress billingAddress = DelegatePaymentBillingAddress.builder()
                .name("John Doe")
                .lineOne("123 Fake St.")
                .lineTwo("Unit 1")
                .city("San Francisco")
                .state("CA")
                .postalCode("12345")
                .country("US")
                .build();

        final RiskSignal riskSignal = RiskSignal.builder()
                .type("card_testing")
                .score(10)
                .action("blocked")
                .build();

        return DelegatePaymentRequest.builder()
                .paymentMethod(paymentMethod)
                .allowance(allowance)
                .billingAddress(billingAddress)
                .riskSignals(Collections.singletonList(riskSignal))
                .metadata(Collections.singletonMap("campaign", "q4"))
                .build();
    }

    static DelegatePaymentRequest buildMinimalDelegatePaymentRequest() {
        final DelegatePaymentMethod paymentMethod = DelegatePaymentMethod.builder()
                .type("card")
                .cardNumberType(CardNumberType.FPAN)
                .number("4242424242424242")
                .metadata(Collections.singletonMap("issuing_bank", "test"))
                .build();

        final DelegatePaymentAllowance allowance = DelegatePaymentAllowance.builder()
                .reason(AllowanceReason.ONE_TIME)
                .maxAmount(5000L)
                .currency("EUR")
                .merchantId("cli_merchant_123")
                .checkoutSessionId("sess_abc")
                .expiresAt(Instant.parse("2027-01-01T00:00:00Z"))
                .build();

        final RiskSignal riskSignal = RiskSignal.builder()
                .type("velocity")
                .score(5)
                .action("allow")
                .build();

        return DelegatePaymentRequest.builder()
                .paymentMethod(paymentMethod)
                .allowance(allowance)
                .riskSignals(Collections.singletonList(riskSignal))
                .build();
    }
}
