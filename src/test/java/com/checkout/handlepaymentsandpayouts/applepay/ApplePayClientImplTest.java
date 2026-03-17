package com.checkout.handlepaymentsandpayouts.applepay;

import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.EmptyResponse;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.handlepaymentsandpayouts.applepay.entities.ProtocolVersions;
import com.checkout.handlepaymentsandpayouts.applepay.requests.EnrollDomainRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.GenerateSigningRequestRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.UploadCertificateRequest;
import com.checkout.handlepaymentsandpayouts.applepay.responses.GenerateSigningRequestResponse;
import com.checkout.handlepaymentsandpayouts.applepay.responses.UploadCertificateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplePayClientImplTest {

    private ApplePayClient client;

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    @Mock
    private SdkAuthorization oAuthAuthorization;

    @BeforeEach
    void setUp() {
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY_OR_OAUTH)).thenReturn(authorization);
        lenient().when(sdkCredentials.getAuthorization(SdkAuthorizationType.OAUTH)).thenReturn(oAuthAuthorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
        client = new ApplePayClientImpl(apiClient, configuration);
    }

    @Test
    void shouldUploadPaymentProcessingCertificate() throws ExecutionException, InterruptedException {

        final UploadCertificateRequest request = createUploadCertificateRequest();
        final UploadCertificateResponse response = mock(UploadCertificateResponse.class);

        when(apiClient.postAsync(eq("applepay/certificates"), eq(authorization), eq(UploadCertificateResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<UploadCertificateResponse> future = client.uploadPaymentProcessingCertificate(request);

        validateUploadCertificateResponse(response, future.get());
    }

    @Test
    void shouldEnrollDomain() throws ExecutionException, InterruptedException {

        final EnrollDomainRequest request = createEnrollDomainRequest();
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.postAsync(eq("applepay/enrollments"), eq(oAuthAuthorization), eq(EmptyResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<EmptyResponse> future = client.enrollDomain(request);

        validateEmptyResponse(response, future.get());
    }

    @Test
    void shouldGenerateCertificateSigningRequest() throws ExecutionException, InterruptedException {

        final GenerateSigningRequestRequest request = createGenerateSigningRequestRequest();
        final GenerateSigningRequestResponse response = mock(GenerateSigningRequestResponse.class);

        when(apiClient.postAsync(eq("applepay/signing-requests"), eq(authorization), eq(GenerateSigningRequestResponse.class),
                eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<GenerateSigningRequestResponse> future = client.generateCertificateSigningRequest(request);

        validateGenerateSigningRequestResponse(response, future.get());
    }

    // Synchronous methods
    @Test
    void shouldUploadPaymentProcessingCertificateSync() {

        final UploadCertificateRequest request = createUploadCertificateRequest();
        final UploadCertificateResponse response = mock(UploadCertificateResponse.class);

        when(apiClient.post(eq("applepay/certificates"), eq(authorization), eq(UploadCertificateResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final UploadCertificateResponse result = client.uploadPaymentProcessingCertificateSync(request);

        validateUploadCertificateResponse(response, result);
    }

    @Test
    void shouldEnrollDomainSync() {

        final EnrollDomainRequest request = createEnrollDomainRequest();
        final EmptyResponse response = mock(EmptyResponse.class);

        when(apiClient.post(eq("applepay/enrollments"), eq(oAuthAuthorization), eq(EmptyResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final EmptyResponse result = client.enrollDomainSync(request);

        validateEmptyResponse(response, result);
    }

    @Test
    void shouldGenerateCertificateSigningRequestSync() {

        final GenerateSigningRequestRequest request = createGenerateSigningRequestRequest();
        final GenerateSigningRequestResponse response = mock(GenerateSigningRequestResponse.class);

        when(apiClient.post(eq("applepay/signing-requests"), eq(authorization), eq(GenerateSigningRequestResponse.class),
                eq(request), isNull()))
                .thenReturn(response);

        final GenerateSigningRequestResponse result = client.generateCertificateSigningRequestSync(request);

        validateGenerateSigningRequestResponse(response, result);
    }

    // Common methods
    private void validateUploadCertificateResponse(final UploadCertificateResponse response, final UploadCertificateResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private void validateEmptyResponse(final EmptyResponse response, final EmptyResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private void validateGenerateSigningRequestResponse(final GenerateSigningRequestResponse response, final GenerateSigningRequestResponse result) {
        assertNotNull(result);
        assertEquals(response, result);
    }

    private UploadCertificateRequest createUploadCertificateRequest() {
        return UploadCertificateRequest.builder()
                .content("-----BEGIN CERTIFICATE-----\nMIIDFjCCAf4CAQAwDQYJKoZIhvcNAQEFBQAwUzELMAkGA1UEBhMCVUsxEDAOBgNV\n-----END CERTIFICATE-----")
                .build();
    }

    private EnrollDomainRequest createEnrollDomainRequest() {
        return EnrollDomainRequest.builder()
                .domain("example.com")
                .build();
    }

    private GenerateSigningRequestRequest createGenerateSigningRequestRequest() {
        return GenerateSigningRequestRequest.builder()
                .protocolVersion(ProtocolVersions.EC_V1)
                .build();
    }

    private UploadCertificateResponse createMockUploadCertificateResponse() {
        final UploadCertificateResponse response = new UploadCertificateResponse();
        response.setId("aplc_hefptsiydvkexnzzb35zrlqgfq");
        response.setPublicKeyHash("tqYV+tmG9aMh+l/K6cicUnPqkb1gUiLjSTM9gEz6Nl0=");
        response.setValidFrom(Instant.parse("2021-01-01T17:32:28Z"));
        response.setValidUntil(Instant.parse("2025-01-01T17:32:28Z"));
        return response;
    }

    private GenerateSigningRequestResponse createMockGenerateSigningRequestResponse() {
        final GenerateSigningRequestResponse response = new GenerateSigningRequestResponse();
        response.setContent("-----BEGIN CERTIFICATE REQUEST-----\nMIICWjCCAUICAQAwFTETMBEGA1UEAwwKaXNzdWluZy5jb20wggEiMA0GCSqGSIb3\n-----END CERTIFICATE REQUEST-----");
        return response;
    }

}