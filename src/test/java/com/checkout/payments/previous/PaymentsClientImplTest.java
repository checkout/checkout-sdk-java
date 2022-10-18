package com.checkout.payments.previous;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.Currency;
import com.checkout.common.PaymentSourceType;
import com.checkout.payments.*;
import com.checkout.payments.previous.request.PaymentRequest;
import com.checkout.payments.previous.request.PayoutRequest;
import com.checkout.payments.previous.request.source.AbstractRequestSource;
import com.checkout.payments.previous.response.GetPaymentResponse;
import com.checkout.payments.previous.response.PaymentResponse;
import com.checkout.payments.previous.response.PaymentsQueryResponse;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
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
class PaymentsClientImplTest {

    private static final Type PAYMENT_ACTIONS_TYPE = new TypeToken<ItemsResponse<PaymentAction>>() {
    }.getType();

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        paymentsClient = new PaymentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPayment() throws ExecutionException, InterruptedException {

        final PaymentRequest request = mock(PaymentRequest.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayment_customSource() throws ExecutionException, InterruptedException {

        final CustomSource customSource = new CustomSource(10, Currency.USD);

        final PaymentRequest request = PaymentRequest.builder().source(customSource).build();
        final PaymentResponse response = mock(PaymentResponse.class);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Getter
    @Setter
    private static class CustomSource extends AbstractRequestSource {

        private long amount;
        private Currency currency;

        protected CustomSource(final long amount,
                               final Currency currency) {

            super(PaymentSourceType.ALIPAY);
            this.amount = amount;
            this.currency = currency;
        }
    }

    @Test
    void shouldRequestPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final PaymentRequest request = mock(PaymentRequest.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), eq("1234")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request, "1234");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout() throws ExecutionException, InterruptedException {

        final PayoutRequest request = mock(PayoutRequest.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayout(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout_idempotencyKey() throws ExecutionException, InterruptedException {

        final PayoutRequest request = mock(PayoutRequest.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), eq("456")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayout(request, "456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetPaymentsList() throws ExecutionException, InterruptedException {
        final PaymentsQueryFilter query = mock(PaymentsQueryFilter.class);
        final PaymentsQueryResponse response = mock(PaymentsQueryResponse.class);

        when(apiClient.queryAsync(eq("payments"), any(SdkAuthorization.class), eq(query), eq(PaymentsQueryResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentsQueryResponse> future = paymentsClient.getPaymentsList(query);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldGetPayment() throws ExecutionException, InterruptedException {

        final GetPaymentResponse response = mock(GetPaymentResponse.class);

        when(apiClient.getAsync(eq("payments/12345678"), any(SdkAuthorization.class), eq(GetPaymentResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetPaymentResponse> future = paymentsClient.getPayment("12345678");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetPaymentActions() throws ExecutionException, InterruptedException {

        final ItemsResponse response = mock(ItemsResponse.class);

        when(apiClient.getAsync(eq("payments/5433211/actions"), any(SdkAuthorization.class), eq(PAYMENT_ACTIONS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<PaymentAction>> future = paymentsClient.getPaymentActions("5433211");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_request() throws ExecutionException, InterruptedException {

        final CaptureRequest request = CaptureRequest.builder().build();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final CaptureRequest request = CaptureRequest.builder().build();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_request() throws ExecutionException, InterruptedException {

        final RefundRequest request = RefundRequest.builder().build();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final RefundRequest request = RefundRequest.builder().build();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_request() throws ExecutionException, InterruptedException {

        final VoidRequest request = VoidRequest.builder().build();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final VoidRequest request = VoidRequest.builder().build();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}