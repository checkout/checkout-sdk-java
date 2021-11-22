package com.checkout.risk;

import com.checkout.AbstractClient;
import com.checkout.ApiClient;
import com.checkout.CheckoutConfiguration;
import com.checkout.SdkAuthorizationType;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;

import java.util.concurrent.CompletableFuture;

import static com.checkout.common.CheckoutUtils.validateParams;

public class RiskClientImpl extends AbstractClient implements RiskClient {

    private static final String PRE_AUTHENTICATION_PATH = "risk/assessments/pre-authentication";
    private static final String PRE_CAPTURE_PATH = "risk/assessments/pre-capture";

    public RiskClientImpl(final ApiClient apiClient, final CheckoutConfiguration configuration) {
        super(apiClient, configuration, SdkAuthorizationType.SECRET_KEY);
    }

    @Override
    public CompletableFuture<PreAuthenticationAssessmentResponse> requestPreAuthenticationRiskScan(final PreAuthenticationAssessmentRequest preAuthenticationAssessmentRequest) {
        validateParams("preAuthenticationAssessmentRequest", preAuthenticationAssessmentRequest);
        return apiClient.postAsync(PRE_AUTHENTICATION_PATH, sdkAuthorization(), PreAuthenticationAssessmentResponse.class, preAuthenticationAssessmentRequest, null);
    }

    @Override
    public CompletableFuture<PreCaptureAssessmentResponse> requestPreCaptureRiskScan(final PreCaptureAssessmentRequest preCaptureAssessmentRequest) {
        validateParams("preCaptureAssessmentRequest", preCaptureAssessmentRequest);
        return apiClient.postAsync(PRE_CAPTURE_PATH, sdkAuthorization(), PreCaptureAssessmentResponse.class, preCaptureAssessmentRequest, null);
    }

}
