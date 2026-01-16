package com.checkout.payments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.ItemsResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.AccountHolder;
import com.checkout.common.AccountType;
import com.checkout.common.Address;
import com.checkout.common.CountryCode;
import com.checkout.common.CustomerResponse;
import com.checkout.common.Phone;
import com.checkout.payments.request.AuthorizationRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.PayoutRequest;
import com.checkout.payments.request.source.AbstractRequestSource;
import com.checkout.payments.request.source.PayoutRequestCurrencyAccountSource;
import com.checkout.payments.request.source.RequestBankAccountSource;
import com.checkout.payments.request.source.RequestCardSource;
import com.checkout.payments.request.source.RequestIdSource;
import com.checkout.payments.request.source.RequestProviderTokenSource;
import com.checkout.payments.request.source.apm.RequestPayPalSource;
import com.checkout.payments.request.source.apm.RequestTamaraSource;
import com.checkout.payments.response.AuthorizationResponse;
import com.checkout.payments.response.GetPaymentResponse;
import com.checkout.payments.response.PaymentResponse;
import com.checkout.payments.response.PaymentsQueryResponse;
import com.checkout.payments.response.PayoutResponse;
import com.checkout.payments.sender.PaymentInstrumentSender;
import com.google.gson.reflect.TypeToken;

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
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        paymentsClient = new PaymentsClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPayment() throws ExecutionException, InterruptedException {
        final RequestIdSource source = createIdSource();
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse response = createPaymentResponse();
        final PaymentRequest request = createPaymentRequest(sender, source);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestPaymentWithCustomApmSource() throws ExecutionException, InterruptedException {
        final PaymentRequest request = createTamaraPaymentRequest();
        final PaymentResponse response = createTamaraPaymentResponse();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestPayment_idempotencyKey() throws ExecutionException, InterruptedException {
        final RequestCardSource source = createCardSource();
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse response = createPaymentResponse();
        final PaymentRequest request = createPaymentRequest(sender, source);

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), eq("1234")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request, "1234");
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestPayout() throws ExecutionException, InterruptedException {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse response = createPayoutResponse();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request);
        final PayoutResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestPayout_idempotencyKey() throws ExecutionException, InterruptedException {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse response = createPayoutResponse();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), eq("456")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request, "456");
        final PayoutResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldGetPaymentsList() throws ExecutionException, InterruptedException {
        final PaymentsQueryFilter query = createQueryFilter();
        final PaymentsQueryResponse response = createPaymentsQueryResponse();

        when(apiClient.queryAsync(eq("payments"), any(SdkAuthorization.class), eq(query), eq(PaymentsQueryResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentsQueryResponse> future = paymentsClient.getPaymentsList(query);
        final PaymentsQueryResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldGetPayment() throws ExecutionException, InterruptedException {

        final GetPaymentResponse response = createGetPaymentResponse();

        when(apiClient.getAsync(eq("payments/12345678"), any(SdkAuthorization.class), eq(GetPaymentResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GetPaymentResponse> future = paymentsClient.getPayment("12345678");
        final GetPaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldGetPaymentActions() throws ExecutionException, InterruptedException {

        final ItemsResponse<PaymentAction> response = createPaymentActionsResponse();

        when(apiClient.getAsync(eq("payments/5433211/actions"), any(SdkAuthorization.class), eq(PAYMENT_ACTIONS_TYPE)))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ItemsResponse<PaymentAction>> future = paymentsClient.getPaymentActions("5433211");
        final ItemsResponse<PaymentAction> actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldIncrementPaymentAuthorization() throws ExecutionException, InterruptedException {
        final AuthorizationRequest request = createAuthorizationRequest();
        final AuthorizationResponse response = createAuthorizationResponse();

        when(apiClient.postAsync(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AuthorizationResponse> future = paymentsClient.incrementPaymentAuthorization("payment_id", request);
        final AuthorizationResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldIncrementPaymentAuthorization_idempotencyKey() throws ExecutionException, InterruptedException {
        final AuthorizationRequest request = createAuthorizationRequest();
        final AuthorizationResponse response = createAuthorizationResponse();

        when(apiClient.postAsync(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), eq("idempotency_key")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AuthorizationResponse> future = paymentsClient.incrementPaymentAuthorization("payment_id", request, "idempotency_key");
        final AuthorizationResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldCapturePayment() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456");
        final CaptureResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldCapturePayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", "456789");
        final CaptureResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldCapturePayment_request() throws ExecutionException, InterruptedException {
        final CaptureRequest request = createCaptureRequest();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request);
        final CaptureResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldCapturePayment_request_idempotencyKey() throws ExecutionException, InterruptedException {
        final CaptureRequest request = createCaptureRequest();
        final CaptureResponse response = new CaptureResponse();

        when(apiClient.postAsync(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<CaptureResponse> future = paymentsClient.capturePayment("123456", request, "123");
        final CaptureResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRefundPayment() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456");
        final RefundResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRefundPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", "456789");
        final RefundResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldRefundPayment_request() throws ExecutionException, InterruptedException {
        final RefundRequest request = createRefundRequest();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request);
        final RefundResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRefundPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {
        final RefundRequest request = createRefundRequest();
        final RefundResponse response = new RefundResponse();

        when(apiClient.postAsync(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<RefundResponse> future = paymentsClient.refundPayment("123456", request, "123");
        final RefundResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldReversePayment() throws ExecutionException, InterruptedException {

        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456");
        final ReverseResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldReversePayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", "123");
        final ReverseResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldReversePayment_request() throws ExecutionException, InterruptedException {
        final ReverseRequest request = createReverseRequest();
        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", request);
        final ReverseResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldReversePayment_request_idempotencyKey() throws ExecutionException, InterruptedException {
        final ReverseRequest request = createReverseRequest();
        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", request, "123");
        final ReverseResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldVoidPayment() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456");
        final VoidResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldVoidPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), eq("456789")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", "456789");
        final VoidResponse actualResponse = future.get();

        validateResponse(response, actualResponse);

    }

    @Test
    void shouldVoidPayment_request() throws ExecutionException, InterruptedException {
        final VoidRequest request = createVoidRequest();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request);
        final VoidResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldVoidPayment_request_idempotencyKey() throws ExecutionException, InterruptedException {
        final VoidRequest request = createVoidRequest();
        final VoidResponse response = new VoidResponse();

        when(apiClient.postAsync(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<VoidResponse> future = paymentsClient.voidPayment("123456", request, "123");
        final VoidResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestProviderTokenSourcePayment() throws ExecutionException, InterruptedException {
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse response = createPaymentResponse();
        final RequestProviderTokenSource source = createProviderTokenSource();
        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestPayPalSourcePayment() throws ExecutionException, InterruptedException {
        final PaymentResponse response = createPaymentResponse();
        final PaymentRequest request = createPayPalPaymentRequest();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    @Test
    void shouldRequestBankAccountPayment() throws ExecutionException, InterruptedException {
        final PaymentResponse response = createPaymentResponse();
        final PaymentRequest request = createBankAccountPaymentRequest();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);
        final PaymentResponse actualResponse = future.get();

        validateResponse(response, actualResponse);
    }

    // Synchronous methods
    @Test
    void shouldRequestPaymentSync() {
        final RequestIdSource source = createIdSource();
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse expectedResponse = createPaymentResponse();
        final PaymentRequest request = createPaymentRequest(sender, source);

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPaymentSync_idempotencyKey() {
        final RequestCardSource source = createCardSource();
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse expectedResponse = createPaymentResponse();
        final PaymentRequest request = createPaymentRequest(sender, source);

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), eq("1234")))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request, "1234");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPaymentWithCustomApmSourceSync() {
        final PaymentRequest request = createTamaraPaymentRequest();
        final PaymentResponse expectedResponse = createTamaraPaymentResponse();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPayoutSync() {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse expectedResponse = createPayoutResponse();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PayoutResponse actualResponse = paymentsClient.requestPayoutSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPayoutSync_idempotencyKey() {
        final PayoutRequest request = createPayoutRequest();
        final PayoutResponse expectedResponse = createPayoutResponse();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), eq("1234")))
                .thenReturn(expectedResponse);

        final PayoutResponse actualResponse = paymentsClient.requestPayoutSync(request, "1234");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetPaymentsListSync() {
        final PaymentsQueryFilter query = createQueryFilter();
        final PaymentsQueryResponse expectedResponse = createPaymentsQueryResponse();

        when(apiClient.query(eq("payments"), any(SdkAuthorization.class), eq(query), eq(PaymentsQueryResponse.class)))
                .thenReturn(expectedResponse);

        final PaymentsQueryResponse actualResponse = paymentsClient.getPaymentsListSync(query);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetPaymentSync() {
        final GetPaymentResponse expectedResponse = createGetPaymentResponse();

        when(apiClient.get(eq("payments/12345678"), any(SdkAuthorization.class), eq(GetPaymentResponse.class)))
                .thenReturn(expectedResponse);

        final GetPaymentResponse actualResponse = paymentsClient.getPaymentSync("12345678");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldGetPaymentActionsSync() {
        final ItemsResponse<PaymentAction> expectedResponse = createPaymentActionsResponse();

        when(apiClient.get(eq("payments/5433211/actions"), any(SdkAuthorization.class), eq(PAYMENT_ACTIONS_TYPE)))
                .thenReturn(expectedResponse);

        final ItemsResponse<PaymentAction> actualResponse = paymentsClient.getPaymentActionsSync("5433211");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldIncrementPaymentAuthorizationSync() {
        final AuthorizationRequest request = createAuthorizationRequest();
        final AuthorizationResponse expectedResponse = createAuthorizationResponse();

        when(apiClient.post(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final AuthorizationResponse actualResponse = paymentsClient.incrementPaymentAuthorizationSync("payment_id", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldIncrementPaymentAuthorizationSync_idempotencyKey() {
        final AuthorizationRequest request = createAuthorizationRequest();
        final AuthorizationResponse expectedResponse = createAuthorizationResponse();

        when(apiClient.post(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), eq("456")))
                .thenReturn(expectedResponse);

        final AuthorizationResponse actualResponse = paymentsClient.incrementPaymentAuthorizationSync("payment_id", request, "456");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync() {
        final CaptureResponse expectedResponse = new CaptureResponse();

        when(apiClient.post(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = paymentsClient.capturePaymentSync("123456");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync_idempotencyKey() {
        final CaptureResponse expectedResponse = new CaptureResponse();

        when(apiClient.post(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), isNull(), eq("456789")))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = paymentsClient.capturePaymentSync("123456", "456789");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync_request() {
        final CaptureRequest request = createCaptureRequest();
        final CaptureResponse expectedResponse = new CaptureResponse();

        when(apiClient.post(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = paymentsClient.capturePaymentSync("123456", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldCapturePaymentSync_request_idempotencyKey() {
        final CaptureRequest request = createCaptureRequest();
        final CaptureResponse expectedResponse = new CaptureResponse();

        when(apiClient.post(eq("payments/123456/captures"), any(SdkAuthorization.class), eq(CaptureResponse.class), eq(request), eq("123")))
                .thenReturn(expectedResponse);

        final CaptureResponse actualResponse = paymentsClient.capturePaymentSync("123456", request, "123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRefundPaymentSync() {
        final RefundResponse expectedResponse = new RefundResponse();

        when(apiClient.post(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final RefundResponse actualResponse = paymentsClient.refundPaymentSync("123456");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRefundPaymentSync_idempotencyKey() {
        final RefundResponse expectedResponse = new RefundResponse();

        when(apiClient.post(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), isNull(), eq("456789")))
                .thenReturn(expectedResponse);

        final RefundResponse actualResponse = paymentsClient.refundPaymentSync("123456", "456789");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRefundPaymentSync_request() {
        final RefundRequest request = createRefundRequest();
        final RefundResponse expectedResponse = new RefundResponse();

        when(apiClient.post(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final RefundResponse actualResponse = paymentsClient.refundPaymentSync("123456", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRefundPaymentSync_request_idempotencyKey() {
        final RefundRequest request = createRefundRequest();
        final RefundResponse expectedResponse = new RefundResponse();

        when(apiClient.post(eq("payments/123456/refunds"), any(SdkAuthorization.class), eq(RefundResponse.class), eq(request), eq("123")))
                .thenReturn(expectedResponse);

        final RefundResponse actualResponse = paymentsClient.refundPaymentSync("123456", request, "123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldReversePaymentSync() {
        final ReverseResponse expectedResponse = new ReverseResponse();

        when(apiClient.post(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final ReverseResponse actualResponse = paymentsClient.reversePaymentSync("123456");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldReversePaymentSync_idempotencyKey() {
        final ReverseResponse expectedResponse = new ReverseResponse();

        when(apiClient.post(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), eq("456789")))
                .thenReturn(expectedResponse);

        final ReverseResponse actualResponse = paymentsClient.reversePaymentSync("123456", "456789");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldReversePaymentSync_request() {
        final ReverseRequest request = createReverseRequest();
        final ReverseResponse expectedResponse = new ReverseResponse();

        when(apiClient.post(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final ReverseResponse actualResponse = paymentsClient.reversePaymentSync("123456", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldReversePaymentSync_request_idempotencyKey() {
        final ReverseRequest request = createReverseRequest();
        final ReverseResponse expectedResponse = new ReverseResponse();

        when(apiClient.post(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), eq("123")))
                .thenReturn(expectedResponse);

        final ReverseResponse actualResponse = paymentsClient.reversePaymentSync("123456", request, "123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidPaymentSync() {
        final VoidResponse expectedResponse = new VoidResponse();

        when(apiClient.post(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), isNull()))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = paymentsClient.voidPaymentSync("123456");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidPaymentSync_idempotencyKey() {
        final VoidResponse expectedResponse = new VoidResponse();

        when(apiClient.post(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), isNull(), eq("456789")))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = paymentsClient.voidPaymentSync("123456", "456789");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidPaymentSync_request() {
        final VoidRequest request = createVoidRequest();
        final VoidResponse expectedResponse = new VoidResponse();

        when(apiClient.post(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = paymentsClient.voidPaymentSync("123456", request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldVoidPaymentSync_request_idempotencyKey() {
        final VoidRequest request = createVoidRequest();
        final VoidResponse expectedResponse = new VoidResponse();

        when(apiClient.post(eq("payments/123456/voids"), any(SdkAuthorization.class), eq(VoidResponse.class), eq(request), eq("123")))
                .thenReturn(expectedResponse);

        final VoidResponse actualResponse = paymentsClient.voidPaymentSync("123456", request, "123");

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestProviderTokenSourcePaymentSync() {
        final PaymentInstrumentSender sender = createPaymentSender();
        final PaymentResponse expectedResponse = createPaymentResponse();
        final RequestProviderTokenSource source = createProviderTokenSource();
        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestPayPalSourcePaymentSync() {
        final PaymentResponse expectedResponse = createPaymentResponse();
        final PaymentRequest request = createPayPalPaymentRequest();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    @Test
    void shouldRequestBankAccountPaymentSync() {
        final PaymentResponse expectedResponse = createPaymentResponse();
        final PaymentRequest request = createBankAccountPaymentRequest();

        when(apiClient.post(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(expectedResponse);

        final PaymentResponse actualResponse = paymentsClient.requestPaymentSync(request);

        validateResponse(expectedResponse, actualResponse);
    }

    // Common methods
    private RequestIdSource createIdSource() {
        return mock(RequestIdSource.class);
    }

    private RequestCardSource createCardSource() {
        return mock(RequestCardSource.class);
    }

    private PaymentInstrumentSender createPaymentSender() {
        return mock(PaymentInstrumentSender.class);
    }

    private PaymentRequest createPaymentRequest(PaymentInstrumentSender sender, AbstractRequestSource source) {
        return PaymentRequest.builder().sender(sender).source(source).build();
    }

    private PayoutRequest createPayoutRequest() {
        return PayoutRequest.builder()
                .source(PayoutRequestCurrencyAccountSource.builder().build())
                .build();
    }

    private PaymentsQueryFilter createQueryFilter() {
        return mock(PaymentsQueryFilter.class);
    }

    private AuthorizationRequest createAuthorizationRequest() {
        return mock(AuthorizationRequest.class);
    }

    private CaptureRequest createCaptureRequest() {
        return CaptureRequest.builder().build();
    }

    private RefundRequest createRefundRequest() {
        return RefundRequest.builder().build();
    }

    private ReverseRequest createReverseRequest() {
        return new ReverseRequest();
    }

    private VoidRequest createVoidRequest() {
        return VoidRequest.builder().build();
    }

    private RequestProviderTokenSource createProviderTokenSource() {
        return RequestProviderTokenSource.builder()
                .token("token")
                .paymentMethod("method")
                .accountHolder(mock(AccountHolder.class))
                .build();
    }

    private PaymentRequest createPayPalPaymentRequest() {
        return PaymentRequest.builder()
                .source(new RequestPayPalSource())
                .build();
    }

    private PaymentRequest createBankAccountPaymentRequest() {
        return PaymentRequest.builder()
                .source(RequestBankAccountSource.builder()
                        .paymentMethod("ach")
                        .accountType(AccountType.SAVINGS)
                        .country(CountryCode.US)
                        .accountNumber("1365456745")
                        .bankCode("011075150")
                        .accountHolder(AccountHolder.builder()
                                .build())
                        .build())
                .build();
    }

    private PaymentRequest createTamaraPaymentRequest() {
        final Address billingAddress = new Address();
        billingAddress.setAddressLine1("CheckoutSdk.com");
        billingAddress.setAddressLine2("90 Tottenham Court Road");
        billingAddress.setCity("London");
        billingAddress.setState("London");
        billingAddress.setZip("W1T 4TJ");
        billingAddress.setCountry(CountryCode.GB);

        final RequestTamaraSource source = new RequestTamaraSource();
        source.setBillingAddress(billingAddress);
        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PaymentRequest request = PaymentRequest.builder()
                .sender(sender)
                .source(source)
                .processing(ProcessingSettings.builder()
                        .build())
                .build();
        request.setItems(Collections.singletonList(ProductRequest.builder()
                .name("Item name")
                .quantity(3L)
                .unitPrice(100L)
                .totalAmount(100L)
                .taxAmount(19L)
                .discountAmount(2L)
                .reference("some description about item")
                .imageUrl("https://some_s3bucket.com")
                .url("https://some.website.com/item")
                .sku("123687000111")
                .wxpayGoodsId("wxpayGoodsId")
                .build()));
        return request;
    }

    private PaymentResponse createTamaraPaymentResponse() {
        final CustomerResponse customerResponse = new CustomerResponse();
        customerResponse.setEmail("email");
        customerResponse.setId("id");
        customerResponse.setName("name");
        customerResponse.setPhone(Phone.builder().build());

        final PaymentProcessing paymentProcessing = new PaymentProcessing();
        paymentProcessing.setPartnerPaymentId("1234567");

        final PaymentResponse response = new PaymentResponse();
        response.setCustomer(customerResponse);
        response.setProcessing(paymentProcessing);
        return response;
    }

    private PaymentResponse createPaymentResponse() {
        return mock(PaymentResponse.class);
    }

    private PayoutResponse createPayoutResponse() {
        return mock(PayoutResponse.class);
    }

    private PaymentsQueryResponse createPaymentsQueryResponse() {
        return mock(PaymentsQueryResponse.class);
    }

    private GetPaymentResponse createGetPaymentResponse() {
        return mock(GetPaymentResponse.class);
    }

    private AuthorizationResponse createAuthorizationResponse() {
        return mock(AuthorizationResponse.class);
    }

    @SuppressWarnings("unchecked")
    private ItemsResponse<PaymentAction> createPaymentActionsResponse() {
        return (ItemsResponse<PaymentAction>) mock(ItemsResponse.class);
    }

    private <T> void validateResponse(T expectedResponse, T actualResponse) {
        assertEquals(expectedResponse, actualResponse);
        assertNotNull(actualResponse);
    }

}