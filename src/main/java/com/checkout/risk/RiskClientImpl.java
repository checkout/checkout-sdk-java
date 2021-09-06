package com.checkout.risk;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SecretKeyCredentials;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;

import java.util.concurrent.CompletableFuture;

public class RiskClientImpl extends AbstractClient implements RiskClient {

    private static final String PRE_AUTHENTICATION_PATH = "risk/assessments/pre-authentication";
    private static final String PRE_CAPTURE_PATH = "risk/assessments/pre-authentication";

    public RiskClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, SecretKeyCredentials.fromConfiguration(configuration));
    }

    @Override
    public CompletableFuture<PreAuthenticationAssessmentResponse> requestPreAuthenticationRiskScan(final PreAuthenticationAssessmentRequest preAuthenticationAssessmentRequest) {
        return apiClient.postAsync(PRE_AUTHENTICATION_PATH, apiCredentials, PreAuthenticationAssessmentResponse.class, preAuthenticationAssessmentRequest, null);
    }

    @Override
    public CompletableFuture<PreCaptureAssessmentResponse> requestPreCaptureRiskScan(final PreCaptureAssessmentRequest preCaptureAssessmentRequest) {
        return apiClient.postAsync(PRE_CAPTURE_PATH, apiCredentials, PreCaptureAssessmentResponse.class, preCaptureAssessmentRequest, null);
    }

}
