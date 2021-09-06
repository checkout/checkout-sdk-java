package com.checkout.risk;


import com.checkout.risk.preauthentication.PreAuthenticationAssessmentRequest;
import com.checkout.risk.preauthentication.PreAuthenticationAssessmentResponse;
import com.checkout.risk.precapture.PreCaptureAssessmentRequest;
import com.checkout.risk.precapture.PreCaptureAssessmentResponse;

import java.util.concurrent.CompletableFuture;

public interface RiskClient {

    CompletableFuture<PreAuthenticationAssessmentResponse> requestPreAuthenticationRiskScan(PreAuthenticationAssessmentRequest preAuthenticationAssessmentRequest);

    CompletableFuture<PreCaptureAssessmentResponse> requestPreCaptureRiskScan(PreCaptureAssessmentRequest preCaptureAssessmentRequest);

}
