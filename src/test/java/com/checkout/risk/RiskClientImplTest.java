package com.checkout.risk;

import com.checkout.ApiClient;
import com.checkout.CheckoutArgumentException;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorization;
import com.checkout.SdkAuthorizationType;
import com.checkout.SdkCredentials;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RiskClientImplTest {

    private static final String PRE_AUTHENTICATION_PATH = "risk/assessments/pre-authentication";
    private static final String PRE_CAPTURE_PATH = "risk/assessments/pre-capture";

    @Mock
    private ApiClient apiClient;

    @Mock
    private CheckoutConfiguration configuration;

    @Mock
    private SdkCredentials sdkCredentials;

    @Mock
    private SdkAuthorization authorization;

    private RiskClient riskClient;

    @BeforeEach
    void setup() {
        this.riskClient = new RiskClientImpl(apiClient, configuration);
    }

    @Test
    void shouldRequestPreAuthenticationRiskScan() throws ExecutionException, InterruptedException {

        setupMockCredentials();
        
        final PreAuthenticationAssessmentRequest request = createPreAuthenticationRequest();
        final PreAuthenticationAssessmentResponse response = createPreAuthenticationResponse();

        when(apiClient.postAsync(eq(PRE_AUTHENTICATION_PATH), eq(authorization), eq(PreAuthenticationAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PreAuthenticationAssessmentResponse> preAuthenticationAssessmentResponse = riskClient.requestPreAuthenticationRiskScan(request);

        validatePreAuthenticationResponse(preAuthenticationAssessmentResponse.get(), response);
    }

    @Test
    void preAuthenticationRiskScan_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreAuthenticationRiskScan(null);
            fail();
        } catch (final Exception e) {
            validateArgumentException(e, "preAuthenticationAssessmentRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRequestPreCaptureRiskScan() throws ExecutionException, InterruptedException {

        setupMockCredentials();
        
        final PreCaptureAssessmentRequest request = createPreCaptureRequest();
        final PreCaptureAssessmentResponse response = createPreCaptureResponse();

        when(apiClient.postAsync(eq(PRE_CAPTURE_PATH), eq(authorization), eq(PreCaptureAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PreCaptureAssessmentResponse> responseCompletableFuture = riskClient.requestPreCaptureRiskScan(request);

        validatePreCaptureResponse(responseCompletableFuture.get(), response);
    }

    @Test
    void preCaptureRiskScan_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreCaptureRiskScan(null);
            fail();
        } catch (final Exception e) {
            validateArgumentException(e, "preCaptureAssessmentRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    // Synchronous method tests
    @Test
    void shouldRequestPreAuthenticationRiskScanSync() {

        setupMockCredentials();
        
        final PreAuthenticationAssessmentRequest request = createPreAuthenticationRequest();
        final PreAuthenticationAssessmentResponse response = createPreAuthenticationResponse();

        when(apiClient.post(eq(PRE_AUTHENTICATION_PATH), eq(authorization), eq(PreAuthenticationAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(response);

        final PreAuthenticationAssessmentResponse preAuthenticationAssessmentResponse = riskClient.requestPreAuthenticationRiskScanSync(request);

        validatePreAuthenticationResponse(preAuthenticationAssessmentResponse, response);
    }

    @Test
    void preAuthenticationRiskScanSync_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreAuthenticationRiskScanSync(null);
            fail();
        } catch (final Exception e) {
            validateArgumentException(e, "preAuthenticationAssessmentRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    @Test
    void shouldRequestPreCaptureRiskScanSync() {

        setupMockCredentials();
        
        final PreCaptureAssessmentRequest request = createPreCaptureRequest();
        final PreCaptureAssessmentResponse response = createPreCaptureResponse();

        when(apiClient.post(eq(PRE_CAPTURE_PATH), eq(authorization), eq(PreCaptureAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(response);

        final PreCaptureAssessmentResponse responseResult = riskClient.requestPreCaptureRiskScanSync(request);

        validatePreCaptureResponse(responseResult, response);
    }

    @Test
    void preCaptureRiskScanSync_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreCaptureRiskScanSync(null);
            fail();
        } catch (final Exception e) {
            validateArgumentException(e, "preCaptureAssessmentRequest cannot be null");
        }

        verifyNoInteractions(apiClient);
    }

    // Common methods
    private void setupMockCredentials() {
        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);
    }

    private PreAuthenticationAssessmentRequest createPreAuthenticationRequest() {
        return mock(PreAuthenticationAssessmentRequest.class);
    }

    private PreAuthenticationAssessmentResponse createPreAuthenticationResponse() {
        return mock(PreAuthenticationAssessmentResponse.class);
    }

    private PreCaptureAssessmentRequest createPreCaptureRequest() {
        return mock(PreCaptureAssessmentRequest.class);
    }

    private PreCaptureAssessmentResponse createPreCaptureResponse() {
        return mock(PreCaptureAssessmentResponse.class);
    }

    private void validatePreAuthenticationResponse(PreAuthenticationAssessmentResponse actual, PreAuthenticationAssessmentResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validatePreCaptureResponse(PreCaptureAssessmentResponse actual, PreCaptureAssessmentResponse expected) {
        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    private void validateArgumentException(Exception exception, String expectedMessage) {
        assertTrue(exception instanceof CheckoutArgumentException);
        assertEquals(expectedMessage, exception.getMessage());
    }

}
