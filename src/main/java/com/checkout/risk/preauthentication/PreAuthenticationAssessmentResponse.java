package com.checkout.risk.preauthentication;

import com.checkout.common.Resource;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class PreAuthenticationAssessmentResponse extends Resource {

    @SerializedName("assessment_id")
    private String assessmentId;

    private Long score;

    private PreAuthenticationResult result;

    private PreAuthenticationWarning warning;

}
