package com.checkout.risk.precapture;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PreCaptureAssessmentResponse extends Resource {

    @SerializedName("assessment_id")
    private String assessmentId;

    private PreCaptureResult result;

    private PreCaptureWarning warning;

}
