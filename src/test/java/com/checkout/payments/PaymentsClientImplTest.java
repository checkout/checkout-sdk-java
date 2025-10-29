package com.checkout.payments;

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
import com.checkout.payments.response.PaymentsQueryResponse;
import com.checkout.payments.request.AuthorizationRequest;
import com.checkout.payments.request.PaymentRequest;
import com.checkout.payments.request.PayoutRequest;
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
import com.checkout.payments.response.PayoutResponse;
import com.checkout.payments.sender.PaymentInstrumentSender;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
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

        final RequestIdSource source = mock(RequestIdSource.class);
        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPaymentWithCustomApmSource() throws ExecutionException, InterruptedException {

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

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final RequestCardSource source = mock(RequestCardSource.class);
        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), eq("1234")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request, "1234");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout() throws ExecutionException, InterruptedException {

        final PayoutRequestCurrencyAccountSource source = mock(PayoutRequestCurrencyAccountSource.class);
        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PayoutResponse response = mock(PayoutResponse.class);

        final PayoutRequest request = PayoutRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayout_idempotencyKey() throws ExecutionException, InterruptedException {

        final PayoutRequestCurrencyAccountSource source = mock(PayoutRequestCurrencyAccountSource.class);
        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PayoutResponse response = mock(PayoutResponse.class);

        final PayoutRequest request = PayoutRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PayoutResponse.class), eq(request), eq("456")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PayoutResponse> future = paymentsClient.requestPayout(request, "456");

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
    void shouldIncrementPaymentAuthorization() throws ExecutionException, InterruptedException {

        final AuthorizationRequest request = mock(AuthorizationRequest.class);
        final AuthorizationResponse response = mock(AuthorizationResponse.class);

        when(apiClient.postAsync(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AuthorizationResponse> future = paymentsClient.incrementPaymentAuthorization("payment_id", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldIncrementPaymentAuthorization_idempotencyKey() throws ExecutionException, InterruptedException {

        final AuthorizationRequest request = mock(AuthorizationRequest.class);
        final AuthorizationResponse response = mock(AuthorizationResponse.class);

        when(apiClient.postAsync(eq("payments/payment_id/authorizations"), any(SdkAuthorization.class), eq(AuthorizationResponse.class), eq(request), eq("idempotency_key")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<AuthorizationResponse> future = paymentsClient.incrementPaymentAuthorization("payment_id", request, "idempotency_key");

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
    void shouldReversePayment() throws ExecutionException, InterruptedException {

        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldReversePayment_idempotencyKey() throws ExecutionException, InterruptedException {

        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), isNull(), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", "123");

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldReversePayment_request() throws ExecutionException, InterruptedException {

        final ReverseRequest request = new ReverseRequest();
        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldReversePayment_request_idempotencyKey() throws ExecutionException, InterruptedException {

        final ReverseRequest request = new ReverseRequest();
        final ReverseResponse response = new ReverseResponse();

        when(apiClient.postAsync(eq("payments/123456/reversals"), any(SdkAuthorization.class), eq(ReverseResponse.class), eq(request), eq("123")))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<ReverseResponse> future = paymentsClient.reversePayment("123456", request, "123");

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

    @Test
    void shouldRequestProviderTokenSourcePayment() throws ExecutionException, InterruptedException {

        final PaymentInstrumentSender sender = mock(PaymentInstrumentSender.class);
        final PaymentResponse response = mock(PaymentResponse.class);

        final RequestProviderTokenSource source = RequestProviderTokenSource.builder()
                .token("token")
                .paymentMethod("method")
                .accountHolder(mock(AccountHolder.class))
                .build();

        final PaymentRequest request = PaymentRequest.builder().sender(sender).source(source).build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());

    }

    @Test
    void shouldRequestPayPalSourcePayment() throws ExecutionException, InterruptedException {
        final PaymentResponse response = mock(PaymentResponse.class);
        final PaymentRequest request = PaymentRequest.builder()
                .source(new RequestPayPalSource())
                .build();

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

    @Test
    void shouldRequestBankAccountPayment() throws ExecutionException, InterruptedException {
        final PaymentResponse response = mock(PaymentResponse.class);
        final PaymentRequest request = PaymentRequest.builder()
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

        when(apiClient.postAsync(eq("payments"), any(SdkAuthorization.class), eq(PaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentResponse> future = paymentsClient.requestPayment(request);

        assertNotNull(future.get());
        assertEquals(response, future.get());
    }

}