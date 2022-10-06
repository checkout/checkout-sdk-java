package com.checkout.payments;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Exemption;
import com.checkout.common.ThreeDSFlowType;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ThreeDSRequest {

    private Boolean enabled;

    @SerializedName("attempt_n3d")
    private Boolean attemptN3D;

    private String eci;

    private String cryptogram;

    private String xid;

    private String version;

    private Exemption exemption;

    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator;

    @SerializedName("allow_upgrade")
    private Boolean allowUpgrade;

    /**
     * Not available on Previous
     */

    private String status;

    @SerializedName("authentication_date")
    private Instant authenticationDate;

    @SerializedName("authentication_amount")
    private Long authenticationAmount;

    @SerializedName("flow_type")
    private ThreeDSFlowType flowType;

    @SerializedName("status_reason_code")
    private String statusReasonCode;

    @SerializedName("challenge_cancel_reason")
    private String challengeCancelReason;

    private String score;

    @SerializedName("cryptogram_algorithm")
    private String cryptogramAlgorithm;

    @SerializedName("authentication_id")
    private String authenticationId;

}

