package com.checkout.handlepaymentsandpayouts.payments.postpayments.responses;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.HttpMetadata;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.requests.unreferencedrefundrequest.UnreferencedRefundRequest;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponseaccepted.RequestAPaymentOrPayoutResponseAccepted;
import com.checkout.handlepaymentsandpayouts.payments.postpayments.responses.requestapaymentorpayoutresponsecreated.RequestAPaymentOrPayoutResponseCreated;
import com.checkout.payments.PaymentsClient;
import com.checkout.payments.PaymentsClientImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentsClientUnreferencedRefundImplTest {

    private static final Map<Integer, Class<? extends HttpMetadata>> RESPONSE_MAPPINGS = new HashMap<>();

    static {
        RESPONSE_MAPPINGS.put(201, RequestAPaymentOrPayoutResponseCreated.class);
        RESPONSE_MAPPINGS.put(202, RequestAPaymentOrPayoutResponseAccepted.class);
    }

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private PaymentsClient paymentsClient;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.paymentsClient = new PaymentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestUnreferencedRefundPaymentCreated() throws ExecutionException, InterruptedException {

        // Arrange
        final UnreferencedRefundRequest request = mock(UnreferencedRefundRequest.class);
        final RequestAPaymentOrPayoutResponseCreated response = mock(RequestAPaymentOrPayoutResponseCreated.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("payments"), eq(authorization), eq(RESPONSE_MAPPINGS), eq(request), isNull());

        // Act
        final CompletableFuture<RequestAPaymentOrPayoutResponse> future = paymentsClient.requestPayment(request);
        final RequestAPaymentOrPayoutResponse result = future.get();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getCreated());
        assertNull(result.getAccepted());
    }

    @Test
    void shouldRequestUnreferencedRefundPaymentIdempotencyKeyCreated() throws ExecutionException, InterruptedException {

        // Arrange
        final UnreferencedRefundRequest request = mock(UnreferencedRefundRequest.class);
        final RequestAPaymentOrPayoutResponseCreated response = mock(RequestAPaymentOrPayoutResponseCreated.class);
        final String idempotencyKey = "idem-key-201";

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("payments"), eq(authorization), eq(RESPONSE_MAPPINGS), eq(request), eq(idempotencyKey));

        // Act
        final CompletableFuture<RequestAPaymentOrPayoutResponse> future = paymentsClient.requestPayment(request, idempotencyKey);
        final RequestAPaymentOrPayoutResponse result = future.get();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getCreated());
        assertNull(result.getAccepted());
    }

    @Test
    void shouldRequestUnreferencedRefundPaymentAccepted() throws ExecutionException, InterruptedException {

        // Arrange
        final UnreferencedRefundRequest request = mock(UnreferencedRefundRequest.class);
        final RequestAPaymentOrPayoutResponseAccepted response = mock(RequestAPaymentOrPayoutResponseAccepted.class);

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("payments"), eq(authorization), eq(RESPONSE_MAPPINGS), eq(request), isNull());

        // Act
        final CompletableFuture<RequestAPaymentOrPayoutResponse> future = paymentsClient.requestPayment(request);
        final RequestAPaymentOrPayoutResponse result = future.get();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAccepted());
        assertNull(result.getCreated());
    }

    @Test
    void shouldRequestUnreferencedRefundPaymentIdempotencyKeyAccepted() throws ExecutionException, InterruptedException {

        // Arrange
        final UnreferencedRefundRequest request = mock(UnreferencedRefundRequest.class);
        final RequestAPaymentOrPayoutResponseAccepted response = mock(RequestAPaymentOrPayoutResponseAccepted.class);
        final String idempotencyKey = "idem-key-202";

        doReturn(CompletableFuture.completedFuture(response))
                .when(apiClient)
                .postAsync(eq("payments"), eq(authorization), eq(RESPONSE_MAPPINGS), eq(request), eq(idempotencyKey));

        // Act
        final CompletableFuture<RequestAPaymentOrPayoutResponse> future = paymentsClient.requestPayment(request, idempotencyKey);
        final RequestAPaymentOrPayoutResponse result = future.get();

        // Assert
        assertNotNull(result);
        assertNotNull(result.getAccepted());
        assertNull(result.getCreated());
    }

}