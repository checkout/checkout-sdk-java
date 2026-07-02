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

    private ChallengeIndicator challengeIndicator;

    private Boolean allowUpgrade;

    private MerchantAuthenticationInfo merchantAuthenticationInfo;

    private AccountInfo accountInfo;

    private String status;

    private Instant authenticationDate;

    private Long authenticationAmount;

    private ThreeDSFlowType flowType;

    private String statusReasonCode;

    private String challengeCancelReason;

    private String score;

    private String cryptogramAlgorithm;

    private String authenticationId;

    private InitialAuthentication initialAuthentication;

}

