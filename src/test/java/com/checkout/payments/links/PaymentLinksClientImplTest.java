package com.checkout.payments.links;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.AmountAllocations;
import com.checkout.common.Currency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentLinksClientImplTest {

    private static final String PAYMENT_LINKS_PATH = "payment-links";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private PaymentLinksClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new PaymentLinksClientImpl(apiClient, configuration);
    }

    @Test
    void shouldGetPaymentLink() throws ExecutionException, InterruptedException {
        final String reference = "payment_link_reference";
        final PaymentLinkDetailsResponse expectedResponse = mock(PaymentLinkDetailsResponse.class);

        when(apiClient.getAsync(eq(PAYMENT_LINKS_PATH + "/" + reference), eq(authorization), eq(PaymentLinkDetailsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentLinkDetailsResponse> future = client.getPaymentLink(reference);
        final PaymentLinkDetailsResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreatePaymentLink() throws ExecutionException, InterruptedException {
        final PaymentLinkRequest request = createPaymentLinkRequest();
        final PaymentLinkResponse expectedResponse = mock(PaymentLinkResponse.class);

        when(apiClient.postAsync(eq(PAYMENT_LINKS_PATH), eq(authorization), eq(PaymentLinkResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentLinkResponse> future = client.createPaymentLink(request);
        final PaymentLinkResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldGetPaymentLinkSync() {
        final String reference = "payment_link_reference";
        final PaymentLinkDetailsResponse expectedResponse = mock(PaymentLinkDetailsResponse.class);

        when(apiClient.get(eq(PAYMENT_LINKS_PATH + "/" + reference), eq(authorization), eq(PaymentLinkDetailsResponse.class)))
                .thenReturn(expectedResponse);

        final PaymentLinkDetailsResponse actualResponse = client.getPaymentLinkSync(reference);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCreatePaymentLinkSync() {
        final PaymentLinkRequest request = createPaymentLinkRequest();
        final PaymentLinkResponse expectedResponse = mock(PaymentLinkResponse.class);

        when(apiClient.post(eq(PAYMENT_LINKS_PATH), eq(authorization), eq(PaymentLinkResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentLinkResponse actualResponse = client.createPaymentLinkSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private PaymentLinkRequest createPaymentLinkRequest() {
        return PaymentLinkRequest.builder()
                .amount(100L)
                .currency(Currency.USD)
                .reference("ORD-123A")
                .amountAllocations(Collections.singletonList(AmountAllocations.builder()
                        .id("ent_sdioy6bajpzxyl3utftdp7legq")
                        .amount(100L)
                        .reference(UUID.randomUUID().toString())
                        .build()))
                .build();
    }

    private <T> void validateResponse(final T expectedResponse, final T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}
