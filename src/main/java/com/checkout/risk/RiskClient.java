package com.checkout.risk;


import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;

import java.util.concurrent.CompletableFuture;

/**
 * @deprecated Risk endpoints are no longer supported officially,
 * This module will be removed in a future release.
 */
@Deprecated
public interface RiskClient {

    CompletableFuture<PreAuthenticationAssessmentResponse> requestPreAuthenticationRiskScan(PreAuthenticationAssessmentRequest preAuthenticationAssessmentRequest);

    CompletableFuture<PreCaptureAssessmentResponse> requestPreCaptureRiskScan(PreCaptureAssessmentRequest preCaptureAssessmentRequest);

}
