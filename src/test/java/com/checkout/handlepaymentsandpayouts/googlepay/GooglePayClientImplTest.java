package com.checkout.handlepaymentsandpayouts.googlepay;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayEnrollmentRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.requests.GooglePayRegisterDomainRequest;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayDomainListResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentResponse;
import com.checkout.handlepaymentsandpayouts.googlepay.responses.GooglePayEnrollmentStateResponse;
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
public class GooglePayClientImplTest {

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization oAuthAuthorization;

    private GooglePayClient client;

    @BeforeEach
    void setUp() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(oAuthAuthorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new GooglePayClientImpl(apiClient, configuration);
    }

    // Async

    @Test
    void shouldEnrollEntity() throws ExecutionException, InterruptedException {
        final GooglePayEnrollmentRequest request = createEnrollmentRequest();
        final GooglePayEnrollmentResponse response = mock(GooglePayEnrollmentResponse.class);

        when(apiClient.postAsync(eq("googlepay/enrollments"), eq(oAuthAuthorization),
                eq(GooglePayEnrollmentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        validateEnrollmentResponse(response, client.enrollEntity(request).get());
    }

    @Test
    void shouldRegisterDomain() throws ExecutionException, InterruptedException {
        final GooglePayRegisterDomainRequest request = mock(GooglePayRegisterDomainRequest.class);
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.postAsync(eq("googlepay/enrollments/ent_123/domain"), eq(oAuthAuthorization),
                eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        validateEmptyResponse(response, client.registerDomain("ent_123", request).get());
    }

    @Test
    void shouldGetRegisteredDomains() throws ExecutionException, InterruptedException {
        final GooglePayDomainListResponse response = mock(GooglePayDomainListResponse.class);

        when(apiClient.getAsync(eq("googlepay/enrollments/ent_123/domains"), eq(oAuthAuthorization),
                eq(GooglePayDomainListResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        validateDomainsResponse(response, client.getRegisteredDomains("ent_123").get());
    }

    @Test
    void shouldGetEnrollmentState() throws ExecutionException, InterruptedException {
        final GooglePayEnrollmentStateResponse response = mock(GooglePayEnrollmentStateResponse.class);

        when(apiClient.getAsync(eq("googlepay/enrollments/ent_123/state"), eq(oAuthAuthorization),
                eq(GooglePayEnrollmentStateResponse.class)))
                .thenReturn(CompletableFuture.completedFuture(response));

        validateEnrollmentStateResponse(response, client.getEnrollmentState("ent_123").get());
    }

    // Sync

    @Test
    void shouldEnrollEntitySync() {
        final GooglePayEnrollmentRequest request = createEnrollmentRequest();
        final GooglePayEnrollmentResponse response = mock(GooglePayEnrollmentResponse.class);

        when(apiClient.post(eq("googlepay/enrollments"), eq(oAuthAuthorization),
                eq(GooglePayEnrollmentResponse.class), eq(request), isNull()))
                .thenReturn(response);

        validateEnrollmentResponse(response, client.enrollEntitySync(request));
    }

    @Test
    void shouldRegisterDomainSync() {
        final GooglePayRegisterDomainRequest request = mock(GooglePayRegisterDomainRequest.class);
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.post(eq("googlepay/enrollments/ent_123/domain"), eq(oAuthAuthorization),
                eq(EmptyResponse.class), eq(request), isNull()))
                .thenReturn(response);

        validateEmptyResponse(response, client.registerDomainSync("ent_123", request));
    }

    @Test
    void shouldGetRegisteredDomainsSync() {
        final GooglePayDomainListResponse response = mock(GooglePayDomainListResponse.class);

        when(apiClient.get(eq("googlepay/enrollments/ent_123/domains"), eq(oAuthAuthorization),
                eq(GooglePayDomainListResponse.class)))
                .thenReturn(response);

        validateDomainsResponse(response, client.getRegisteredDomainsSync("ent_123"));
    }

    @Test
    void shouldGetEnrollmentStateSync() {
        final GooglePayEnrollmentStateResponse response = mock(GooglePayEnrollmentStateResponse.class);

        when(apiClient.get(eq("googlepay/enrollments/ent_123/state"), eq(oAuthAuthorization),
                eq(GooglePayEnrollmentStateResponse.class)))
                .thenReturn(response);

        validateEnrollmentStateResponse(response, client.getEnrollmentStateSync("ent_123"));
    }

    // Common methods

    private GooglePayEnrollmentRequest createEnrollmentRequest() {
        return GooglePayEnrollmentRequest.builder()
                .entityId("ent_123")
                .emailAddress("merchant@example.com")
                .acceptTermsOfService(true)
                .build();
    }

    private void validateEnrollmentResponse(final GooglePayEnrollmentResponse expected, final GooglePayEnrollmentResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEmptyResponse(final EmptyResponse expected, final EmptyResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateDomainsResponse(final GooglePayDomainListResponse expected, final GooglePayDomainListResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateEnrollmentStateResponse(final GooglePayEnrollmentStateResponse expected, final GooglePayEnrollmentStateResponse actual) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}
