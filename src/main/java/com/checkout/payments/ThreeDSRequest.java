package com.checkout.payments;

import com.checkout.common.ChallengeIndicator;
import com.checkout.common.Exemption;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ThreeDSRequest {

    private boolean enabled;

    @SerializedName("attempt_n3d")
    private Boolean attemptN3D;

    private String eci;

    private String cryptogram;

    private String xid;

    private String version;

    private Exemption exemption;

    @SerializedName("challenge_indicator")
    private ChallengeIndicator challengeIndicator;

}

