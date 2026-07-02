package com.checkout.risk.precapture;

import com.checkout.common.Resource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PreCaptureAssessmentResponse extends Resource {

    private String assessmentId;

    private PreCaptureResult result;

    private PreCaptureWarning warning;

}
