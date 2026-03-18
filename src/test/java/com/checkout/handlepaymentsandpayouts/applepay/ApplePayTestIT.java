package com.checkout.handlepaymentsandpayouts.applepay;

import com.checkout.EmptyResponse;
import com.checkout.PlatformType;
import com.checkout.SandboxTestFixture;
import com.checkout.handlepaymentsandpayouts.applepay.entities.ProtocolVersions;
import com.checkout.handlepaymentsandpayouts.applepay.requests.EnrollDomainRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.GenerateSigningRequestRequest;
import com.checkout.handlepaymentsandpayouts.applepay.requests.UploadCertificateRequest;
import com.checkout.handlepaymentsandpayouts.applepay.responses.GenerateSigningRequestResponse;
import com.checkout.handlepaymentsandpayouts.applepay.responses.UploadCertificateResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApplePayTestIT extends SandboxTestFixture {

    public ApplePayTestIT() {
        super(PlatformType.DEFAULT_OAUTH);
    }

    @Disabled("This test requires a valid payment processing certificate")
    @Test
    void shouldUploadPaymentProcessingCertificate() {

        final UploadCertificateRequest request = createUploadCertificateRequest();

        final UploadCertificateResponse response = blocking(() -> checkoutApi.applePayClient().uploadPaymentProcessingCertificate(request));

        validateUploadCertificateResponse(response);

    }

    @Disabled("This test requires OAuth credentials and domain verification")
    @Test
    void shouldEnrollDomain() {

        final EnrollDomainRequest request = createEnrollDomainRequest();

        final EmptyResponse response = blocking(() -> checkoutApi.applePayClient().enrollDomain(request));

        validateEmptyResponse(response);

    }

    @Test
    void shouldGenerateCertificateSigningRequest() {

        final GenerateSigningRequestRequest request = createGenerateSigningRequestRequest();

        final GenerateSigningRequestResponse response = blocking(() -> checkoutApi.applePayClient().generateCertificateSigningRequest(request));

        validateGenerateSigningRequestResponse(response);

    }

    @Test
    void shouldGenerateCertificateSigningRequestWithRsaProtocol() {

        final GenerateSigningRequestRequest request = GenerateSigningRequestRequest.builder()
                .protocolVersion(ProtocolVersions.RSA_V1)
                .build();

        final GenerateSigningRequestResponse response = blocking(() -> checkoutApi.applePayClient().generateCertificateSigningRequest(request));

        validateGenerateSigningRequestResponse(response);

    }

    @Test
    void shouldApplePayWorkflowGenerateSigningRequestAndUploadCertificate() {

        // Arrange - Generate signing request first
        final GenerateSigningRequestRequest signingRequest = createGenerateSigningRequestRequest();

        // Act - Generate signing request
        final GenerateSigningRequestResponse signingResponse = blocking(() -> checkoutApi.applePayClient().generateCertificateSigningRequest(signingRequest));

        // Assert signing request response
        validateGenerateSigningRequestResponse(signingResponse);

        // Note: In real scenario, you would:
        // 1. Take the signingResponse.getContent()
        // 2. Submit it to Apple Developer Portal
        // 3. Download the certificate from Apple
        // 4. Upload it using uploadPaymentProcessingCertificate
        //
        // For integration testing, we skip the Apple Developer Portal steps
        // as they require manual intervention and valid Apple Developer account

    }

    // Sync methods
    @Disabled("This test requires a valid payment processing certificate")
    @Test
    void shouldUploadPaymentProcessingCertificateSync() {

        final UploadCertificateRequest request = createUploadCertificateRequest();

        final UploadCertificateResponse response = checkoutApi.applePayClient().uploadPaymentProcessingCertificateSync(request);

        validateUploadCertificateResponse(response);

    }

    @Disabled("This test requires OAuth credentials and domain verification")
    @Test
    void shouldEnrollDomainSync() {

        final EnrollDomainRequest request = createEnrollDomainRequest();

        final EmptyResponse response = checkoutApi.applePayClient().enrollDomainSync(request);

        validateEmptyResponse(response);

    }

    @Test
    void shouldGenerateCertificateSigningRequestSync() {

        final GenerateSigningRequestRequest request = createGenerateSigningRequestRequest();

        final GenerateSigningRequestResponse response = checkoutApi.applePayClient().generateCertificateSigningRequestSync(request);

        validateGenerateSigningRequestResponse(response);

    }

    @Test
    void shouldGenerateCertificateSigningRequestWithRsaProtocolSync() {

        final GenerateSigningRequestRequest request = GenerateSigningRequestRequest.builder()
                .protocolVersion(ProtocolVersions.RSA_V1)
                .build();

        final GenerateSigningRequestResponse response = checkoutApi.applePayClient().generateCertificateSigningRequestSync(request);

        validateGenerateSigningRequestResponse(response);

    }

    // Common methods
    private UploadCertificateRequest createUploadCertificateRequest() {
        // Note: This is a sample certificate format
        // In real scenarios, this would be a certificate obtained from Apple Developer Portal
        return UploadCertificateRequest.builder()
                .content("-----BEGIN CERTIFICATE-----\n" +
                        "MIIFjTCCBHWgAwIBAgIIAqVJ3DZvutkwDQYJKoZIhvcNAQEFBQAwgZYxCzAJBgNV\n" +
                        "BAYTAlVTMRMwEQYDVQQKDApBcHBsZSBJbmMuMSwwKgYDVQQLDCNBcHBsZSBXb3Js\n" +
                        "ZHdpZGUgRGV2ZWxvcGVyIFJlbGF0aW9uczFEMEIGA1UEAww7QXBwbGUgV29ybGR3\n" +
                        "aWRlIERldmVsb3BlciBSZWxhdGlvbnMgQ2VydGlmaWNhdGlvbiBBdXRob3JpdHkw\n" +
                        "HhcNMjQwMTEwMTUzNjQ1WhcNMjUwMTA5MTUzNjQ1WjCBjDEaMBgGCgmSJomT8ixk\n" +
                        "ARkWCnRlc3QtZG9tYWluMS0wKwYDVQQDDCRtZXJjaGFudC50ZXN0LWRvbWFpbiAo\n" +
                        "U2FuZGJveCkgLSBBUE1QMRMwEQYDVQQIDApDYWxpZm9ybmlhMQswCQYDVQQGEwJV\n" +
                        "UzEXMBUGA1UECgwOVGVzdCBNZXJjaGFudDEXMBUGA1UECwwOVGVzdCBNZXJjaGFu\n" +
                        "dDBZMBMGByqGSM49AgEGCCqGSM49AwEHA0IABGvWZDKkf8rkJ4V1sdf9Wt1iBZvD\n" +
                        "l9dEJkY/CJFVYNvK5qgWUzjbGKKcLFfvHt3vvK6jggHtMIIB6TAMBgNVHRMBAf8E\n" +
                        "-----END CERTIFICATE-----")
                .build();
    }

    private EnrollDomainRequest createEnrollDomainRequest() {
        return EnrollDomainRequest.builder()
                .domain("checkout-test-domain.com")
                .build();
    }

    private GenerateSigningRequestRequest createGenerateSigningRequestRequest() {
        return GenerateSigningRequestRequest.builder()
                .protocolVersion(ProtocolVersions.EC_V1)
                .build();
    }

    private void validateUploadCertificateResponse(final UploadCertificateResponse response) {
        assertNotNull(response);
        assertNotNull(response.getId());
        assertNotNull(response.getPublicKeyHash());
        assertNotNull(response.getValidFrom());
        assertNotNull(response.getValidUntil());
        assertTrue(response.getValidUntil().isAfter(response.getValidFrom()),
                "Certificate valid until should be after valid from");
    }

    private void validateEmptyResponse(final EmptyResponse response) {
        assertNotNull(response);
        // EmptyResponse doesn't have properties to validate, but should not throw
    }

    private void validateGenerateSigningRequestResponse(final GenerateSigningRequestResponse response) {
        assertNotNull(response);
        assertNotNull(response.getContent());
        assertTrue(response.getContent().contains("BEGIN CERTIFICATE REQUEST"),
                "Response should contain certificate signing request format");
        assertTrue(response.getContent().contains("END CERTIFICATE REQUEST"),
                "Response should contain certificate signing request format");
    }

}