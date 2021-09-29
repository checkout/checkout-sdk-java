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
    private static final String PRE_CAPTURE_PATH = "risk/assessments/pre-authentication";

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

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final PreAuthenticationAssessmentRequest request = mock(PreAuthenticationAssessmentRequest.class);
        final PreAuthenticationAssessmentResponse response = mock(PreAuthenticationAssessmentResponse.class);

        when(apiClient.postAsync(eq(PRE_AUTHENTICATION_PATH), eq(authorization), eq(PreAuthenticationAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PreAuthenticationAssessmentResponse> preAuthenticationAssessmentResponse = riskClient.requestPreAuthenticationRiskScan(request);

        assertNotNull(preAuthenticationAssessmentResponse.get());
        assertEquals(response, preAuthenticationAssessmentResponse.get());

    }

    @Test
    void preAuthenticationRiskScan_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreAuthenticationRiskScan(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("preAuthenticationAssessmentRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

    @Test
    void shouldRequestPreCaptureRiskScan() throws ExecutionException, InterruptedException {

        when(sdkCredentials.getAuthorization(SdkAuthorizationType.SECRET_KEY)).thenReturn(authorization);
        when(configuration.getSdkCredentials()).thenReturn(sdkCredentials);

        final PreCaptureAssessmentRequest request = mock(PreCaptureAssessmentRequest.class);
        final PreCaptureAssessmentResponse response = mock(PreCaptureAssessmentResponse.class);

        when(apiClient.postAsync(eq(PRE_CAPTURE_PATH), eq(authorization), eq(PreCaptureAssessmentResponse.class), eq(request), isNull()))
                .thenReturn(CompletableFuture.completedFuture(response));

        final CompletableFuture<PreCaptureAssessmentResponse> responseCompletableFuture = riskClient.requestPreCaptureRiskScan(request);

        assertNotNull(responseCompletableFuture.get());
        assertEquals(response, responseCompletableFuture.get());

    }

    @Test
    void preCaptureRiskScan_shouldThrowOnNullRequest() {

        try {
            riskClient.requestPreCaptureRiskScan(null);
            fail();
        } catch (final Exception e) {
            assertTrue(e instanceof CheckoutArgumentException);
            assertEquals("preCaptureAssessmentRequest cannot be null", e.getMessage());
        }

        verifyNoInteractions(apiClient);

    }

}
