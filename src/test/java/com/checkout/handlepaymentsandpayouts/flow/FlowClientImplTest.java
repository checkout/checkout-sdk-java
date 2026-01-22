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
        final PaymentSessionRequest request = mock(PaymentSessionRequest.class);
        final PaymentSessionResponse response = mock(PaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions"), eq(authorization), eq(PaymentSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));
        
        final CompletableFuture<PaymentSessionResponse> future = client.requestPaymentSession(request);

        verifySuccessfulResponse(future, response);
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

        verifySuccessfulResponse(future, response);
    }

    @Test
    void shouldSubmitPaymentSessions() throws ExecutionException, InterruptedException {
        final String paymentId = "pay_mbabizu24mvu3mela5njyhpit4";
        final SubmitPaymentSessionRequest request = mock(SubmitPaymentSessionRequest.class);
        final SubmitPaymentSessionResponse response = mock(SubmitPaymentSessionResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/" + paymentId + "/submit"), eq(authorization), eq(SubmitPaymentSessionResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<SubmitPaymentSessionResponse> future =
                client.submitPaymentSessions(paymentId, request);

        verifySuccessfulResponse(future, response);
    }

    @Test
    void shouldRequestPaymentSessionWithPayment() throws ExecutionException, InterruptedException {
        final PaymentSessionWithPaymentRequest request = mock(PaymentSessionWithPaymentRequest.class);
        final PaymentSessionWithPaymentResponse response = mock(PaymentSessionWithPaymentResponse.class);

        when(apiClient.postAsync(eq("payment-sessions/complete"), eq(authorization), eq(PaymentSessionWithPaymentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PaymentSessionWithPaymentResponse> future =
                client.requestPaymentSessionWithPayment(request);

        verifySuccessfulResponse(future, response);
    }

    // common methods
    private static Address createStandardAddress() {
        return Address.builder()
                .addressLine1("123 Main Street")
                .addressLine2("Apt 4B")
                .city("New York")
                .state("NY")
                .zip("10001")
                .country(CountryCode.US)
                .build();
    }

    private static Address createSenderAddress() {
        return Address.builder()
                .addressLine1("456 Elm Street")
                .addressLine2("Suite 200")
                .city("Boston")
                .state("MA")
                .zip("02101")
                .country(CountryCode.US)
                .build();
    }

    private static Phone createStandardPhone() {
        return Phone.builder()
                .countryCode("+1")
                .number("5551234567")
                .build();
    }

    private static Billing createStandardBilling() {
        return Billing.builder()
                .address(createStandardAddress())
                .phone(createStandardPhone())
                .build();
    }

    private static Identification createDrivingLicenceIdentification() {
        return Identification.builder()
                .type(IdentificationType.DRIVING_LICENCE)
                .number("D123456789")
                .issuingCountry("US")
                .build();
    }

    private static IndividualSender createIndividualSender() {
        return IndividualSender.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth("1990-05-15")
                .address(createSenderAddress())
                .identification(createDrivingLicenceIdentification())
                .reference("SENDER-REF-001")
                .build();
    }

    // Test utility methods
    private void verifySuccessfulResponse(CompletableFuture<?> future, Object expectedResponse) 
            throws ExecutionException, InterruptedException {
        assertNotNull(future.get());
        assertEquals(expectedResponse, future.get());
    }
}
