package com.checkout.handlepaymentsandpayouts.flow;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.common.CountryCode;
import com.checkout.common.Currency;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.IdentificationType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.LocaleType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.enums.PaymentType;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Address;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Billing;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Identification;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.PaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.Phone;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.requests.sender.IndividualSender;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessions.responses.PaymentSessionResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.requests.PaymentSessionWithPaymentRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionscomplete.responses.PaymentSessionWithPaymentResponse;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.requests.SubmitPaymentSessionRequest;
import com.checkout.handlepaymentsandpayouts.flow.paymentsessionssubmit.responses.SubmitPaymentSessionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FlowClientImplTest {

    private FlowClient client;

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
        client = new FlowClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPaymentSessions() throws ExecutionException, InterruptedException {
        final PaymentSessionRequest request = createPaymentSessionRequest();
        final PaymentSessionResponse response = createPaymentSessionResponse();

        when(apiClient.postAsync(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));
        
        final CompletableFuture<PaymentSessionResponse> future = client.requestPaymentSession(request);

        validatePaymentSessionResponse(future.get(), response);
    }

    @Test
    void shouldRequestPaymentSessionsWithFullIndividualSender() throws ExecutionException, InterruptedException {
        final PaymentSessionRequest request = PaymentSessionRequest.builder()
                .amount(1000L) // 10.00 in minor currency units
                .currency(Currency.USD)
                .billing(createStandardBilling())
                .successUrl("https://example.com/success")
                .failureUrl("https://example.com/failure")
                .paymentType(PaymentType.REGULAR)
                .reference("ORDER-12345")
                .description("Test payment for order")
                .sender(createIndividualSender())
                .capture(true)
                .locale(LocaleType.EN_GB)
                .build();

        final PaymentSessionResponse response = mock(PaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSessionResponse> future = client.requestPaymentSession(request);

        validatePaymentSessionResponse(future.get(), response);
    }

    @Test
    void shouldSubmitPaymentSessions() throws ExecutionException, InterruptedException {
        final String paymentId = "pay_mbabizu24mvu3mela5njyhpit4";
        final SubmitPaymentSessionRequest request = createSubmitPaymentSessionRequest();
        final SubmitPaymentSessionResponse response = createSubmitPaymentSessionResponse();

        when(apiClient.postAsync(eq("payment-sessions/" + paymentId + "/submit"), eq(authorization), eq(SubmitPaymentSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SubmitPaymentSessionResponse> future = client.submitPaymentSessions(paymentId, request);

        validateSubmitPaymentSessionResponse(future.get(), response);
    }

    @Test
    void shouldRequestPaymentSessionWithPayment() throws ExecutionException, InterruptedException {
        final PaymentSessionWithPaymentRequest request = createPaymentSessionWithPaymentRequest();
        final PaymentSessionWithPaymentResponse response = createPaymentSessionWithPaymentResponse();

        when(apiClient.postAsync(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSessionWithPaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSessionWithPaymentResponse> future = client.requestPaymentSessionWithPayment(request);

        validatePaymentSessionWithPaymentResponse(future.get(), response);
    }

    // Synchronous methods
    @Test
    void shouldRequestPaymentSessionsSync() throws ExecutionException, InterruptedException {

        final PaymentSessionRequest request = createPaymentSessionRequest();
        final PaymentSessionResponse response = createPaymentSessionResponse();

        when(apiClient.post(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        PaymentSessionResponse result = client.requestPaymentSessionSync(request);

        validatePaymentSessionResponse(result, response);

    }

    @Test
    void shouldSubmitPaymentSessionsSync() throws ExecutionException, InterruptedException {
        final String paymentId = "pay_mbabizu24mvu3mela5njyhpit4";
        final SubmitPaymentSessionRequest request = createSubmitPaymentSessionRequest();
        final SubmitPaymentSessionResponse response = createSubmitPaymentSessionResponse();

        when(apiClient.post(eq("payment-sessions/" + paymentId + "/submit"), eq(authorization), eq(SubmitPaymentSessionResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final SubmitPaymentSessionResponse result = client.submitPaymentSessionsSync(paymentId, request);

        validateSubmitPaymentSessionResponse(result, response);
    }

    @Test
    void shouldRequestPaymentSessionWithPaymentSync() throws ExecutionException, InterruptedException {
        final PaymentSessionWithPaymentRequest request = createPaymentSessionWithPaymentRequest();
        final PaymentSessionWithPaymentResponse response = createPaymentSessionWithPaymentResponse();

        when(apiClient.post(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSessionWithPaymentResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final PaymentSessionWithPaymentResponse result = client.requestPaymentSessionWithPaymentSync(request);

        validatePaymentSessionWithPaymentResponse(result, response);
    }

    // Common methods
    private IndividualSender createIndividualSender() {
        return IndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .address(Address.builder()
                        .addressLine1("123 Test Street")
                        .city("London")
                        .state("London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .identification(Identification.builder()
                        .type(IdentificationType.DRIVING_LICENCE)
                        .number("DL123456789")
                        .issuingCountry("GB")
                        .build())
                .reference("SENDER-REF-123")
                .build();
    }

    private Billing createStandardBilling() {
        return Billing.builder()
                .address(Address.builder()
                        .addressLine1("123 High St.")
                        .addressLine2("Flat 456")
                        .city("London")
                        .state("London")
                        .zip("SW1A 1AA")
                        .country(CountryCode.GB)
                        .build())
                .phone(Phone.builder()
                        .countryCode("+1")
                        .number("415 555 2671")
                        .build())
                .build();
    }

    private PaymentSessionRequest createPaymentSessionRequest() {
        return mock(PaymentSessionRequest.class);
    }

    private PaymentSessionResponse createPaymentSessionResponse() {
        return mock(PaymentSessionResponse.class);
    }
    private SubmitPaymentSessionRequest createSubmitPaymentSessionRequest() {
        return mock(SubmitPaymentSessionRequest.class);
    }
    private SubmitPaymentSessionResponse createSubmitPaymentSessionResponse() {
        return mock(SubmitPaymentSessionResponse.class);
    }
    private PaymentSessionWithPaymentRequest createPaymentSessionWithPaymentRequest() {
        return mock(PaymentSessionWithPaymentRequest.class);
    }
    private PaymentSessionWithPaymentResponse createPaymentSessionWithPaymentResponse() {
        return mock(PaymentSessionWithPaymentResponse.class);
    }

    private void validatePaymentSessionResponse(PaymentSessionResponse actual, PaymentSessionResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateSubmitPaymentSessionResponse(SubmitPaymentSessionResponse actual, SubmitPaymentSessionResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validatePaymentSessionWithPaymentResponse(PaymentSessionWithPaymentResponse actual, PaymentSessionWithPaymentResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
