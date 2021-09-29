package com.checkout.payments.four;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.payments.four.action.PaymentAction;
import com.checkout.payments.four.capture.CaptureRequest;
import com.checkout.payments.four.capture.CaptureResponse;
import com.checkout.payments.four.payout.PayoutRequest;
import com.checkout.payments.four.payout.PayoutResponse;
import com.checkout.payments.four.payout.source.RequestCurrencyAccountSource;
import com.checkout.payments.four.refund.RefundRequest;
import com.checkout.payments.four.refund.RefundResponse;
import com.checkout.payments.four.request.PaymentRequest;
import com.checkout.payments.four.request.source.RequestCardSource;
import com.checkout.payments.four.request.source.RequestIdSource;
import com.checkout.payments.four.response.PaymentResponse;
import com.checkout.payments.four.response.source.ResponseCardSource;
import com.checkout.payments.four.response.source.ResponseCurrencyAccountSource;
import com.checkout.payments.four.response.source.ResponseIdSource;
import com.checkout.payments.four.response.source.ResponseNetworkTokenSource;
import com.checkout.payments.four.response.source.ResponseTokenSource;
import com.checkout.payments.four.sender.RequestInstrumentSender;
import com.checkout.payments.four.voids.VoidRequest;
import com.checkout.payments.four.voids.VoidResponse;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
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

    private static final String PAYMENTS_PATH = "/payments";
    private static final String ACTIONS_PATH = "/actions";
    private static final String CAPTURES_PATH = "/captures";
    private static final String REFUNDS_PATH = "/refunds";
    private static final String VOIDS_PATH = "/voids";

    private static final Type PAYMENT_TYPE = TypeToken.getParameterized(PaymentResponse.class,
            ResponseCardSource.class,
            ResponseCurrencyAccountSource.class,
            ResponseIdSource.class,
            ResponseNetworkTokenSource.class,
            ResponseTokenSource.class
    ).getType();

    private static final Type PAYMENT_ACTION_TYPE = new TypeToken<List<PaymentAction>>() {
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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        paymentsClient = new PaymentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPayment() throws ExecutionException, InterruptedException {

        final RequestIdSource source = mock(RequestIdSource.class);
        final RequestInstrumentSender sender = mock(RequestInstrumentSender.class);
        final PaymentResponse<ResponseIdSource> response = (PaymentResponse<ResponseIdSource>) mock(PaymentResponse.class);

        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq(PAYMENTS_PATH), any(SdkAuthorization.class), eq(PAYMENT_TYPE), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse<ResponseIdSource>> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final RequestCardSource source = mock(RequestCardSource.class);
        final RequestInstrumentSender sender = mock(RequestInstrumentSender.class);
        final PaymentResponse<ResponseCardSource> response = (PaymentResponse<ResponseCardSource>) mock(PaymentResponse.class);

        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq(PAYMENTS_PATH), any(SdkAuthorization.class), eq(PAYMENT_TYPE), eq(request), eq("1234")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse<ResponseCardSource>> future = paymentsClient.requestPayment(request, "1234");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout() throws ExecutionException, InterruptedException {

        final RequestCurrencyAccountSource source = mock(RequestCurrencyAccountSource.class);
        final RequestInstrumentSender sender = mock(RequestInstrumentSender.class);
        final PayoutResponse response = mock(PayoutResponse.class);

        final PayoutRequest request = PayoutRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq(PAYMENTS_PATH), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout_idempotencyKey() throws ExecutionException, InterruptedException {

        final RequestCurrencyAccountSource source = mock(RequestCurrencyAccountSource.class);
        final RequestInstrumentSender sender = mock(RequestInstrumentSender.class);
        final PayoutResponse response = mock(PayoutResponse.class);

        final PayoutRequest request = PayoutRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq(PAYMENTS_PATH), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), eq("456")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request, "456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetPayment() throws ExecutionException, InterruptedException {

        final PaymentResponse<ResponseIdSource> response = (PaymentResponse<ResponseIdSource>) mock(PaymentResponse.class);

        when(apiClient.getAsync(eq(PAYMENTS_PATH + "/12345678"), any(SdkAuthorization.class), eq(PAYMENT_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse<ResponseIdSource>> future = paymentsClient.getPayment("12345678");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldGetPaymentActions() throws ExecutionException, InterruptedException {

        final List<PaymentAction> response = Arrays.asList(new PaymentAction(), new PaymentAction());

        when(apiClient.getAsync(eq(PAYMENTS_PATH + "/5433211" + ACTIONS_PATH), any(SdkAuthorization.class), eq(PAYMENT_ACTION_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<List<PaymentAction>> future = paymentsClient.getPaymentActions("5433211");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + CAPTURES_PATH), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + CAPTURES_PATH), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_request() throws ExecutionException, InterruptedException {

        final CaptureRequest request = CaptureRequest.builder().build();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + CAPTURES_PATH), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldCapturePayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final CaptureRequest request = CaptureRequest.builder().build();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + CAPTURES_PATH), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + REFUNDS_PATH), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + REFUNDS_PATH), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_request() throws ExecutionException, InterruptedException {

        final RefundRequest request = RefundRequest.builder().build();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + REFUNDS_PATH), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRefundPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final RefundRequest request = RefundRequest.builder().build();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + REFUNDS_PATH), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + VOIDS_PATH), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + VOIDS_PATH), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", "456789");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_request() throws ExecutionException, InterruptedException {

        final VoidRequest request = VoidRequest.builder().build();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + VOIDS_PATH), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldVoidPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final VoidRequest request = VoidRequest.builder().build();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq(PAYMENTS_PATH + "/123456" + VOIDS_PATH), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request, "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

}