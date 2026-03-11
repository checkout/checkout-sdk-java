package com.checkout.paymentmethods;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.paymentmethods.requests.PaymentMethodsQuery;
import com.checkout.paymentmethods.responses.PaymentMethodsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentMethodsClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration checkoutConfiguration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private PaymentMethodsClient paymentMethodsClient;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(checkoutConfiguration.getSdkCredentials()).thenReturn(sdkCredentials);
        this.paymentMethodsClient = new PaymentMethodsClientImpl(apiClient, checkoutConfiguration);
    }

    @Test
    void shouldGetPaymentMethods() throws ExecutionException, InterruptedException {
        final PaymentMethodsQuery query = createPaymentMethodsQuery();
        final PaymentMethodsResponse expectedResponse = mock(PaymentMethodsResponse.class);

        when(apiClient.queryAsync(eq("payment-methods"), any(SdkAuthorization.class), eq(query), eq(PaymentMethodsResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(expectedResponse));

        final CompletableFuture<PaymentMethodsResponse> future = paymentMethodsClient.getPaymentMethods(query);

        final PaymentMethodsResponse actualResponse = future.get();

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldThrowExceptionWhenQueryIsNull() {
        assertThrows(CheckoutArgumentException.class, 
            () -> paymentMethodsClient.getPaymentMethods(null));
    }

    @Test
    void shouldThrowExceptionWhenProcessingChannelIdIsNull() {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder().build();
        
        assertThrows(CheckoutArgumentException.class,
            () -> paymentMethodsClient.getPaymentMethods(query));
    }

    @Test
    void shouldThrowExceptionWhenProcessingChannelIdIsInvalid() {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder()
                .processingChannelId("invalid_id")
                .build();
        
        assertThrows(IllegalArgumentException.class,
            () -> paymentMethodsClient.getPaymentMethods(query));
    }

    // Synchronous methods
    @Test
    void shouldGetPaymentMethodsSync() {
        final PaymentMethodsQuery query = createPaymentMethodsQuery();
        final PaymentMethodsResponse expectedResponse = mock(PaymentMethodsResponse.class);

        when(apiClient.query(eq("payment-methods"), any(SdkAuthorization.class), eq(query), eq(PaymentMethodsResponse.class)))
                .thenReturn(expectedResponse);

        final PaymentMethodsResponse actualResponse = paymentMethodsClient.getPaymentMethodsSync(query);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldThrowExceptionWhenQueryIsNullSync() {
        assertThrows(CheckoutArgumentException.class,
            () -> paymentMethodsClient.getPaymentMethodsSync(null));
    }

    @Test
    void shouldThrowExceptionWhenProcessingChannelIdIsNullSync() {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder().build();
        
        assertThrows(CheckoutArgumentException.class,
            () -> paymentMethodsClient.getPaymentMethodsSync(query));
    }

    @Test
    void shouldThrowExceptionWhenProcessingChannelIdIsInvalidSync() {
        final PaymentMethodsQuery query = PaymentMethodsQuery.builder()
                .processingChannelId("invalid_id")
                .build();
        
        assertThrows(IllegalArgumentException.class,
            () -> paymentMethodsClient.getPaymentMethodsSync(query));
    }

    // Common methods
    private PaymentMethodsQuery createPaymentMethodsQuery() {
        return PaymentMethodsQuery.builder()
                .processingChannelId("pc_12345678901234567890123456")
                .build();
    }

    private <T> void validateResponse(final T expectedResponse, final T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }
}